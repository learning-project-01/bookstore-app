package com.example.bookstoreapp.models;

import com.example.bookstoreapp.entities.CartItemEntity;
import lombok.Data;

import java.util.Optional;

@Data
public class CartItemStateUpdate {
    Long catalogItemId;
    CartItemState state;
}
