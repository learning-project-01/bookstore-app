package com.example.bookstoreapp.services;

import com.example.bookstoreapp.models.Order;

public interface OrderService {
    public Order createOrder(Order order);
    public Order orderSummary(Long orderId);


}
