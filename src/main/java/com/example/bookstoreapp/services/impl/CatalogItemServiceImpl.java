package com.example.bookstoreapp.services.impl;

import com.example.bookstoreapp.entities.CatalogItemEntity;
import com.example.bookstoreapp.exceptions.AppRuntimeException;
import com.example.bookstoreapp.models.CatalogItem;
import com.example.bookstoreapp.repositories.CatalogItemEntityRepository;
import com.example.bookstoreapp.services.CatalogItemService;
import com.example.bookstoreapp.utils.IdGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;


@Service
public class CatalogItemServiceImpl implements CatalogItemService {


  @Autowired
  private CatalogItemEntityRepository catalogItemEntityRepository;

  @Override
  public CatalogItem create(CatalogItem catalogItem) {
    catalogItem.setId(IdGenerator.getLongId());
    CatalogItemEntity savedEntity = catalogItemEntityRepository.save(catalogItem.toEntity());
    return new CatalogItem().fromEntity(savedEntity);
  }

  @Override
  public CatalogItem update(Long catalogItemId, CatalogItem catalogItem) {
    catalogItem.setId(catalogItemId);
    try {
      CatalogItem savedCatalogItem = findById(catalogItemId);
    } catch (NoSuchElementException e) {
      throw new AppRuntimeException("Item not found in catalog");
    }
    CatalogItemEntity savedEntity = catalogItemEntityRepository.save(catalogItem.toEntity());
    return new CatalogItem().fromEntity(savedEntity);
  }

  @Override
  public CatalogItem findById(Long catalogItemId) {
    CatalogItemEntity entity = catalogItemEntityRepository.findById(catalogItemId).get();
    return new CatalogItem().fromEntity(entity);
  }

  @Override
  public List<CatalogItem> list() {
    Iterable<CatalogItemEntity> entities = catalogItemEntityRepository.findAll();
    List<CatalogItem> items = new ArrayList<>();
    for (CatalogItemEntity entity : entities) {
      CatalogItem catalogItem = new CatalogItem().fromEntity(entity);
      items.add(catalogItem);
    }
    return items;
  }

}
