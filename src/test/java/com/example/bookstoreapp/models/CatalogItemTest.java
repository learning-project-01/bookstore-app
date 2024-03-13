package com.example.bookstoreapp.models;

import com.example.bookstoreapp.entities.CatalogItemEntity;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CatalogItemTest {

  @Test
  void toEntity() {
    CatalogItem catalogItem = new CatalogItem(1L, "Book1", 52.69F, 10, 5);
    CatalogItemEntity catalogItemEntity = catalogItem.toEntity();

        assertEquals(catalogItem.getName(), catalogItemEntity.getName());
        assertEquals(catalogItem.getId(), catalogItemEntity.getId());
        assertEquals(catalogItem.getPrice(), catalogItemEntity.getPrice());
    }

  @Test
  void fromEntity() {
    CatalogItemEntity catalogItemEntity = new CatalogItemEntity(1L, "Book1", 52.69F, 20, 5);
    CatalogItem catalogItem = new CatalogItem().fromEntity(catalogItemEntity);

        assertEquals(catalogItem.getName(), catalogItemEntity.getName());
        assertEquals(catalogItem.getId(), catalogItemEntity.getId());
        assertEquals(catalogItem.getPrice(), catalogItemEntity.getPrice());
  }

}