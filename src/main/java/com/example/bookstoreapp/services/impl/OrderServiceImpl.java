package com.example.bookstoreapp.services.impl;
import com.example.bookstoreapp.entities.CartItemEntity;
import com.example.bookstoreapp.entities.OrderEntity;
import com.example.bookstoreapp.entities.UserEntity;
import com.example.bookstoreapp.exceptions.AppRuntimeException;
import com.example.bookstoreapp.models.Address;
import com.example.bookstoreapp.models.Cart;
import com.example.bookstoreapp.models.Order;
import com.example.bookstoreapp.models.User;
import com.example.bookstoreapp.repositories.OrderRepository;
import com.example.bookstoreapp.services.*;
import com.example.bookstoreapp.utils.IdGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    private CartItemService cartItemService;
    @Autowired
    private UserContextService userContextService;
    @Autowired
    private AddressService addressService;
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private CatalogItemService catalogItemService;

    @Override
    public Order createOrder(Order order) {
        order.setOrderId(IdGenerator.getLongId());
        LocalDate localDate=LocalDate.now();
        order.setOrderdate(localDate);
        order.setDeliveryDate(localDate.plusDays(5));
        order.setUserId(userContextService.getUserId());
        Cart cart = cartItemService.doCheckout(userContextService.getUserId());
        order.setTotalAmount(Double.valueOf(cart.getTotal()));
        List<Address> addressList = addressService.findUserAddress(userContextService.getUserId());
        ObjectMapper objectMapper = new ObjectMapper();
        if (addressList != null) {
            try {
                String jsonAddress = objectMapper.writeValueAsString(addressList.get(0));
                order.setAddressId(Integer.valueOf(jsonAddress));
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        } else throw new AppRuntimeException("No address exists for the user");

        OrderEntity orderEntity = orderRepository.save(order.toEntity());
        return new Order().fromEntity(orderEntity); }


    @Override
    public Order orderSummary(Long orderId) {
        return null;
    }

}


