package com.example.bookstoreapp.services.impl;

import com.example.bookstoreapp.entities.OrderItemEntity;
import com.example.bookstoreapp.entities.ShoppingOrderEntity;
import com.example.bookstoreapp.exceptions.AppRuntimeException;
import com.example.bookstoreapp.models.*;
import com.example.bookstoreapp.repositories.OrderItemEntityRepository;
import com.example.bookstoreapp.repositories.ShoppingOrderEntityRepository;
import com.example.bookstoreapp.services.*;
import com.example.bookstoreapp.utils.AppUtils;
import com.example.bookstoreapp.utils.IdGenerator;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.FontSelector;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import jakarta.persistence.EntityManager;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class OrderServiceImpl implements OrderService {

  @Autowired
  private AddressService addressService;

  @Autowired
  private CartItemService cartItemService;

  @Autowired
  private CatalogItemService catalogItemService;

  @Autowired
  private UserContextService userContextService;

  @Autowired
  private ShoppingOrderEntityRepository shoppingOrderEntityRepository;

  @Autowired
  private OrderItemEntityRepository orderItemEntityRepository;

  @Autowired
  private EntityManager em;

  @Override
  @Transactional
  public ShoppingOrder createOrder(OrderRequest orderRequest) {

    ShoppingOrder shoppingOrder = new ShoppingOrder();
    shoppingOrder.setId(IdGenerator.getLongId());
    shoppingOrder.setOrderDate(new Date());

    // 1. do checkout
    Cart cart = cartItemService.doCheckout(userContextService.getUserId());

    // 2. set totalAmount for items from cart
    shoppingOrder.setTotalAmount(cart.getTotal());

    // 3. get totalItemCount from cart
    // for this we know each item has certain purchase quantity, so we have to sum it
    // let say in the cart item1 has quantity 2, item2 has quantity 3.
    // we will iterate over cart.getItems() and get the quantity for each item and then sum all of them
    // hence total quantity will be  2 + 3 = 5
    int totalItemCount = cart
        .getItems()
        .stream()
        .mapToInt(CartItem::getQuantity)
        .sum();
    shoppingOrder.setTotalItemCount(totalItemCount);

    // 4. Convert each CartItem to OrderItem
    List<OrderItem> orderItems = cart
        .getItems()
        .stream()
        .map(this::convertToOrderItem).toList();

    shoppingOrder.setOrderItems(orderItems);

    // 5. set the address to which order will be shipped. so we have to extract the address from db for user
    Address address = extractAddress(orderRequest.getAddressId());
    String addressStr = AppUtils.toString(address);
    shoppingOrder.setAddress(addressStr);

    // 6. db operation to save shopping order and its item

    final Map<Long, Integer> itemPurchaseCount = new HashMap<>();
    ShoppingOrderEntity entity = shoppingOrderEntityRepository.save(shoppingOrder.toEntity());
    List<OrderItemEntity> orderItemEntities = shoppingOrder
        .getOrderItems()
        .stream()
        .map(e->{
          itemPurchaseCount.put(e.getCatalogItemId(), e.getQuantity());
          e.setOrderId(entity.getId());
          return e.toEntity(entity.getId());
        })
        .toList();
    orderItemEntityRepository.saveAll(orderItemEntities);

    log.info("order amount: {} placed: {}, with item count: {} from cart: {}",
        shoppingOrder.getTotalAmount(),
        shoppingOrder.getId(),
        shoppingOrder.getTotalItemCount(),
        cart.getCartId());

    cartItemService.clearCartPostOrder();
    catalogItemService.reduceStockCount(itemPurchaseCount);

    writeOrderAsJsonDocument(shoppingOrder);
      generateOrderInvoice(shoppingOrder, address, orderItems, cart);
    return shoppingOrder;

  }

    private void generateOrderInvoice(ShoppingOrder shoppingOrder, Address address, List<OrderItem> orderItemsOfInvoice, Cart cart) {
        JSONObject orderJSONObject = new JSONObject(shoppingOrder);
        String userHome = System.getProperty("user.home");
        String fileName = shoppingOrder.getId() + ".pdf";
        String filePath = userHome + "/" + fileName;

        Document document = new Document(PageSize.A4);
        try {
            PdfWriter.getInstance(document, new FileOutputStream(filePath));

            document.open();

            Font titleFont = FontFactory.getFont(FontFactory.HELVETICA, 20, Font.BOLD);
            Paragraph title = new Paragraph();
            Chunk chunk = new Chunk("Order Invoice", titleFont);
            chunk.setUnderline(0.5f, -2f);
            title.add(chunk);
            title.setAlignment(Element.ALIGN_CENTER);
            title.setSpacingAfter(20);
            document.add(title);

            Instant orderDateInstant = shoppingOrder.getOrderDate().toInstant();
            LocalDate orderDate = orderDateInstant.atZone(ZoneId.systemDefault()).toLocalDate();
            String formattedDate = orderDate.toString();

            PdfPTable invoiceInfo = new PdfPTable(2);
            invoiceInfo.addCell(orderInfoCell("Order Id"));
            invoiceInfo.addCell(orderInfoCell("Order Date"));
            PdfPCell orderIdCell = orderInfoCell(" " + shoppingOrder.getId() + " ");
            orderIdCell.setHorizontalAlignment(Element.ALIGN_CENTER);
            orderIdCell.setFixedHeight(25);
            orderIdCell.setPadding(5);
            orderIdCell.setNoWrap(true);
            invoiceInfo.addCell(orderIdCell);
            invoiceInfo.addCell(orderInfoCell(formattedDate));

            PdfPTable orderInfo = new PdfPTable(3);
            orderInfo.setWidthPercentage(100);

            orderInfo.addCell(topRightTable("", PdfPCell.ALIGN_RIGHT));
            orderInfo.addCell(topRightTable("", PdfPCell.ALIGN_RIGHT));
            orderInfo.addCell(topRightTable("Order Details", PdfPCell.ALIGN_CENTER));
            orderInfo.addCell(topRightTable("", PdfPCell.ALIGN_RIGHT));
            orderInfo.addCell(topRightTable("", PdfPCell.ALIGN_RIGHT));
            PdfPCell invoiceTable = new PdfPCell(invoiceInfo);
            invoiceTable.setBorder(0);
            orderInfo.addCell(invoiceTable);

            FontSelector fs = new FontSelector();
            Font font = FontFactory.getFont(FontFactory.TIMES_ROMAN, 13, Font.UNDERLINE);
            fs.addFont(font);
            Phrase bill = fs.process("Billing Address");
            Paragraph line1 = new Paragraph("Address Line1: " + address.getLine1());
            Paragraph line2 = new Paragraph("Address Line2: " + address.getLine2());
            Paragraph city = new Paragraph("City: " + address.getCity());
            Paragraph postal = new Paragraph("Pin code: " + address.getPostalCode());
            Paragraph country = new Paragraph("Country: " + address.getCountry());

            PdfPTable billTable = new PdfPTable(5);
            billTable.setWidthPercentage(100);
            billTable.setWidths(new float[]{1, 5, 2, 2, 2});
            billTable.setSpacingBefore(30.0f);
            billTable.addCell(amountHeaderCell("Index"));
            billTable.addCell(amountHeaderCell("Item Name"));
            billTable.addCell(amountHeaderCell("Unit Price"));
            billTable.addCell(amountHeaderCell("Quantity"));
            billTable.addCell(amountHeaderCell("Amount"));

            for (int i = 0; i < orderItemsOfInvoice.size(); i++) {
                OrderItem orderItem = orderItemsOfInvoice.get(i);
                billTable.addCell(ammountLine(String.valueOf(i + 1)));
                billTable.addCell((ammountLine(cart.getItems().get(i).getName())));
                billTable.addCell((ammountLine(String.valueOf(orderItem.getUnitPrice()))));
                billTable.addCell(ammountLine(String.valueOf(orderItem.getQuantity())));
                billTable.addCell(ammountLine(String.valueOf(orderItem.getTotal())));
            }

            Float discount = 10F;
            Float tax = 18F;
            Float discountAmount = shoppingOrder.getTotalAmount() * (discount / 100);
            Float taxAmount = shoppingOrder.getTotalAmount() * (tax / 100);
            Float grandTotal = shoppingOrder.getTotalAmount() - discountAmount + taxAmount;

            PdfPTable amountInWords = new PdfPTable(1);
            amountInWords.setWidthPercentage(100);
            amountInWords.addCell(ammountToWords(""));
            amountInWords.addCell(ammountToWords("Amount in Words: "));
            amountInWords.addCell(amountBottemCell(AmountToWordsConverter.convertToWords(grandTotal)));
            amountInWords.addCell(ammountToWords(""));
            PdfPCell summaryL = new PdfPCell(amountInWords);
            summaryL.setColspan(3);
            summaryL.setPadding(1.0f);
            billTable.addCell(summaryL);


            PdfPTable accounts = new PdfPTable(2);
            accounts.setWidthPercentage(100);
            accounts.addCell(getAccountsCell("Subtotal"));
            accounts.addCell(getAccountsCellR(String.valueOf(shoppingOrder.getTotalAmount())));
            accounts.addCell(getAccountsCell("Discount (10%)"));
            accounts.addCell(getAccountsCellR(String.valueOf(discountAmount)));
            accounts.addCell(getAccountsCell("Tax(18%)"));
            accounts.addCell(getAccountsCellR(String.valueOf(taxAmount)));
            accounts.addCell(getAccountsCell("Grand Total"));
            accounts.addCell(getAccountsCellR(String.valueOf(grandTotal)));
            PdfPCell summaryR = new PdfPCell(accounts);
            summaryR.setColspan(3);
            billTable.addCell(summaryR);

            document.add(orderInfo);
            document.add(bill);
            document.add(line1);
            document.add(line2);
            document.add(city);
            document.add(postal);
            document.add(country);
            document.add(billTable);

            document.close();

            System.out.println("Table created successfully.");

        } catch (DocumentException | FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public static void setHeader() {

    }


    public static PdfPCell topRightTable(String text, int alignment) {
        FontSelector fs = new FontSelector();
        Font font = FontFactory.getFont(FontFactory.HELVETICA, 16);
        fs.addFont(font);
        Phrase phrase = fs.process(text);
        PdfPCell cell = new PdfPCell(phrase);
        cell.setPadding(5);
        cell.setHorizontalAlignment(alignment);
        cell.setBorder(PdfPCell.NO_BORDER);
        return cell;
    }

    public static PdfPCell orderInfoCell(String text) {
        PdfPCell cell = new PdfPCell(new Paragraph(text));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setPadding(5.0f);
        cell.setBorderColor(BaseColor.LIGHT_GRAY);
        return cell;
    }

    public static PdfPCell amountHeaderCell(String text) {
        FontSelector fs = new FontSelector();
        Font font = FontFactory.getFont(FontFactory.HELVETICA, 11);
        font.setColor(BaseColor.GRAY);
        fs.addFont(font);
        Phrase phrase = fs.process(text);
        PdfPCell cell = new PdfPCell(phrase);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setPadding(5.0f);
        return cell;
    }

    public static PdfPCell ammountLine(String text) {
        PdfPCell cell = new PdfPCell(new Paragraph(text));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setPadding(5.0f);
        cell.setBorderWidthBottom(0);
        cell.setBorderWidthTop(0);
        return cell;
    }

    public static PdfPCell amountBottemCell(String text) {
        Font font = FontFactory.getFont(FontFactory.HELVETICA, 10, Font.BOLDITALIC);
        PdfPCell cell = new PdfPCell(new Paragraph(text, font));
        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
        cell.setPadding(5.0f);
        cell.setBorderWidthBottom(0);
        cell.setBorderWidthTop(0);
        cell.setBorderWidthLeft(0);
        cell.setBorderWidthRight(0);
        return cell;
    }


    public static PdfPCell ammountToWords(String text) {
        FontSelector fs = new FontSelector();
        Font font = FontFactory.getFont(FontFactory.HELVETICA, 10);
        font.setColor(BaseColor.GRAY);
        fs.addFont(font);
        Phrase phrase = fs.process(text);
        PdfPCell cell = new PdfPCell(phrase);
        cell.setBorder(0);
        return cell;
    }

    public static PdfPCell getAccountsCell(String text) {
        FontSelector fs = new FontSelector();
        Font font = FontFactory.getFont(FontFactory.HELVETICA, 10);
        fs.addFont(font);
        Phrase phrase = fs.process(text);
        PdfPCell cell = new PdfPCell(phrase);
        cell.setBorderWidthRight(0);
        cell.setBorderWidthTop(0);
        cell.setPadding(5.0f);
        return cell;
    }

    public static PdfPCell getAccountsCellR(String text) {
        FontSelector fs = new FontSelector();
        Font font = FontFactory.getFont(FontFactory.HELVETICA, 10);
        fs.addFont(font);
        Phrase phrase = fs.process(text);
        PdfPCell cell = new PdfPCell(phrase);
        cell.setBorderWidthLeft(0);
        cell.setBorderWidthTop(0);
        cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
        cell.setPadding(5.0f);
        cell.setPaddingRight(20.0f);
        return cell;
    }

    public static PdfPCell getdescCell(String text) {
        FontSelector fs = new FontSelector();
        Font font = FontFactory.getFont(FontFactory.HELVETICA, 10);
        font.setColor(BaseColor.GRAY);
        fs.addFont(font);
        Phrase phrase = fs.process(text);
        PdfPCell cell = new PdfPCell(phrase);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setBorder(0);
        return cell;
    }

  private void writeOrderAsJsonDocument(ShoppingOrder shoppingOrder){
    String userHome = System.getProperty("user.home");
    String fileName = shoppingOrder.getId()+".json";
    String filePath = userHome + "/" +fileName;
    log.info("writing to file: {} for order: {}", filePath, shoppingOrder.getId());
    AppUtils.writeToFile(filePath, shoppingOrder);
    log.info("[ written ] to file: {} for order: {}", filePath, shoppingOrder.getId());
  }

    private OrderItem convertToOrderItem(CartItem cartItem){
    OrderItem orderItem = new OrderItem();
    orderItem.setId(IdGenerator.getLongId());
    orderItem.setCatalogItemId(cartItem.getId());
    orderItem.setUnitPrice(cartItem.getUnitPrice());
    orderItem.setQuantity(cartItem.getQuantity());
    orderItem.setTotal(cartItem.getTotal());
    orderItem.setCatalogItemId(cartItem.getId());
    orderItem.setCatalogItemId(cartItem.getId());
    orderItem.setCartId(cartItem.getCartId());
    orderItem.setPurchasedOn(new Date());
    orderItem.setStatusCode(StatusCode.ORDER_PLACED);
    return orderItem;
  }

  private Address extractAddress(Long addressId){

    List<Address> addressList = addressService.findUserAddress(userContextService.getUserId());
    if(addressList.isEmpty()){
      throw new AppRuntimeException("please add an address for order");
    }
    Address address = addressList
        .stream()
        .filter(element-> element.getId().equals(addressId)) // matching with addressId
        .findFirst()
        .orElseThrow(()->new AppRuntimeException("Address not found"));

    return address;
  }

    public static class AmountToWordsConverter {

        private static final String[] units = {"", "One", "Two", "Three", "Four", "Five", "Six", "Seven", "Eight", "Nine"};

        private static final String[] teens = {"Ten", "Eleven", "Twelve", "Thirteen", "Fourteen", "Fifteen", "Sixteen", "Seventeen", "Eighteen", "Nineteen"};

        private static final String[] tens = {"", "", "Twenty", "Thirty", "Forty", "Fifty", "Sixty", "Seventy", "Eighty", "Ninety"};

        private static final String[] thousands = {"", "Thousand", "Million", "Billion"};

        private AmountToWordsConverter() {
        }

        public static String convertToWords(Float number) {
            if (number == 0) {
                return "Zero";
            }

            String words = "";
            int index = 0;

            do {
                int num = (int) (number % 1000);
                if (num != 0) {
                    String chunkWords = convertChunk(num);
                    words = chunkWords + thousands[index] + " " + words;
                }
                index++;
                number /= 1000;
            } while (number > 0);

            return words.trim();
        }

        private static String convertChunk(int number) {
            String numToWord = "";

            if (number >= 100) {
                numToWord += units[number / 100] + " Hundred ";
                number %= 100;
            }

            if (number >= 10 && number <= 19) {
                numToWord += teens[number - 10] + " ";
            } else if (number >= 20) {
                numToWord += tens[number / 10] + " ";
                number %= 10;
            }

            if (number > 0) {
                numToWord += units[number] + " ";
            }

            return numToWord;
        }
    }

}
