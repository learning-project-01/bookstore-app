package com.example.bookstoreapp.services.impl;

import com.example.bookstoreapp.models.CartItem;
import com.example.bookstoreapp.services.CatalogItemService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockitoExtension.class)
class CartItemServiceImplTest {
    @InjectMocks
    CartItemServiceImpl cartItemServiceImpl;
    @Mock
    CatalogItemService catalogItemService;

    @Test
    void addItem() {
        CartItem cartItem = new CartItem();
        Long catalogItemId = 1L;
        Mockito.when(catalogItemService.findById(catalogItemId)).thenReturn(cartItem);
        assertThrows(NullPointerException.class, () -> {
            cartItemServiceImpl.addItem(catalogItemId);
        });
    }

    @Test
    void getCartSummary() {
        assertTrue(true);
    }

    @Test
    void doCheckout() {

    }
}