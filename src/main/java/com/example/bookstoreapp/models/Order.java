package com.example.bookstoreapp.models;

import com.example.bookstoreapp.entities.OrderEntity;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Date;

@Data
public class Order {
    private long orderId;
    private long customerId;
    private LocalDateTime orderTime;
    private double totalAmount;
    private String shippingAddress;

    public OrderEntity toEntity() {
        OrderEntity orderEntity = new OrderEntity();
        orderEntity.setOrderId(this.getOrderId());
        orderEntity.setCustomerId(this.getCustomerId());
        orderEntity.setOrderTime(this.getOrderTime());
        orderEntity.setTotalAmount(this.getTotalAmount());
        orderEntity.setShippingAddress(this.getShippingAddress());
        return orderEntity;
    }

    public Order fromEntity(OrderEntity orderEntity) {
        this.setOrderId(orderEntity.getOrderId());
        this.setCustomerId(orderEntity.getCustomerId());
        this.setOrderTime(orderEntity.getOrderTime());
        this.setTotalAmount(orderEntity.getTotalAmount());
        this.setShippingAddress(orderEntity.getShippingAddress());
        return this;
    }

}
