package com.example.bookstoreapp.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "user")
@Data
public class UserEntity {

    @Id
    private Long id;
    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private String role;

}
