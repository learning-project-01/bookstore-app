package com.example.bookstoreapp.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "orders")
@Data
@NoArgsConstructor
public class OrderEntity {
    @Id
    private long orderId;
    private long customerId;
    private long addressId;
    private LocalDateTime orderTime;
    private double totalAmount;
    private String shippingAddress;
}
