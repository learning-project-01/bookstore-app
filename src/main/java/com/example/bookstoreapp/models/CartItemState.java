package com.example.bookstoreapp.models;

import java.util.HashMap;
import java.util.Map;

public enum CartItemState {
    IN_CART(0), // any SAVE_LATER must be IN_CART before BUY_NOW
    SAVE_LATER(1), // it is in cart but in SAVE_LATER group
    BUY_NOW(2); // inside cart select the cartItem to buy

    private static final Map<Integer, CartItemState> weightVsState = new HashMap<>();
    private final int weight;

    CartItemState(int weight) {
        this.weight = weight;
    }

    public static CartItemState mapWeighToState(int weight) {
        CartItemState state = weightVsState.get(weight);
        if (state != null) {
            return state;
        }
        for (CartItemState cartItemState : CartItemState.values()) {
            weightVsState.put(cartItemState.getWeight(), cartItemState);
        }
        return weightVsState.get(weight);
    }

    public int getWeight() {
        return weight;
    }
}
