package com.example.bookstoreapp.models;

import com.example.bookstoreapp.entities.OrderEntity;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class Order {

    private Long orderId;
    private Long userId;
    private Date orderdate;
    private Integer addressId;
    private BigDecimal totalAmount;
    private Date deliveryDate;
    public OrderEntity toEntity() {
        OrderEntity orderEntity = new OrderEntity();
        orderEntity.setOrderId(this.getOrderId());
        orderEntity.setUserId(this.getUserId());
        orderEntity.setOrderdate(this.getOrderdate());
        orderEntity.setAddressId(this.getAddressId());
        orderEntity.setTotalAmount(this.getTotalAmount());
        orderEntity.setDeliveryDate(this.getDeliveryDate());
        return orderEntity;
    }

    public Order fromEntity(OrderEntity entity) {
        this.setOrderId(entity.getOrderId());
        this.setUserId(entity.getUserId());
        this.setOrderdate(entity.getOrderdate());
        this.setAddressId(entity.getAddressId());
        this.setTotalAmount(entity.getTotalAmount());
        this.setDeliveryDate(entity.getDeliveryDate());
        return this;
    }


}