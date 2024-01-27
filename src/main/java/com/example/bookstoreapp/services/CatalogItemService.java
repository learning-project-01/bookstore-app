package com.example.bookstoreapp.services;

import com.example.bookstoreapp.models.CatalogItem;

import java.util.List;

public interface CatalogItemService {
  CatalogItem create(CatalogItem catalogItem);
  CatalogItem update(Long catalogItemId, CatalogItem catalogItem);

  CatalogItem findById(Long catalogItemId);

  List<CatalogItem> list();
}
