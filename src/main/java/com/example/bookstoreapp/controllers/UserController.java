package com.example.bookstoreapp.controllers;

import com.example.bookstoreapp.models.User;
import com.example.bookstoreapp.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {

  @Autowired
  private UserService userService;

  @PostMapping("/signup")
  public User createUser(@RequestBody User user) {
    return userService.createUser(user);
  }
}
