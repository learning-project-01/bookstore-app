package com.example.bookstoreapp.repositories;

import com.example.bookstoreapp.entities.OrderItemEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface OrderItemEntityRepository extends CrudRepository<OrderItemEntity, Long> {

  public List<OrderItemEntity> findByOrderId(long orderId);

}
