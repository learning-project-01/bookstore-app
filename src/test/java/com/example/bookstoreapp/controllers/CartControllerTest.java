package com.example.bookstoreapp.controllers;

import com.example.bookstoreapp.models.Cart;
import com.example.bookstoreapp.models.CartItem;
import com.example.bookstoreapp.models.CartItemState;
import com.example.bookstoreapp.models.CartItemStateUpdate;
import com.example.bookstoreapp.services.CartItemService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyLong;

@ExtendWith(MockitoExtension.class)
class CartControllerTest {
    @InjectMocks
    CartController cartController;

    @Mock
    CartItemService cartItemService;


    @Test
    void addItemToCart() {

        CartItem cartItem = new CartItem();
        Mockito.when(cartItemService.addItem(anyLong())).thenReturn(cartItem);

        CartItem addedItem = cartController.addItemToCart(1L);

        Mockito.verify(cartItemService).addItem(1L);
        assertEquals(cartItem, addedItem);


    }

    @Test
    void getCartSummary() {
        Cart cart = new Cart();

        Mockito.when(cartItemService.getCartSummary(anyLong())).thenReturn(cart);
        Cart cartSummary = cartController.getCartSummary();
        Mockito.verify(cartItemService).getCartSummary(anyLong());
        assertEquals(cartSummary, cart);
    }

    @Test
    void checkout() {
        Cart cart = new Cart();
        Mockito.when(cartItemService.doCheckout(anyLong())).thenReturn(cart);
        Cart cartCheckout = cartController.checkout();
        assertEquals(cart, cartCheckout);
    }

    @Test
    void updateState() {
        Long id = System.nanoTime();
        CartItemStateUpdate stateUpdate = new CartItemStateUpdate();
        stateUpdate.setCatalogItemId(id);
        stateUpdate.setState(CartItemState.SAVE_LATER);
        cartController.updateState(id, stateUpdate);
    }
}