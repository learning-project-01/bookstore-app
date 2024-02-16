package com.example.bookstoreapp.services;

import com.example.bookstoreapp.models.Order;

import java.util.List;

public interface OrderService {
     Order createOrder(Order order);

     Order orderSummary(Long order_id);

     List<Order> allOrders(Long customer_id);
}
