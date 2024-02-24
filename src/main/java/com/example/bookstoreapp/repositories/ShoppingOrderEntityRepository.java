package com.example.bookstoreapp.repositories;

import com.example.bookstoreapp.entities.OrderItemEntity;
import com.example.bookstoreapp.entities.ShoppingOrderEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShoppingOrderEntityRepository extends CrudRepository<ShoppingOrderEntity,Long> {
}
