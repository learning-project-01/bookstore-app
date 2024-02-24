package com.example.bookstoreapp.repositories;

import com.example.bookstoreapp.entities.OrderItemEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderItemEntityRepository extends CrudRepository<OrderItemEntity,Long> {
}
