package com.example.bookstoreapp.entities;

import com.example.bookstoreapp.models.AddressType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "address")
@Data
public class AddressEntity {

  @Id
  private Long id;
  private Long userId;
  private String line1;
  private String line2;
  private String city;
  private String state;
  private String country;
  private String postalCode;
  private String addressType;

}