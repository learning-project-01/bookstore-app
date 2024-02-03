package com.example.bookstoreapp.services;

import com.example.bookstoreapp.models.Cart;
import com.example.bookstoreapp.models.CartItem;

public interface CartItemService {
  CartItem addItem(Long catalogItemId);

  Cart getCartSummary(Long cartId);

  Cart doCheckout(Long cartId);
}
