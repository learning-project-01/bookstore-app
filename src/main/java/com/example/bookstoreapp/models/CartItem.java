package com.example.bookstoreapp.models;


import com.example.bookstoreapp.entities.CartItemEntity;
import lombok.Data;

@Data
public class CartItem extends CatalogItem {

  private Long cartId;
  private int quantity;
  private Float total;
  private Float unitPrice;

  private CartItemState cartItemState;

  private boolean outOfStock;
  private String alertMessage; // some message like: item out of stock or 2 quantities available only


  public CartItem fromEntity(CartItemEntity cartItemEntity, CatalogItem catalogItem) {
    super.setId(catalogItem.getId());
    super.setName(catalogItem.getName());
    super.setPrice(catalogItem.getPrice());
    this.setCartId(cartItemEntity.getCartId());
    this.setCartItemState(CartItemState.mapWeighToState(cartItemEntity.getState()));
    this.setQuantity(cartItemEntity.getQuantity());
    this.setUnitPrice(catalogItem.getPrice());
    this.setTotal(this.getUnitPrice() * this.getQuantity());
    return this;
  }
}
