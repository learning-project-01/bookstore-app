package com.example.bookstoreapp.repositories;

import com.example.bookstoreapp.entities.ShoppingOrderEntity;
import org.springframework.data.repository.CrudRepository;

public interface ShoppingOrderEntityRepository extends CrudRepository<ShoppingOrderEntity, Long> {
}
