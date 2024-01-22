package com.example.bookstoreapp.controllers;

import com.example.bookstoreapp.models.Cart;
import com.example.bookstoreapp.models.CartItem;
import com.example.bookstoreapp.services.CartItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
