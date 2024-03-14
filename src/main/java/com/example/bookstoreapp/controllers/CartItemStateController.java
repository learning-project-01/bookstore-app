package com.example.bookstoreapp.controllers;

import com.example.bookstoreapp.models.CartItemStateUpdate;
import com.example.bookstoreapp.services.CartItemStateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cart")
public class CartItemStateController {
    @Autowired
    private CartItemStateService cartItemStateService;
    @PutMapping("/{catalogItemId}/state")
    public CartItemStateUpdate updateState(@PathVariable Long catalogItemId, @RequestBody CartItemStateUpdate cartItemStateUpdate)
    {
        return cartItemStateService.updateStste(catalogItemId,cartItemStateUpdate);
    }

}
