package com.example.bookstoreapp.services;

import com.example.bookstoreapp.models.ShoppingOrder;
import com.example.bookstoreapp.models.OrderRequest;

import java.util.List;

public interface OrderService {

  public ShoppingOrder createOrder(OrderRequest orderRequest);
  public List<ShoppingOrder> list();

  public ShoppingOrder extractOrder(long orderId);
}
