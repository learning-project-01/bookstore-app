package com.example.bookstoreapp.auth;

import com.example.bookstoreapp.models.User;

public interface AuthService {
  public String createAuthenticationContext(User user);
  public boolean destroyAuthenticationContext(String tokenStr);
  public void setUserContext(String tokenStr);
}
