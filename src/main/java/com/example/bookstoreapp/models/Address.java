package com.example.bookstoreapp.models;

import com.example.bookstoreapp.entities.AddressEntity;
import lombok.Data;

@Data
public class Address {

  private Long id;
  private Long userId;
  private String line1;
  private String line2;
  private String city;
  private String state;
  private String country;
  private String postalCode;


  public AddressEntity toEntity(){
    AddressEntity addressEntity = new AddressEntity();
    addressEntity.setId(this.getId());
    addressEntity.setUserId(this.getUserId());
    addressEntity.setLine1(this.getLine1());
    addressEntity.setLine2(this.getLine2());
    addressEntity.setCity(this.getCity());
    addressEntity.setState(this.getState());
    addressEntity.setCountry(this.getCountry());
    addressEntity.setPostalCode(this.getPostalCode());
    return addressEntity;
  }

  public Address fromEntity(AddressEntity entity){
    this.setId(entity.getId());
    this.setUserId(entity.getUserId());
    this.setLine1(entity.getLine1());
    this.setLine2(entity.getLine2());
    this.setCity(entity.getCity());
    this.setState(entity.getState());
    this.setCountry(entity.getCountry());
    this.setPostalCode(entity.getPostalCode());
    return this;
  }

}
