package com.example.bookstoreapp.models;

import com.example.bookstoreapp.entities.CartItemEntity;
import lombok.Data;

import java.util.Optional;

@Data
public class CartItemStateUpdate {
    Long catalogItemId;
    int state;

    public CartItemEntity toEntity() {
        CartItemEntity cartItemEntity = new CartItemEntity();
        cartItemEntity.setState(this.getState());
        cartItemEntity.setCatalogItemId(this.getCatalogItemId());
        return cartItemEntity;
    }

    public CartItemStateUpdate fromEntity(Optional<CartItemEntity> cartItemEntity) {
        this.setState(cartItemEntity.get().getState());
        this.setCatalogItemId(cartItemEntity.get().getCatalogItemId());
        return this;
    }
}
