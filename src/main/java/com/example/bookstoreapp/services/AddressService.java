package com.example.bookstoreapp.services;

import com.example.bookstoreapp.models.Address;

import java.util.List;

public interface AddressService {

  public Address create(Address address);

  public Address updateAddress(Address address);

  public Address findById(Long id);

  public List<Address> findUserAddress(Long userId);

  public Long removeAddressById(Long id);
}
