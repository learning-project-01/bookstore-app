package com.example.bookstoreapp.services.impl;

import com.example.bookstoreapp.models.CatalogItem;
import com.example.bookstoreapp.services.CatalogItemService;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class CatalogItemServiceImpl implements CatalogItemService {

    @Override
    public CatalogItem create(CatalogItem catalogItem) {
        catalogItem.setId(System.currentTimeMillis());
        return catalogItem;
    }

    @Override
    public List<CatalogItem> list() {
        CatalogItem catalogItem = new CatalogItem();
        catalogItem.setId(System.currentTimeMillis());
        catalogItem.setName("Item Book One");
        catalogItem.setPrice(230.40f);
        return List.of(catalogItem);
    }
}
