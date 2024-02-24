package com.example.bookstoreapp.services;

import com.example.bookstoreapp.models.OrderRequest;
import com.example.bookstoreapp.models.ShoppingOrder;

public interface OrderService {
    public ShoppingOrder createOrder(OrderRequest orderRequest);
}
