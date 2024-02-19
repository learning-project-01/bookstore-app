package com.example.bookstoreapp.controllers;

import com.example.bookstoreapp.models.Order;
import com.example.bookstoreapp.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    OrderService orderService;

    @PostMapping
    public Order crateOrder(@RequestBody Order order) {
        return orderService.createOrder(order);
    }
//    @GetMapping
//    public Order crateOrder(Order order) {
//        return orderService.createOrder(order);
//    }

    @GetMapping("/{orderId}")
    public Order orderSummary(@PathVariable("orderId") long orderId) {
        return orderService.orderSummary(orderId);
    }

    @PostMapping("/{customerId}")
    public List<Order> allOrders(@PathVariable("customerId") long customerId) {
        return orderService.allOrders(customerId);
    }

}
