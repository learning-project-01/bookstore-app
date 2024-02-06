package com.example.bookstoreapp.controllers;

import com.example.bookstoreapp.models.CatalogItem;
import com.example.bookstoreapp.services.CatalogItemService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(MockitoExtension.class)
class CatalogItemControllerTest {
    @InjectMocks
    CatalogItemController catalogItemController;
    @Mock
    CatalogItemService catalogItemService;

    @Test
    void createNewItem() {
        CatalogItem catalogItem = new CatalogItem();
        Mockito.when(catalogItemService.create(Mockito.any(CatalogItem.class))).thenReturn(catalogItem);
        CatalogItem createdCatalogItem = catalogItemController.createNewItem(catalogItem);
        assertEquals(createdCatalogItem, catalogItem);
    }

    @Test
    void updateItem() {
        CatalogItem catalogItem = new CatalogItem();
        Mockito.when(catalogItemService.update(1L, catalogItem)).thenReturn(catalogItem);
        CatalogItem updatedCatalogItem = catalogItemController.updateItem(1L, catalogItem);
        assertEquals(updatedCatalogItem, catalogItem);
    }

    @Test
    void listItems() {
        List<CatalogItem> catalogItemList = new ArrayList<>();
        Mockito.when(catalogItemService.list()).thenReturn(catalogItemList);
        List<CatalogItem> listItems = catalogItemController.listItems();
        assertNotNull(listItems);
        assertEquals(listItems, catalogItemList);

    }
}