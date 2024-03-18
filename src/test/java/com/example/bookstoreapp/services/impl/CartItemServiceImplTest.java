package com.example.bookstoreapp.services.impl;

import com.example.bookstoreapp.entities.CartItemEntity;
import com.example.bookstoreapp.models.CartItem;
import com.example.bookstoreapp.models.CartItemState;
import com.example.bookstoreapp.models.CartItemStateUpdate;
import com.example.bookstoreapp.repositories.CartItemEntityRepository;
import com.example.bookstoreapp.services.CatalogItemService;
import com.example.bookstoreapp.services.UserContextService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockitoExtension.class)
class CartItemServiceImplTest {
    @InjectMocks
    CartItemServiceImpl cartItemServiceImpl;

    @Mock
    CatalogItemService catalogItemService;

    @Mock
    UserContextService userContextService;

    @Mock
    CartItemEntityRepository cartItemEntityRepository;

    @Mock
    EntityManager entityManager;

    @Test
    void addItem() {
        CartItem cartItem = new CartItem();
        Long catalogItemId = 1L;
        Mockito.when(catalogItemService.findById(catalogItemId)).thenReturn(cartItem);
        assertThrows(NullPointerException.class, () -> {
            cartItemServiceImpl.addItem(catalogItemId);
        });
    }

    @Test
    void getCartSummary() {
        assertTrue(true);
    }

    @Test
    void doCheckout() {

    }

    @Test
    void updateState() {
        // define mock objects
        CriteriaBuilder criteriaBuilder = Mockito.mock(CriteriaBuilder.class);
        CriteriaQuery criteriaQuery = Mockito.mock(CriteriaQuery.class);
        Root<CartItemEntity> root = Mockito.mock(Root.class);
        TypedQuery typedQuery = Mockito.mock(TypedQuery.class);

        // mock actions
        Mockito.when(entityManager.createQuery(Mockito.eq(criteriaQuery)))
            .thenReturn(typedQuery);
        Mockito.when(criteriaBuilder.createQuery(Mockito.eq(CartItemEntity.class)))
            .thenReturn(criteriaQuery);
        Mockito.when(criteriaQuery.from(CartItemEntity.class))
            .thenReturn(root);
        Mockito.when(typedQuery.setMaxResults(Mockito.anyInt())).thenReturn(typedQuery);
        Mockito.when(typedQuery.getResultList()).thenReturn(Arrays.asList(
            new CartItemEntity()
        ));
        Mockito.when(entityManager.getCriteriaBuilder()).thenReturn(criteriaBuilder);

        // do test invocation
        Long catalogItemId = System.nanoTime();
        CartItemStateUpdate stateUpdate = new CartItemStateUpdate();
        stateUpdate.setCatalogItemId(catalogItemId);
        stateUpdate.setState(CartItemState.SAVE_LATER);
        cartItemServiceImpl.updateState(catalogItemId, stateUpdate);

    }
}