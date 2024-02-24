package com.example.bookstoreapp.services;

import com.example.bookstoreapp.models.CatalogItem;

import java.util.List;
import java.util.Map;

public interface CatalogItemService {
  CatalogItem create(CatalogItem catalogItem);
  CatalogItem update(Long catalogItemId, CatalogItem catalogItem);

  CatalogItem findById(Long catalogItemId);

  List<CatalogItem> list();

  int reduceStockCount(Map<Long, Integer> purchasedItemCount);
}
