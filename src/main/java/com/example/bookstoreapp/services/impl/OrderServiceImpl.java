package com.example.bookstoreapp.services.impl;

import com.example.bookstoreapp.entities.OrderItemEntity;
import com.example.bookstoreapp.entities.ShoppingOrderEntity;
import com.example.bookstoreapp.exceptions.AppRuntimeException;
import com.example.bookstoreapp.models.Address;
import com.example.bookstoreapp.models.Cart;
import com.example.bookstoreapp.models.CartItem;
import com.example.bookstoreapp.models.OrderItem;
import com.example.bookstoreapp.models.ShoppingOrder;
import com.example.bookstoreapp.models.OrderRequest;
import com.example.bookstoreapp.models.StatusCode;
import com.example.bookstoreapp.repositories.OrderItemEntityRepository;
import com.example.bookstoreapp.repositories.ShoppingOrderEntityRepository;
import com.example.bookstoreapp.services.AddressService;
import com.example.bookstoreapp.services.CartItemService;
import com.example.bookstoreapp.services.CatalogItemService;
import com.example.bookstoreapp.services.OrderService;
import com.example.bookstoreapp.services.UserContextService;
import com.example.bookstoreapp.utils.AppUtils;
import com.example.bookstoreapp.utils.IdGenerator;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    return shoppingOrder;

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
}
