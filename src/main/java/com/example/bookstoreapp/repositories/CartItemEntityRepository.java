package com.example.bookstoreapp.repositories;

import com.example.bookstoreapp.entities.CartItemEntity;
import org.springframework.data.repository.CrudRepository;

public interface CartItemEntityRepository extends CrudRepository<CartItemEntity, Long> {
}
