package com.example.bookstoreapp.auth.models;

import com.example.bookstoreapp.models.User;
import lombok.Data;

@Data
public class AuthenticationContext {
  private String userId;
  private String headerTokenUUID;
  private Long expiryAt;
  private User user;
}
