package com.example.bookstoreapp.services.impl;

import com.example.bookstoreapp.entities.AddressEntity;
import com.example.bookstoreapp.models.Address;
import com.example.bookstoreapp.repositories.AddressRepository;
import com.example.bookstoreapp.services.AddressService;
import com.example.bookstoreapp.services.UserContextService;
import com.example.bookstoreapp.utils.IdGenerator;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AddressServiceImpl implements AddressService {

  @Autowired
  private AddressRepository addressRepository;

  @Autowired
  private EntityManager entityManager;

  @Autowired
  private UserContextService userContextService;

  @Override
  public Address create(Address address) {
    address.setId(IdGenerator.getLongId());
    address.setUserId(userContextService.getUserId());
    AddressEntity entity = address.toEntity();
    entity = addressRepository.save(entity);
    return address.fromEntity(entity);
  }

  @Override
  public Address updateAddress(Address address) {
    return null;
  }

  @Override
  public Address findById(Long id) {
    AddressEntity entity = addressRepository.findById(id).get();
    return new Address().fromEntity(entity);
  }

  @Override
  public List<Address> findUserAddress(Long userId) {
    CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
    CriteriaQuery<AddressEntity> criteriaQuery = criteriaBuilder.createQuery(AddressEntity.class);
    Root<AddressEntity> root = criteriaQuery.from(AddressEntity.class);

    // Adding a predicate to the query to filter by cartId
    Predicate predicate = criteriaBuilder.equal(root.get("userId"), userContextService.getUserId());
    criteriaQuery.where(predicate);

    List<AddressEntity> addressEntities = entityManager.createQuery(criteriaQuery).getResultList();

    List<Address> addresses = addressEntities
        .stream()
        .map(e -> {
          return new Address().fromEntity(e);
        })
        .toList();

    return addresses;
  }

  @Override
  public Long removeAddressById(Long id) {
    return null;
  }
}
