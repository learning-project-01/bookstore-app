package com.example.bookstoreapp.services.impl;

import com.example.bookstoreapp.entities.CatalogItemEntity;
import com.example.bookstoreapp.models.CatalogItem;
import com.example.bookstoreapp.repositories.CatalogItemEntityRepository;
import com.example.bookstoreapp.services.CatalogItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class CatalogItemServiceImpl implements CatalogItemService {


    @Autowired
    private CatalogItemEntityRepository catalogItemEntityRepository;
    @Override
    public CatalogItem create(CatalogItem catalogItem) {
        catalogItem.setId(System.currentTimeMillis());
        CatalogItemEntity savedEntity = catalogItemEntityRepository.save(catalogItem.toEntity());
        return new CatalogItem().buildCatalogItem(savedEntity);
    }

    @Override
    public List<CatalogItem> list() {
      Iterable<CatalogItemEntity> entities = catalogItemEntityRepository.findAll();
      List<CatalogItem> items = new ArrayList<>();
      for(CatalogItemEntity entity: entities){
          CatalogItem catalogItem = new CatalogItem().buildCatalogItem(entity);
          items.add(catalogItem);
      }
      return items;
    }
}
