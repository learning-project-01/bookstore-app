package com.example.bookstoreapp.entities;

import com.example.bookstoreapp.models.OrderItem;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.util.List;

@Entity
@Table(name = "shopping_order")
@Data
@NoArgsConstructor
public class ShoppingOrderEntity {
  private Long id;
  private Float totalAmount;
  private int totalItemCount;
  private Date orderDate;
  private List<OrderItem> orderItems;
  private String address;
}
