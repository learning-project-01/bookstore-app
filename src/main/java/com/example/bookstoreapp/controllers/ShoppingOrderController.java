package com.example.bookstoreapp.controllers;

import com.example.bookstoreapp.models.OrderRequest;
import com.example.bookstoreapp.models.ShoppingOrder;
import com.example.bookstoreapp.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/orders")
public class ShoppingOrderController {

  @Autowired
  private OrderService orderService;

  @PostMapping
  public ShoppingOrder createOrder(OrderRequest orderRequest){
    return orderService.createOrder(orderRequest);
  }
}
