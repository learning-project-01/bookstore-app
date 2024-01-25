package com.example.bookstoreapp.models;

import com.example.bookstoreapp.entities.CatalogItemEntity;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CatalogItemTest {

    @Test
    void toEntity() {
        CatalogItem catalogItem = new CatalogItem(1L, "Book1", 52.69F);
        CatalogItemEntity catalogItemEntity = catalogItem.toEntity();

        assertEquals(catalogItem.getName(),catalogItemEntity.getName());
        assertEquals(catalogItem.getId(),catalogItemEntity.getId());
        assertEquals(catalogItem.getPrice(),catalogItemEntity.getPrice());
    }

    @Test
    void fromEntity() {
        CatalogItemEntity catalogItemEntity = new CatalogItemEntity(1L, "Book1", 52.69F);
        CatalogItem catalogItem = new CatalogItem().fromEntity(catalogItemEntity);

        assertEquals(catalogItem.getName(),catalogItemEntity.getName());
        assertEquals(catalogItem.getId(),catalogItemEntity.getId());
        assertEquals(catalogItem.getPrice(),catalogItemEntity.getPrice());


    }

    @Test
    void getId() {
    }

    @Test
    void getName() {
    }

    @Test
    void getPrice() {
    }

    @Test
    void setId() {
    }

    @Test
    void setName() {
    }

    @Test
    void setPrice() {
    }

    @Test
    void testEquals() {
    }

}