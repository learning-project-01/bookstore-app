package com.example.bookstoreapp.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;
@Data
@Entity
@Table(name="orders")
public class OrderEntity {
    @Id
    private Long orderId;
    private Long userId;
    private LocalDate orderDate;
    private String shippingAddress;
    private Double totalAmount;
    private LocalDate deliveryDate;
}
