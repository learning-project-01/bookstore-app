package com.example.bookstoreapp.repositories;

import com.example.bookstoreapp.entities.MediaEntity;
import org.springframework.data.repository.CrudRepository;

public interface MediaRepository extends CrudRepository<MediaEntity, Long> {
}
