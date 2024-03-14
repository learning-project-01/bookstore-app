package com.example.bookstoreapp.services;

import com.example.bookstoreapp.models.ShoppingOrder;
import com.example.bookstoreapp.models.OrderRequest;

public interface OrderService {

  public ShoppingOrder createOrder(OrderRequest orderRequest);

  public ShoppingOrder generateOrderInvoice(Long id);

}
