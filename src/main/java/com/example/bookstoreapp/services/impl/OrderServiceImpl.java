package com.example.bookstoreapp.services.impl;

import com.example.bookstoreapp.entities.OrderItemEntity;
import com.example.bookstoreapp.entities.ShoppingOrderEntity;
import com.example.bookstoreapp.exceptions.AppRuntimeException;
import com.example.bookstoreapp.models.*;
import com.example.bookstoreapp.repositories.CartItemEntityRepository;
import com.example.bookstoreapp.repositories.OrderItemEntityRepository;
import com.example.bookstoreapp.repositories.ShoppingOrderEntityRepository;
import com.example.bookstoreapp.services.AddressService;
import com.example.bookstoreapp.services.CartItemService;
import com.example.bookstoreapp.services.OrderService;
import com.example.bookstoreapp.services.UserContextService;
import com.example.bookstoreapp.utils.AppUtils;
import com.example.bookstoreapp.utils.IdGenerator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Date;
import java.util.List;
@Service
@Slf4j
public class OrderServiceImpl implements OrderService {

    @Autowired
    private AddressService addressService;

    @Autowired
    private CartItemService cartItemService;

    @Autowired
    private UserContextService userContextService;

    @Autowired
    private ShoppingOrderEntityRepository shoppingOrderEntityRepository;

    @Autowired
    private OrderItemEntityRepository orderItemEntityRepository;

    @Override
    public ShoppingOrder createOrder(OrderRequest orderRequest) {

        ShoppingOrder shoppingOrder = new ShoppingOrder();
        shoppingOrder.setId(IdGenerator.getLongId());
        shoppingOrder.setOrderDate(new Date());
        Cart cart = cartItemService.doCheckout(userContextService.getUserId());
        shoppingOrder.setTotalAmount(cart.getTotal());
        int totalItemCount = cart
                .getItems()
                .stream()
                .mapToInt(CartItem::getQuantity)
                .sum();
        shoppingOrder.setTotalItemCount(totalItemCount);
        List<OrderItem> orderItems = cart
                .getItems()
                .stream()
                .map(this::convertToOrderItem).toList();
        shoppingOrder.setOrderItems(orderItems);
        Address address = extractAddress(orderRequest.getAddressId());
        String addressStr = AppUtils.toString(address);
        shoppingOrder.setAddress(addressStr);
        ShoppingOrderEntity entity = shoppingOrderEntityRepository.save(shoppingOrder.toEntity());
        List<OrderItemEntity> orderItemEntities = shoppingOrder
                .getOrderItems()
                .stream()
                .map(e->{
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

        return shoppingOrder;
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
    private Address extractAddress(Long addressId) {
        List<Address> addressList = addressService.findUserAddress(userContextService.getUserId());
        if (addressList.isEmpty()) {
            throw new AppRuntimeException("please add an address for order");
        }
        Address address = addressList
                .stream()
                .filter(element -> element.getId().equals(addressId))
                .findFirst()
                .orElseThrow(() -> new AppRuntimeException("Address not found"));
        return address;
        // Delete cart items associated with the order
        List<CartItem> cartItems=deleteCartItems(cartItemService.doCheckout(userContextService.getUserId()));
    }
    private void deleteCartItems(List<CartItem> cartItems) {
        for (CartItem cartItem : cartItems) {
            CartItemEntityRepository.delete(cartItem);
        }
    }
}