package com.example.bookstoreapp.models;

import lombok.Data;

import java.util.Date;

@Data
public class OrderItem {
  private Long id;
  private Long orderId;
  private Long catalogItemId;
  private Long cartId;
  private int quantity;
  private Float unitPrice;
  private Float total;
  private Date purchasedOn;
  private StatusCode statusCode;
}
