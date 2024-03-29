package com.example.bookstoreapp.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Table(name = "order_item")
@Data
@NoArgsConstructor
public class OrderItemEntity {

  @Id
  private Long id;
  private Long orderId;
  private Long catalogItemId;
  private Long cartId;
  private Integer quantity;
  private Float unitPrice;
  private Float total;
  private Date purchasedOn;
  private int statusCode;
}
