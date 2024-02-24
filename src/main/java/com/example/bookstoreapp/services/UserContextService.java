package com.example.bookstoreapp.services;

import com.example.bookstoreapp.models.User;

public interface UserContextService {


  Long getUserId();

  void setUser(User user);

}
