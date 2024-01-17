package com.example.bookstoreapp.services;

import com.example.bookstoreapp.models.CatalogItem;

import java.util.List;

public interface CatalogItemService {
    public CatalogItem create(CatalogItem catalogItem);

    public List<CatalogItem> list();
}
