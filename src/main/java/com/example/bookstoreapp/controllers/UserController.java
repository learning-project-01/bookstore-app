package com.example.bookstoreapp.controllers;

import com.example.bookstoreapp.auth.AuthService;
import com.example.bookstoreapp.auth.Token;
import com.example.bookstoreapp.models.TokenRequest;
import com.example.bookstoreapp.models.User;
import com.example.bookstoreapp.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
@RestController
@RequestMapping("/user")
public class UserController {
  @Autowired
  private UserService userService;
  @Autowired
  private AuthService authService;
  @PostMapping("/signup")
  public User createUser(@RequestBody User user) {
    return userService.createUser(user);
  }

  @PostMapping("/login")
  public Token authenticate(@RequestBody User user){
    String tokenValue = userService.authenticate(user);
    Token token = new Token();
    token.setEncodedTokenValue(tokenValue);
    return token;
  }
  @PostMapping("/logout")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public boolean logout(@RequestBody TokenRequest tokenRequest) {
    boolean tokenDestroyed = authService.destroyAuthenticationContext(tokenRequest.getToken());
    return tokenDestroyed;
  }
}
