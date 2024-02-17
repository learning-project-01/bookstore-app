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
        orderEntity.setOrderId(this.getOrder_id());
        orderEntity.setCustomerID(this.getCustomer_id());
        orderEntity.setOrderTime(this.getOrder_time());
        orderEntity.setTotalAmount(this.getTotal_amount());
        orderEntity.setShippingAddress(this.getShipping_address());
        return orderEntity;
    }

    public Order fromEntity(OrderEntity orderEntity) {
        this.setOrder_id(orderEntity.getOrderId());
        this.setCustomer_id(orderEntity.getCustomerID());
        this.setOrder_time(orderEntity.getOrderTime());
        this.setTotal_amount(orderEntity.getTotalAmount());
        this.setShipping_address(orderEntity.getShippingAddress());
        return this;
    }

}
