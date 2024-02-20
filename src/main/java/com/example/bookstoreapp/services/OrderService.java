package com.example.bookstoreapp.services;

import com.example.bookstoreapp.models.Order;

import java.util.List;

public interface OrderService {
    Order createOrder(Order order);

    Order orderSummary(Long orderId);

    List<Order> allOrders(Long customerId);
}
