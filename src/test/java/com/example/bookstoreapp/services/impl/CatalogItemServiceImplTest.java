package com.example.bookstoreapp.services.impl;

import com.example.bookstoreapp.entities.CatalogItemEntity;
import com.example.bookstoreapp.exceptions.AppRuntimeException;
import com.example.bookstoreapp.models.CatalogItem;
import com.example.bookstoreapp.repositories.CatalogItemEntityRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CatalogItemServiceImplTest {
    @InjectMocks
    CatalogItemServiceImpl catalogItemServiceImpl;

    @Mock
    CatalogItemEntityRepository catalogItemEntityRepository;

    @Test
    void create() {

        CatalogItem catalogItem = new CatalogItem();
        catalogItem.setId(1L);

        CatalogItemEntity catalogItemEntity = catalogItem.toEntity();
        when(catalogItemEntityRepository.save(Mockito.any(CatalogItemEntity.class))).thenReturn(catalogItemEntity);

        CatalogItem createdCatalogItem = catalogItemServiceImpl.create(catalogItem);
        assertEquals(1L, createdCatalogItem.getId());

    }

    @Test
    void update() {

        CatalogItem catalogItem = new CatalogItem();
        Long catalogId = 1L;
        assertThrows(AppRuntimeException.class, () -> {
            catalogItemServiceImpl.update(catalogId, catalogItem);
        });

    }

    @Test
    void findById() {

        Long catalogItemId = 1L;
        CatalogItemEntity entity = new CatalogItemEntity();

        when(catalogItemEntityRepository.findById(catalogItemId)).thenReturn(Optional.of(entity));
        CatalogItem resultCatalogItem = catalogItemServiceImpl.findById(catalogItemId);

        Mockito.verify(catalogItemEntityRepository).findById(catalogItemId);
        assertNotNull(resultCatalogItem);

    }

    @Test
    void list() {

        CatalogItem catalogItem = new CatalogItem();
        CatalogItemEntity catalogItemEntity = catalogItem.toEntity();
        List<CatalogItemEntity> lists = new ArrayList<>();
        lists.add(catalogItemEntity);

        Mockito.when(catalogItemEntityRepository.findAll()).thenReturn(lists);
        List<CatalogItem> catalogItemList = catalogItemServiceImpl.list();

        assertNotNull(catalogItemList);
    }
}