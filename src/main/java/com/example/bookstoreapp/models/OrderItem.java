package com.example.bookstoreapp.models;

import com.example.bookstoreapp.entities.OrderItemEntity;
import com.example.bookstoreapp.utils.IdGenerator;
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

  public OrderItemEntity toEntity(Long orderId){
    OrderItemEntity entity = new OrderItemEntity();
    entity.setId(IdGenerator.getLongId());
    entity.setOrderId(orderId);
    entity.setCatalogItemId(getCatalogItemId());
    entity.setCartId(getCartId());
    entity.setQuantity(getQuantity());
    entity.setUnitPrice(getUnitPrice());
    entity.setTotal(getTotal());
    entity.setPurchasedOn(getPurchasedOn());
    entity.setStatusCode(getStatusCode().getValue());
    return entity;
  }

  public OrderItem fromEntity(OrderItemEntity entity){
    this.setId(entity.getId());
    this.setOrderId(entity.getOrderId());
    this.setCatalogItemId(entity.getCatalogItemId());
    this.setQuantity(entity.getQuantity());
    this.setUnitPrice(entity.getUnitPrice());
    this.setTotal(entity.getTotal());
    this.setPurchasedOn(entity.getPurchasedOn());
    this.setStatusCode(StatusCode.toStatusCode(entity.getStatusCode()));
    return this;
  }
}
