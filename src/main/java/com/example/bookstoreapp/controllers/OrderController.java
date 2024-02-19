package com.example.bookstoreapp.controllers;
import com.example.bookstoreapp.models.Order;
import com.example.bookstoreapp.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping
    public Order createOrder(@RequestBody Order order) {
       return orderService.createOrder(order);
    }
    @GetMapping
    public Order orderSummary(Long orderId) {
        return orderService.orderSummary(orderId);
    }
}
