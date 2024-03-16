package com.example.bookstoreapp.services;

import com.example.bookstoreapp.models.Cart;
import com.example.bookstoreapp.models.CartItem;
import com.example.bookstoreapp.models.CartItemState;
import com.example.bookstoreapp.models.CartItemStateUpdate;

public interface CartItemService {
  CartItem addItem(Long catalogItemId);

  Cart getCartSummary(Long cartId);

  Cart doCheckout(Long cartId);

  int clearCartPostOrder();
  CartItemStateUpdate updateState(Long catalogItemId, CartItemStateUpdate state);

}
