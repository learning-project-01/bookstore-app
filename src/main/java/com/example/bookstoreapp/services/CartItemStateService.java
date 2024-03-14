package com.example.bookstoreapp.services;

import com.example.bookstoreapp.models.CartItemStateUpdate;

public interface CartItemStateService {
    CartItemStateUpdate updateStste(Long catalogItemId, CartItemStateUpdate cartItemStateUpdate);
}
