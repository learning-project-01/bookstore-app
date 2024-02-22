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
import com.example.bookstoreapp.repositories.OrderItemEntityRepository;
import com.example.bookstoreapp.repositories.ShoppingOrderEntityRepository;
import com.example.bookstoreapp.services.AddressService;
import com.example.bookstoreapp.services.CartItemService;
import com.example.bookstoreapp.services.OrderService;
import com.example.bookstoreapp.services.UserContextService;
import com.example.bookstoreapp.utils.AppUtils;
import com.example.bookstoreapp.utils.IdGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
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

    // 6. db operation to reduce items in catalog

    // 7. db operation to save shopping order and its item
    ShoppingOrderEntity entity = shoppingOrderEntityRepository.save(shoppingOrder.toEntity());
    List<OrderItemEntity> orderItemEntities = shoppingOrder
        .getOrderItems()
        .stream()
        .map(e-> e.toEntity(entity.getId()))
        .toList();
    orderItemEntityRepository.saveAll(orderItemEntities);
    return shoppingOrder;

  }

  private OrderItem convertToOrderItem(CartItem cartItem){
    return null; // todo the logic
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
