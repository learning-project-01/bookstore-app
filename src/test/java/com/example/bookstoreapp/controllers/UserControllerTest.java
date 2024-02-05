package com.example.bookstoreapp.controllers;

import com.example.bookstoreapp.models.User;
import com.example.bookstoreapp.services.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
// what is the purpose of @ExtendWith Annotation
  // why MockitoExtension.class is used here
class UserControllerTest {

  @InjectMocks // what does this annotation do
  private UserController userController;

  @Mock // what does this annotation do
  private UserService userService;

  @Test
  void authenticate() {
    User user = new User();
    user.setEmail("user@email.com");
    user.setPassword("password1");
    String uuid = UUID.randomUUID().toString();
    // what does below statement do?
    Mockito.when(userService.authenticate(Mockito.eq(user))).thenReturn(uuid);
    String token = userController.authenticate(user);
    assertEquals(uuid, token);
  }
}