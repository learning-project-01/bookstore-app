package com.example.bookstoreapp.repositories;

import com.example.bookstoreapp.entities.ShoppingOrderEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ShoppingOrderEntityRepository extends CrudRepository<ShoppingOrderEntity, Long> {
  public List<ShoppingOrderEntity> findByCartId(long cartId);
}
