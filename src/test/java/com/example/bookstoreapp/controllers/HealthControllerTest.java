package com.example.bookstoreapp.controllers;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.env.Environment;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class HealthControllerTest {
    @InjectMocks
    HealthController healthController;
    @Mock
    Environment environment;
    @Test
    void getHealth() {

        Mockito.when(environment.getProperty("application.name")).thenReturn("bookstore-app");
        Map<String, String> stringStringMap = healthController.getHealth();
        assertEquals("bookstore-app",environment.getProperty("application.name"));

    }
}