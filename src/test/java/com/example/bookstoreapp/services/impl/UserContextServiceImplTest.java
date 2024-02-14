package com.example.bookstoreapp.services.impl;

import com.example.bookstoreapp.models.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class UserContextServiceImplTest {

    private UserContextServiceImpl userContextService;

    @BeforeEach
    public void beforeEach(){
        userContextService = new UserContextServiceImpl();
        User user = new User();
        user.setId(434175530633000L);
        userContextService.setUser(user);
    }

    @Test
    void getUserId() {
        assertEquals(434175530633000L, userContextService.getUserId());
    }
}