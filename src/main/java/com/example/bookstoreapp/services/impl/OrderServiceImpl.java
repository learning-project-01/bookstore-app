package com.example.bookstoreapp.services.impl;
import com.example.bookstoreapp.entities.CartItemEntity;
import com.example.bookstoreapp.entities.OrderEntity;
import com.example.bookstoreapp.entities.UserEntity;
import com.example.bookstoreapp.models.Address;
import com.example.bookstoreapp.models.Order;
import com.example.bookstoreapp.models.User;
import com.example.bookstoreapp.repositories.OrderRepository;
import com.example.bookstoreapp.services.CartItemService;
import com.example.bookstoreapp.services.CatalogItemService;
import com.example.bookstoreapp.services.OrderService;
import com.example.bookstoreapp.services.UserContextService;
import com.example.bookstoreapp.utils.IdGenerator;
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
private OrderRepository orderRepository;
    @Autowired
    private CartItemService cartItemService;
    @Autowired
    private CatalogItemService catalogItemService;
    @Override
    public Order createOrder(Order order) {
        order.setUserId(System.nanoTime());
        order.setOrderId(System.nanoTime());
        LocalDateTime orderDate = LocalDateTime.now();
        LocalDate currentDate = LocalDate.now();
        LocalDate deliveryDate = currentDate.plusDays(5);
       List<Address> address=new AddressServiceImpl().findUserAddress(new UserContextServiceImpl().getUserId());
       Address AddressId=address.getAddressId();

        OrderEntity orderEntity = orderRepository.save(order.toEntity());
        return new Order().fromEntity(orderEntity);    }

    @Override
    public Order orderSummary(Long orderId) {

        return null;
    }
}


