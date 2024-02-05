package com.example.bookstoreapp.models;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

class CartItemStateTest {

    @Test
    void testMapWeightToState() {
        assertEquals(CartItemState.IN_CART, CartItemState.mapWeighToState(0));
        assertEquals(CartItemState.SAVE_LATER, CartItemState.mapWeighToState(1));
        assertEquals(CartItemState.BUY_NOW, CartItemState.mapWeighToState(2));
    }
}
