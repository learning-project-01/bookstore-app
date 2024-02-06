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

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
        /* what is the purpose of @ExtendWith Annotation ? &  why MockitoExtension.class is used here ? - This is the annotation of Junit5 , this is used to register the MockitoExtension with test class, The MockitoExtension helps to set up and initialize the mocks for the test */
class UserControllerTest {

    /* what does @InjectMocks annotation do ? - This is used to add mock dependencies, into the instance of class under test.The mock dependencies are marked with @Mock and the class under test here is UserController. Mockito create an instance of the class and inject the mocked dependencies to it */
    @InjectMocks
    private UserController userController;

    @Mock
    /* what does this annotation do ? - Mock is used to create an instance of the class UserService , the mock is just an object which simulates same behaviour as a real object */
    private UserService userService;

    @Test
    void authenticate() {
        User user = new User();

        String uuid = UUID.randomUUID().toString();
        // what does below statement do? - when the userService object invokes the authenticate method with certain object i.e. user here it will return the predefined uuid string
        Mockito.when(userService.authenticate(Mockito.eq(user))).thenReturn(uuid);
        // Mockito will return the uuid when we invoke it with a user object so when pass the object in the method it will always return the uuid
        String token = userController.authenticate(user);
        assertEquals(uuid, token);
    }

    @Test
    void createUser() {
        User user1 = new User();
        user1.setEmail("user@gmail.com");
        user1.setPassword("password1");

        Mockito.when(userService.createUser(Mockito.any(User.class))).thenReturn(user1);
        userController.createUser(user1);
        assertEquals("user@gmail.com", user1.getEmail());
        assertEquals("password1", user1.getPassword());

    }
}