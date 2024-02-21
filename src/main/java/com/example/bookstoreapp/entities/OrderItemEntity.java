package com.example.bookstoreapp.entities;

import com.example.bookstoreapp.models.StatusCode;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Table(name = "order_item")
@Data
@NoArgsConstructor
public class OrderItemEntity {
  private Long id;
  private Long orderId;
  private Long catalogItemId;
  private Long cartId;
  private Integer quantity;
  private Float unitPrice;
  private Float total;
  private Date purchasedOn;
  private StatusCode statusCode;
}
