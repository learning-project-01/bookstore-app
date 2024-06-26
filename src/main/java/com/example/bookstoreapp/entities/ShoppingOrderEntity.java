package com.example.bookstoreapp.entities;

import com.example.bookstoreapp.models.OrderItem;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Table(name = "shopping_order")
@Data
@NoArgsConstructor
public class ShoppingOrderEntity {

  @Id
  private Long id;
  private Float totalAmount;
  private Long cartId;
  private int totalItemCount;
  private Date orderDate;
  private String address;
}
