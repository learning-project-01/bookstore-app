package com.example.bookstoreapp.auth;

import com.example.bookstoreapp.auth.cache.AuthCacheClient;
import com.example.bookstoreapp.auth.impl.AuthServiceImpl;
import com.example.bookstoreapp.auth.models.AuthenticationContext;
import com.example.bookstoreapp.exceptions.AppRuntimeException;
import com.example.bookstoreapp.models.User;
import com.example.bookstoreapp.services.UserContextService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Base64;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class AuthServiceImplTest {

  @InjectMocks
  private AuthServiceImpl authServiceImpl;

  @Mock
  private AuthCacheClient authCacheClient;

  @Mock
  private UserContextService userContextService;

  @BeforeEach
  public void beforeEach(){
    MockitoAnnotations.openMocks(this);
    ReflectionTestUtils.setField(authServiceImpl, "authTokenTtl", 60L);
  }
  @AfterEach
  public void afterEach(){
    Mockito.reset(authCacheClient, userContextService);
  }

  @Test
  void testCreateAuthenticationContext() {
    User user = new User();
    user.setEmail("test@example.com");
    String token = authServiceImpl.createAuthenticationContext(user);
    assertNotNull(token);
    verify(authCacheClient, times(1)).put(anyString(), any(AuthenticationContext.class));
  }

  @Test
  void testDestroyAuthenticationContext() {
    String tokenStr = UUID.randomUUID().toString();
    String base64Encoded = Base64.getEncoder().encodeToString(tokenStr.getBytes());
    AuthenticationContext context = new AuthenticationContext();
    context.setHeaderTokenUUID(tokenStr);
    when(authCacheClient.get(anyString())).thenReturn(context);
    boolean result = authServiceImpl.destroyAuthenticationContext(base64Encoded);
    assertTrue(result);
    verify(authCacheClient, times(1)).remove(any(String[].class));
  }

  @Test
  void testDestroyAuthenticationContext_NotFound() {
    String tokenStr = UUID.randomUUID().toString();
    String base64Encoded = Base64.getEncoder().encodeToString(tokenStr.getBytes());
    when(authCacheClient.get(anyString())).thenReturn(null);
    boolean result = authServiceImpl.destroyAuthenticationContext(base64Encoded);
    assertFalse(result);
    verify(authCacheClient, never()).remove(anyString(), anyString());
  }

  @Test
  void testSetUserContext() {
    String tokenStr = UUID.randomUUID().toString();
    String base64Encoded = Base64.getEncoder().encodeToString(tokenStr.getBytes());
    AuthenticationContext context = new AuthenticationContext();
    context.setHeaderTokenUUID(tokenStr);
    context.setExpiryAt(System.currentTimeMillis() + 10000);
    context.setUser(new User());
    when(authCacheClient.get(anyString())).thenReturn(context);
    assertDoesNotThrow(() -> authServiceImpl.setUserContext(base64Encoded));
    verify(userContextService, times(1)).setUser(any(User.class));
  }

  @Test
  void testSetUserContext_NullAuthenticationContext() {
    String tokenStr = UUID.randomUUID().toString();
    String base64Encoded = Base64.getEncoder().encodeToString(tokenStr.getBytes());
    when(authCacheClient.get(anyString())).thenReturn(null);
    assertThrows(AppRuntimeException.class, () -> authServiceImpl.setUserContext(base64Encoded));
    verify(userContextService, never()).setUser(any(User.class));
  }

  @Test
  void testSetUserContext_ExpiredToken() {
    String tokenStr = UUID.randomUUID().toString();
    String base64Encoded = Base64.getEncoder().encodeToString(tokenStr.getBytes());
    AuthenticationContext context = new AuthenticationContext();
    context.setExpiryAt(System.currentTimeMillis() - 10000);
    when(authCacheClient.get(anyString())).thenReturn(context);
    assertThrows(AppRuntimeException.class, () -> authServiceImpl.setUserContext(base64Encoded));
    verify(userContextService, never()).setUser(any(User.class));
  }
}

