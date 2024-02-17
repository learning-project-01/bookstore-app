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
import java.util.Base64;
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
    private AuthServiceImpl authServiceImpl;
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }
    @Test
    void testCreateAuthenticationContext() {
        User user = new User();
        user.setEmail("test@example.com");
        String token = authServiceImpl.createAuthenticationContext(user);
        assertNotNull(token);
        verify(authCacheClient, times(1)).put(anyString(), any(AuthenticationContext.class));}
    @Test
    void testDestroyAuthenticationContext() {
        String tokenStr = UUID.randomUUID().toString();
        String base64Encoded = Base64.getEncoder().encodeToString(tokenStr.getBytes());
        AuthenticationContext context = new AuthenticationContext();
        when(authCacheClient.get(anyString())).thenReturn(context);
        boolean result = authServiceImpl.destroyAuthenticationContext(base64Encoded);
        assertTrue(result);
        verify(authCacheClient, times(1)).remove( anyString());
    }
   @Test
    void testDestroyAuthenticationContext_NotFound() {
        String tokenStr = UUID.randomUUID().toString();
        when(authCacheClient.get(anyString())).thenReturn(null);
        boolean result = authServiceImpl.destroyAuthenticationContext(tokenStr);
        assertFalse(result);
        verify(authCacheClient, never()).remove(anyString(), anyString());
    }
    @Test
    void testSetUserContext() {
        String tokenStr = UUID.randomUUID().toString();
        AuthenticationContext context = new AuthenticationContext();
        context.setExpiryAt(System.currentTimeMillis() + 10000);
        when(authCacheClient.get(anyString())).thenReturn(context);
        assertDoesNotThrow(() -> authServiceImpl.setUserContext(tokenStr));
        verify(userContextService, times(1)).setUser(any(User.class));
    }
   @Test
    void testSetUserContext_NullAuthenticationContext() {
        String tokenStr = UUID.randomUUID().toString();
        when(authCacheClient.get(anyString())).thenReturn(null);
        assertThrows(AppRuntimeException.class, () -> authServiceImpl.setUserContext(tokenStr));
        verify(userContextService, never()).setUser(any(User.class));
    }
    @Test
    void testSetUserContext_ExpiredToken() {
        String tokenStr = UUID.randomUUID().toString();
        AuthenticationContext context = new AuthenticationContext();
        context.setExpiryAt(System.currentTimeMillis() - 10000);
        when(authCacheClient.get(anyString())).thenReturn(context);
        assertThrows(AppRuntimeException.class, () -> authServiceImpl.setUserContext(tokenStr));
        verify(userContextService, never()).setUser(any(User.class));
    }
}

