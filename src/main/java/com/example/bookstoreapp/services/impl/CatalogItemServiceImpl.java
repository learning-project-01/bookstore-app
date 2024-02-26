package com.example.bookstoreapp.services.impl;

import com.example.bookstoreapp.entities.CatalogItemEntity;
import com.example.bookstoreapp.exceptions.AppRuntimeException;
import com.example.bookstoreapp.models.CatalogItem;
import com.example.bookstoreapp.repositories.CatalogItemEntityRepository;
import com.example.bookstoreapp.services.CatalogItemService;
import com.example.bookstoreapp.utils.IdGenerator;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaUpdate;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;


@Service
public class CatalogItemServiceImpl implements CatalogItemService {


  @Autowired
  private CatalogItemEntityRepository catalogItemEntityRepository;

  @Autowired
  private EntityManager entityManager;

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


  @Override
  public int reduceStockCount(Map<Long, Integer> purchasedItemCount) {

    CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
    purchasedItemCount
        .entrySet()
        .stream()
        .forEach(e -> {
          reduceStock(criteriaBuilder, e.getKey(), e.getValue());
        });

    return 1;
  }

  private int reduceStock(CriteriaBuilder criteriaBuilder, Long itemId, int reduceCount) {

    CriteriaUpdate<CatalogItemEntity> updateQuery = criteriaBuilder.createCriteriaUpdate(CatalogItemEntity.class);
    Root<CatalogItemEntity> root = updateQuery.from(CatalogItemEntity.class);
    Predicate condition1 = criteriaBuilder.equal(root.get("id"), itemId);
    updateQuery.where(condition1);
    updateQuery.set("stockQuantity", criteriaBuilder.diff(root.get("stockQuantity"), reduceCount));
    int rowsUpdated = entityManager.createQuery(updateQuery).executeUpdate();
    return rowsUpdated;
  }

}
