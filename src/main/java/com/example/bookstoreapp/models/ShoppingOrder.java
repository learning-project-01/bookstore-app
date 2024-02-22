package com.example.bookstoreapp.models;

import com.example.bookstoreapp.entities.ShoppingOrderEntity;
import com.example.bookstoreapp.utils.IdGenerator;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class ShoppingOrder {

  private Long id;
  private Float totalAmount;
  private int totalItemCount;
  private Date orderDate;
  private List<OrderItem> orderItems;
  private String address;

  public ShoppingOrderEntity toEntity(){
    ShoppingOrderEntity entity = new ShoppingOrderEntity();
    entity.setId(IdGenerator.getLongId());
    entity.setTotalAmount(this.getTotalAmount());
    entity.setTotalItemCount(this.getTotalItemCount());
    entity.setOrderDate(this.getOrderDate());
    entity.setAddress(this.getAddress());
    return entity;
  }

  public ShoppingOrder fromEntity(ShoppingOrderEntity entity){
    this.setId(entity.getId());
    this.setTotalAmount(entity.getTotalAmount());
    this.setTotalItemCount(entity.getTotalItemCount());
    this.setOrderDate(entity.getOrderDate());
    this.setOrderDate(entity.getOrderDate());
    return this;
  }
}
