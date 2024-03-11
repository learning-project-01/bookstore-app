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
import com.example.bookstoreapp.services.UserContextService;
import com.example.bookstoreapp.utils.IdGenerator;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class CartItemServiceImpl implements CartItemService {

    @Autowired
    private CatalogItemService catalogItemService;

    @Autowired
    private UserContextService userContextService;

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private CartItemEntityRepository cartItemEntityRepository;

    @Override
    public CartItem addItem(Long catalogItemId) {
        CatalogItem catalogItem = catalogItemService.findById(catalogItemId);
        CartItemEntity savedCartItemEntity = findCartItemEntity(catalogItemId);
        if (catalogItem.getStockQuantity() <= 0) {
            throw new AppRuntimeException("Item is out of stock");
        }
        if (savedCartItemEntity == null) { // create new entity
            CartItemEntity newCartItemEntity = new CartItemEntity();
            newCartItemEntity.setId(IdGenerator.getLongId());
            newCartItemEntity.setCartId(userContextService.getUserId());
            newCartItemEntity.setQuantity(1);
            newCartItemEntity.setCatalogItemId(catalogItemId);
            newCartItemEntity.setState(CartItemState.BUY_NOW.getWeight());

            newCartItemEntity = cartItemEntityRepository.save(newCartItemEntity);

            return new CartItem().fromEntity(newCartItemEntity, catalogItem);
        }

        /* An item can have two states : One in which it has a limit & the other in which it has no limit
        if item doesn't have any limit = In database (item_limit = 0)
        if item has limit = In database item_limit is set to the limit count
         */
        // When there is no item limit
        if (catalogItem.getItemLimit() == 0) {
            // increase the quantity
            savedCartItemEntity.setQuantity(savedCartItemEntity.getQuantity() + 1);
            savedCartItemEntity = cartItemEntityRepository.save(savedCartItemEntity);
        }
        // When there is a specific item limit present
        if (catalogItem.getItemLimit() != 0) {
            if (savedCartItemEntity.getQuantity() >= catalogItem.getItemLimit())
                throw new AppRuntimeException("You have reached the maximum limit for this item");

            // increase the quantity
            savedCartItemEntity.setQuantity(savedCartItemEntity.getQuantity() + 1);
            savedCartItemEntity = cartItemEntityRepository.save(savedCartItemEntity);
        }

        return new CartItem().fromEntity(savedCartItemEntity, catalogItem);
    }

    private CartItemEntity findCartItemEntity(Long catalogItemId) {
        // fetch from db using criteria query
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<CartItemEntity> criteriaQuery = criteriaBuilder.createQuery(CartItemEntity.class);
        Root<CartItemEntity> root = criteriaQuery.from(CartItemEntity.class);

        // Adding a predicate to the query to filter by catalogItemId
        Predicate predicate1 = criteriaBuilder.equal(root.get("catalogItemId"), catalogItemId);
        Predicate predicate2 = criteriaBuilder.equal(root.get("cartId"), userContextService.getUserId());
        criteriaQuery.where(predicate1, predicate2);

        List<CartItemEntity> resultList = entityManager.createQuery(criteriaQuery).setMaxResults(1).getResultList();

        return resultList.isEmpty() ? null : resultList.get(0);
    }

    private List<CartItemEntity> findCartItemEntities(CartItemState cartItemState) {
        // fetch from db using criteria query
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<CartItemEntity> criteriaQuery = criteriaBuilder.createQuery(CartItemEntity.class);
        Root<CartItemEntity> root = criteriaQuery.from(CartItemEntity.class);

        List<Predicate> predicateList = new ArrayList<>();
        // Adding a predicate to the query to filter by cartId
        Predicate cartIdPredicate = criteriaBuilder.equal(root.get("cartId"), userContextService.getUserId());
        predicateList.add(cartIdPredicate);
        if (cartItemState != null) {
            Predicate statePredicate = criteriaBuilder.equal(root.get("state"), cartItemState.getWeight());
            predicateList.add(statePredicate);
        }
        criteriaQuery.where(predicateList.toArray(new Predicate[0]));
        // select * from cart_item where cart_id=?
        // select * from cart_item where cart_id=? and state=?

        return entityManager.createQuery(criteriaQuery).getResultList();
    }

    @Override
    public Cart getCartSummary(Long cartId) {
        return getCartSummary(cartId, null);
    }

    private Cart getCartSummary(Long cartId, CartItemState cartItemState) {
        cartId = userContextService.getUserId();
        List<CartItemEntity> cartItemEntities = findCartItemEntities(cartItemState);

        List<CartItem> cartItems = new ArrayList<>();
        for (CartItemEntity entity : cartItemEntities) {
            CatalogItem catalogItem = catalogItemService.findById(entity.getCatalogItemId());
            cartItems.add(new CartItem().fromEntity(entity, catalogItem));
        }

        Double buyItemsTotalPrice = cartItems.stream().filter(e -> e.getCartItemState() == CartItemState.BUY_NOW).mapToDouble(CartItem::getTotal).sum();

        Cart cart = new Cart();
        cart.setCartId(cartId);
        cart.setTotal(buyItemsTotalPrice.floatValue());
        cart.setItems(cartItems);
        return cart;
    }

    @Override
    public Cart doCheckout(Long cartId) {
        Cart cart = getCartSummary(cartId, CartItemState.BUY_NOW);
        if (cart.getItems().isEmpty()) {
            throw new AppRuntimeException("none of the item in cart is for BUY_NOW");
        }
        return cart;
    }

    @Override
    public int clearCartPostOrder() {

        int state = CartItemState.BUY_NOW.getWeight();
        Long cartId = userContextService.getUserId();

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaDelete<CartItemEntity> deleteQuery = criteriaBuilder.createCriteriaDelete(CartItemEntity.class);
        Root<CartItemEntity> root = deleteQuery.from(CartItemEntity.class);

        // Define delete condition
        Predicate condition1 = criteriaBuilder.equal(root.get("cartId"), cartId);
        Predicate condition2 = criteriaBuilder.equal(root.get("state"), state);
        deleteQuery.where(criteriaBuilder.and(condition1, condition2));// this is AND operator with Criteria

        // DELETE FROM TBL_NAME WHERE CART_ID=? AND STATE=?
        // Execute delete query
        int deletedRowCount = entityManager.createQuery(deleteQuery).executeUpdate();

        log.info("clear items from cart: {} with state: {}", cartId, state);

        return deletedRowCount;
    }
}
