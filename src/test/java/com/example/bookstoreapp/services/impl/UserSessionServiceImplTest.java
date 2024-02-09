package com.example.bookstoreapp.services.impl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class UserSessionServiceImplTest {
    @InjectMocks
    UserSessionServiceImpl userSessionServiceImps;

    @Test
    void getUserId() {
        assertEquals(434175530633000L, userSessionServiceImps.getUserId());
    }
}