package com.example.bookstoreapp.services.impl;

import com.example.bookstoreapp.entities.CartItemEntity;
import com.example.bookstoreapp.exceptions.AppRuntimeException;
import com.example.bookstoreapp.models.Cart;
import com.example.bookstoreapp.models.CartItem;
import com.example.bookstoreapp.models.CartItemState;
import com.example.bookstoreapp.models.CatalogItem;
import com.example.bookstoreapp.repositories.CartItemEntityRepository;
import com.example.bookstoreapp.services.CartItemService;
import com.example.bookstoreapp.services.CatalogItemService;
import com.example.bookstoreapp.services.UserSessionService;
import com.example.bookstoreapp.utils.IdGenerator;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CartItemServiceImpl implements CartItemService {

  @Autowired
  private CatalogItemService catalogItemService;

  @Autowired
  private UserSessionService userSessionService;

  @Autowired
  private EntityManager entityManager;

  @Autowired
  private CartItemEntityRepository cartItemEntityRepository;

  @Override
  public CartItem addItem(Long catalogItemId) {
    CatalogItem catalogItem = catalogItemService.findById(catalogItemId);
    CartItemEntity savedCartItemEntity = findCartItemEntity(catalogItemId);
    if(catalogItem.getStockQuantity() <= 0){
      throw new AppRuntimeException("Item is out of stock");
    }
    if (savedCartItemEntity == null) { // create new entity
      CartItemEntity newCartItemEntity = new CartItemEntity();
      newCartItemEntity.setId(IdGenerator.getLongId());
      newCartItemEntity.setCartId(userSessionService.getUserId());
      newCartItemEntity.setQuantity(1);
      newCartItemEntity.setCatalogItemId(catalogItemId);
      newCartItemEntity.setState(CartItemState.BUY_NOW.getWeight());

      newCartItemEntity = cartItemEntityRepository.save(newCartItemEntity);

      return new CartItem().fromEntity(newCartItemEntity, catalogItem);
    }
    // increase the quantity
    savedCartItemEntity.setQuantity(savedCartItemEntity.getQuantity() + 1);
    savedCartItemEntity = cartItemEntityRepository.save(savedCartItemEntity);

    return new CartItem().fromEntity(savedCartItemEntity, catalogItem);
  }

  private CartItemEntity findCartItemEntity(Long catalogItemId) {
    // fetch from db using criteria query
    CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
    CriteriaQuery<CartItemEntity> criteriaQuery = criteriaBuilder.createQuery(CartItemEntity.class);
    Root<CartItemEntity> root = criteriaQuery.from(CartItemEntity.class);

    // Adding a predicate to the query to filter by catalogItemId
    Predicate predicate1 = criteriaBuilder.equal(root.get("catalogItemId"), catalogItemId);
    Predicate predicate2 = criteriaBuilder.equal(root.get("cartId"), userSessionService.getUserId());
    criteriaQuery.where(predicate1, predicate2);

    List<CartItemEntity> resultList = entityManager.createQuery(criteriaQuery)
        .setMaxResults(1)
        .getResultList();

    return resultList.isEmpty() ? null : resultList.get(0);
  }

  private List<CartItemEntity> findCartItemEntities() {
    // fetch from db using criteria query
    CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
    CriteriaQuery<CartItemEntity> criteriaQuery = criteriaBuilder.createQuery(CartItemEntity.class);
    Root<CartItemEntity> root = criteriaQuery.from(CartItemEntity.class);

    // Adding a predicate to the query to filter by cartId
    Predicate predicate = criteriaBuilder.equal(root.get("cartId"), userSessionService.getUserId());
    criteriaQuery.where(predicate);

    return entityManager.createQuery(criteriaQuery).getResultList();
  }

  @Override
  public Cart getCartSummary(Long cartId) {
    cartId = userSessionService.getUserId();
    List<CartItemEntity> cartItemEntities = findCartItemEntities();

    List<CartItem> cartItems = new ArrayList<>();
    for (CartItemEntity entity : cartItemEntities) {
      CatalogItem catalogItem = catalogItemService.findById(entity.getCatalogItemId());
      cartItems.add(new CartItem().fromEntity(entity, catalogItem));
    }

    Double buyItemsTotalPrice = cartItems.stream()
        .filter(e -> e.getCartItemState() == CartItemState.BUY_NOW)
        .mapToDouble(CartItem::getTotal)
        .sum();

    Cart cart = new Cart();
    cart.setCartId(cartId);
    cart.setTotal(buyItemsTotalPrice.floatValue());
    cart.setItems(cartItems);
    return cart;
  }
}
