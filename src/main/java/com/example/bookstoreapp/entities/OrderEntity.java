package com.example.bookstoreapp.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;
@Data
@Entity
public class OrderEntity {
    @Id
    private Long orderId;
    private Long userId;
    private LocalDate orderdate;
    private Integer addressId;
    private Double totalAmount;
    private LocalDate deliveryDate;
}
