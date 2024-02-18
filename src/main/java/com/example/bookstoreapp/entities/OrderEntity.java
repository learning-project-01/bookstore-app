package com.example.bookstoreapp.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
@Data
@Entity
public class OrderEntity {
    @Id
    private Long orderId;
    private Long userId;
    private Date orderdate;
    private Integer addressId;
    private BigDecimal totalAmount;
    private Date deliveryDate;
}
