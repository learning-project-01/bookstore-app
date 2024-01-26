package com.example.bookstoreapp.repositories;

import com.example.bookstoreapp.entities.AddressEntity;
import org.springframework.data.repository.CrudRepository;

public interface AddressRepository extends CrudRepository<AddressEntity, Long> {
}
