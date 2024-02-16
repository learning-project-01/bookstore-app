package com.example.bookstoreapp.auth;
import com.example.bookstoreapp.auth.cache.AuthCacheClient;
import com.example.bookstoreapp.auth.impl.AuthServiceImpl;
import com.example.bookstoreapp.auth.models.AuthenticationContext;
import com.example.bookstoreapp.exceptions.AppRuntimeException;
import com.example.bookstoreapp.models.User;
import com.example.bookstoreapp.services.UserContextService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.TestPropertySource;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
@TestPropertySource(properties = {"auth.token.ttl.minutes=60"})

class AuthServiceImplTest {

    @Mock
    private AuthCacheClient authCacheClient;


    @Mock
    private UserContextService userContextService;

    @InjectMocks
    private AuthServiceImpl authService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateAuthenticationContext() {
        // Given
        User user = new User();
        user.setEmail("test@example.com");

        // When
        String token = authService.createAuthenticationContext(user);

        // Then
        assertNotNull(token);
        verify(authCacheClient, times(1)).put(anyString(), any(AuthenticationContext.class));}


    @Test
    void testDestroyAuthenticationContext() {
        // Given
        String tokenStr = UUID.randomUUID().toString();
        AuthenticationContext context = new AuthenticationContext();
        when(authCacheClient.get(anyString())).thenReturn(context);

        // When
        boolean result = authService.destroyAuthenticationContext(tokenStr);

        // Then
        assertTrue(result);
        verify(authCacheClient, times(1)).remove(anyString(), anyString());
    }

    @Test
    void testDestroyAuthenticationContext_NotFound() {
        // Given
        String tokenStr = UUID.randomUUID().toString();
        when(authCacheClient.get(anyString())).thenReturn(null);

        // When
        boolean result = authService.destroyAuthenticationContext(tokenStr);

        // Then
        assertFalse(result);
        verify(authCacheClient, never()).remove(anyString(), anyString());
    }

    @Test
    void testSetUserContext() {
        // Given
        String tokenStr = UUID.randomUUID().toString();
        AuthenticationContext context = new AuthenticationContext();
        context.setExpiryAt(System.currentTimeMillis() + 10000);
        when(authCacheClient.get(anyString())).thenReturn(context);

        // When
        assertDoesNotThrow(() -> authService.setUserContext(tokenStr));

        // Then
        verify(userContextService, times(1)).setUser(any(User.class));
    }

    @Test
    void testSetUserContext_NullAuthenticationContext() {
        // Given
        String tokenStr = UUID.randomUUID().toString();
        when(authCacheClient.get(anyString())).thenReturn(null);

        // When, Then
        assertThrows(AppRuntimeException.class, () -> authService.setUserContext(tokenStr));
        verify(userContextService, never()).setUser(any(User.class));
    }

    @Test
    void testSetUserContext_ExpiredToken() {
        // Given
        String tokenStr = UUID.randomUUID().toString();
        AuthenticationContext context = new AuthenticationContext();
        context.setExpiryAt(System.currentTimeMillis() - 10000);
        when(authCacheClient.get(anyString())).thenReturn(context);

        // When, Then
        assertThrows(AppRuntimeException.class, () -> authService.setUserContext(tokenStr));
        verify(userContextService, never()).setUser(any(User.class));
    }
}

