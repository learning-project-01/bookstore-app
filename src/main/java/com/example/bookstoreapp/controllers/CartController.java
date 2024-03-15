package com.example.bookstoreapp.controllers;

import com.example.bookstoreapp.models.Cart;
import com.example.bookstoreapp.models.CartItem;
import com.example.bookstoreapp.models.CartItemStateUpdate;
import com.example.bookstoreapp.services.CartItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cart")
public class CartController {

  @Autowired
  private CartItemService cartItemService;

  @PostMapping("/{catalogItemId}")
  public CartItem addItemToCart(@PathVariable("catalogItemId") Long catalogItemId){
    return cartItemService.addItem(catalogItemId);
  }

  @GetMapping
  public Cart getCartSummary(){
    return cartItemService.getCartSummary(0L);
  }

  @GetMapping("/checkout")
  public Cart checkout(){
    return cartItemService.doCheckout(0L);
  }
  @PutMapping("/{catalogItemId}/state")
  public CartItemStateUpdate updateState(@PathVariable Long catalogItemId, @RequestBody CartItem cartItem)
  {
    return cartItemService.updateState(catalogItemId,cartItem);
  }
}
