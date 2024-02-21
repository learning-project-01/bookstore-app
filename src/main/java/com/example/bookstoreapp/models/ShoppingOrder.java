package com.example.bookstoreapp.models;

import lombok.Data;

import java.util.List;

@Data
public class ShoppingOrder {
  private Long id;
  private Float totalAmount;
  private int totalItemCount;
  private List<OrderItem> orderItems;
  private String address;
}
