package com.example.bookstoreapp.repositories;

import com.example.bookstoreapp.entities.CatalogItemEntity;
import org.springframework.data.repository.CrudRepository;

public interface CatalogItemEntityRepository extends CrudRepository<CatalogItemEntity, Long> {
}
