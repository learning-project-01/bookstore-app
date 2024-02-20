package com.example.bookstoreapp.models;

import com.example.bookstoreapp.entities.OrderEntity;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;

@Data
public class Order {

    private Long orderId;
    private Long userId;
    private LocalDate orderDate;
    private String shippingAddress;
    private Double totalAmount;
    private LocalDate deliveryDate;

    public OrderEntity toEntity() {
        OrderEntity orderEntity = new OrderEntity();
        orderEntity.setOrderId(this.getOrderId());
        orderEntity.setUserId(this.getUserId());
        orderEntity.setOrderDate(this.getOrderDate());
        orderEntity.setShippingAddress(this.getShippingAddress());
        orderEntity.setTotalAmount(this.getTotalAmount());
        orderEntity.setDeliveryDate(this.getDeliveryDate());
        return orderEntity;
    }

    public Order fromEntity(OrderEntity entity) {
        this.setOrderId(entity.getOrderId());
        this.setUserId(entity.getUserId());
        this.setOrderDate(entity.getOrderDate());
        this.setShippingAddress(entity.getShippingAddress());
        this.setTotalAmount(entity.getTotalAmount());
        this.setDeliveryDate(entity.getDeliveryDate());
        return this;
    }


}