package com.example.bookstoreapp.entities;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Date;
@Entity
@Data
@NoArgsConstructor
@Table(name = "order_item")
public class OrderItemEntity {
    @Id
    private Long id;
    private Long orderId;
    private Long catalogItemId;
    private Long cartId;
    private int quantity;
    private float total;
    private float unitPrice;
    private Date purchasedOn;
    private int statusCode;
}
