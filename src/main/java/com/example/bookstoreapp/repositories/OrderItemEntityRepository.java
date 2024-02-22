package com.example.bookstoreapp.repositories;

import com.example.bookstoreapp.entities.OrderItemEntity;
import org.springframework.data.repository.CrudRepository;

public interface OrderItemEntityRepository extends CrudRepository<OrderItemEntity, Long> {
}
