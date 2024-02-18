package com.example.bookstoreapp.models;

import com.example.bookstoreapp.entities.UserEntity;
import com.fasterxml.jackson.annotation.JsonSetter;
import lombok.Data;

@Data
public class User {
  private Long id;
  private String email;
  private char[] password;
  private String firstName;
  private String lastName;
  private UserRole role;

  public String getPassword() {
    if(this.password == null){
      this.password = new char[]{};
    }
    return new String(password);
  }

  @JsonSetter
  public void setPassword(String password) {
    if(password == null){
      password = "";
    }
    this.password = password.toCharArray();
    password = null;
  }

  public UserEntity toEntity() {
    UserEntity entity = new UserEntity();
    entity.setId(this.getId());
    entity.setEmail(this.getEmail());
    entity.setPassword(this.getPassword());
    entity.setFirstName(this.getFirstName());
    entity.setLastName(this.getLastName());
    entity.setRole(this.getRole().name());
    return entity;
  }

  public User fromEntity(UserEntity entity) {
    this.setId(entity.getId());
    this.setEmail(entity.getEmail());
    this.setPassword("[PROTECTED]");
    this.setFirstName(entity.getFirstName());
    this.setLastName(entity.getLastName());
    this.setRole(UserRole.valueOf(entity.getRole().toUpperCase()));
    return this;
  }
}
