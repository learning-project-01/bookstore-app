package com.example.bookstoreapp.controllers;

import com.example.bookstoreapp.models.OrderRequest;
import com.example.bookstoreapp.models.ShoppingOrder;
import com.example.bookstoreapp.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/orders")
public class ShoppingOrderController {

  @Autowired
  private OrderService orderService;

  @PostMapping
  public ShoppingOrder createOrder(@RequestBody OrderRequest orderRequest){
    return orderService.createOrder(orderRequest);
  }

  @GetMapping
  public List<ShoppingOrder> list(){
    return orderService.list();
  }

  @GetMapping("/{orderId}")
  public ShoppingOrder getByOrderId(@PathVariable long orderId){
    return orderService.extractOrder(orderId);
  }
}
