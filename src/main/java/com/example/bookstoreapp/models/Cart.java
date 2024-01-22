package com.example.bookstoreapp.models;

import lombok.Data;

import java.util.List;

@Data
public class Cart {
  private Long cartId; // this is the userId
  private Float total; // this should be the total sum of
  private List<CartItem> items;
}
