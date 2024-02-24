package com.example.bookstoreapp.repositories;

import com.example.bookstoreapp.entities.CartItemEntity;
import com.example.bookstoreapp.models.CartItem;
import org.springframework.data.repository.CrudRepository;

public interface CartItemEntityRepository extends CrudRepository<CartItemEntity, Long> {
    static void delete(CartItem cartItem) {
    }
}
