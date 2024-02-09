package com.example.bookstoreapp.services.impl;

import com.example.bookstoreapp.entities.AddressEntity;
import com.example.bookstoreapp.models.Address;
import com.example.bookstoreapp.repositories.AddressRepository;
import com.example.bookstoreapp.services.UserSessionService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AddressServiceImplTest {
    @InjectMocks
    AddressServiceImpl addressServiceImpl;
    @Mock
    UserSessionService userSessionService;
    @Mock
    AddressRepository addressRepository;
    @Mock
    EntityManager entityManager;
    @Mock
    CriteriaQuery<AddressEntity> criteriaQuery;

    @Test
    void create() {
        Address address = new Address();
        AddressEntity entity = address.toEntity();
        entity.setUserId(123L);
        entity.setId(1L);

        when(userSessionService.getUserId()).thenReturn(123L);
        when(addressRepository.save(any(AddressEntity.class))).thenReturn(entity);

        Address createdAdd = addressServiceImpl.create(address);

        assertEquals(1L, createdAdd.getId());
        assertEquals(123L, createdAdd.getUserId());

    }

    @Test
    void updateAddress() {
        Address address = new Address();
        Address add = addressServiceImpl.updateAddress(address);
        assertNull(add);

    }

    @Test
    void findById() throws NoSuchElementException {
        Long longId = 1L;

        AddressEntity addressEntity = new AddressEntity();
        addressEntity.setId(longId);
        when(addressRepository.findById(longId)).thenReturn(Optional.of(addressEntity));
        Address foundAdd = addressServiceImpl.findById(longId);
        assertEquals(longId, foundAdd.getId());

    }

    @Test
    void findUserAddress() {
        // AddressEntity addressEntity = new AddressEntity();
        // Long userId = 1L;
        // addressEntity.setUserId(userId);
        // when(userSessionService.getUserId()).thenReturn(userId);

        // CriteriaBuilder criteriaBuilder = mock(CriteriaBuilder.class);
        // criteriaQuery = criteriaBuilder.createQuery(AddressEntity.class);
        // Mockito.when(entityManager.getCriteriaBuilder()).thenReturn(criteriaBuilder);
        // Root<AddressEntity> root = mock(Root.class);
        // Mockito.when(criteriaQuery.from(AddressEntity.class)).thenReturn(root);

        // List<Address> foundAddresses = addressServiceImpl.findUserAddress(userId);

        // assertEquals(1, foundAddresses.size());
    }

    @Test
    void removeAddressById() {

        Long result = addressServiceImpl.removeAddressById(123L);
        assertNull(result);
    }
}
