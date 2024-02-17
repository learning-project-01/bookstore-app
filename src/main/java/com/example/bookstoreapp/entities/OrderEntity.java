package com.example.bookstoreapp.entities;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
@Data
public class OrderEntity {
    private Long orderId;
    private Long userId;
    private Date orderdate;
    private Integer addressId;
    private BigDecimal totalAmount;
    private Date deliveryDate;
}
