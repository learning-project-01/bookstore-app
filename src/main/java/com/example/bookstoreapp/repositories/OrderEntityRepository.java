package com.example.bookstoreapp.repositories;

import com.example.bookstoreapp.entities.OrderEntity;
import org.springframework.data.repository.CrudRepository;

public interface OrderEntityRepository extends CrudRepository<OrderEntity, Long> {

}
