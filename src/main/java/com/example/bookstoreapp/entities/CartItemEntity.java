package com.example.bookstoreapp.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "cart_item")
@Data
public class CartItemEntity {

  @Id
  private Long id;
  private Long cartId;
  private Long catalogItemId;
  private int quantity;
  private int state;
}
