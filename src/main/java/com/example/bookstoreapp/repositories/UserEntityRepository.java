package com.example.bookstoreapp.repositories;

import com.example.bookstoreapp.entities.UserEntity;
import org.springframework.data.repository.CrudRepository;

public interface UserEntityRepository extends CrudRepository<UserEntity, Long> {
  public UserEntity findByEmail(String email);
}
