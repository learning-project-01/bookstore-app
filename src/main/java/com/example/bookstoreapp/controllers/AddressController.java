package com.example.bookstoreapp.controllers;

import com.example.bookstoreapp.models.Address;
import com.example.bookstoreapp.services.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/address")
public class AddressController {

  @Autowired
  private AddressService addressService;

  @PostMapping
  public Address create(@RequestBody Address address){
    return addressService.create(address);
  }

  @GetMapping
  public List<Address> list(){
    return addressService.findUserAddress(0L);
  }

}
