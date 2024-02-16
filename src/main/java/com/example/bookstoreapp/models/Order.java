package com.example.bookstoreapp.models;

import com.example.bookstoreapp.entities.OrderEntity;
import lombok.Data;

import java.util.Date;

@Data
public class Order {
    private long order_id;
    private long customer_id;
    private Date order_time;
    private double total_amount;
    private String shipping_address;


    public OrderEntity toEntity() {
        OrderEntity orderEntity = new OrderEntity();
        orderEntity.setOrder_id(this.getOrder_id());
        orderEntity.setCustomer_id(this.getCustomer_id());
        orderEntity.setOrder_time(this.getOrder_time());
        orderEntity.setTotal_amount(this.getTotal_amount());
        orderEntity.setShipping_address(this.getShipping_address());
        return orderEntity;
    }

    public Order fromEntity(OrderEntity orderEntity) {
        this.setOrder_id(orderEntity.getOrder_id());
        this.setCustomer_id(orderEntity.getCustomer_id());
        this.setOrder_time(orderEntity.getOrder_time());
        this.setTotal_amount(orderEntity.getTotal_amount());
        this.setShipping_address(orderEntity.getShipping_address());
        return this;
    }

}
