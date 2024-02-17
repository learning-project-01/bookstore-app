package com.example.bookstoreapp.auth.impl;

import com.example.bookstoreapp.auth.AuthService;
import com.example.bookstoreapp.auth.cache.AuthCacheClient;
import com.example.bookstoreapp.auth.models.AuthenticationContext;
import com.example.bookstoreapp.exceptions.AppRuntimeException;
import com.example.bookstoreapp.models.User;
import com.example.bookstoreapp.services.UserContextService;
import com.example.bookstoreapp.utils.AppUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@Slf4j
public class AuthServiceImpl implements AuthService {

  @Value("${auth.token.ttl.minutes: 60}")
  private Long authTokenTtl;

  @Autowired
  private AuthCacheClient authCacheClient;

  @Autowired
  private UserContextService userContextService;
  public Long getAuthTokenTtl() {
    return authTokenTtl;
  }

  // Setter method for authTokenTtl
  public void setAuthTokenTtl(Long authTokenTtl) {
    this.authTokenTtl = authTokenTtl;
  }

  @Override
  public String createAuthenticationContext(User user) {
    AuthenticationContext context = new AuthenticationContext();
    context.setUser(user);
    context.setUserId(user.getEmail());
    context.setHeaderTokenUUID(UUID.randomUUID().toString());
    context.setExpiryAt(authTokenTtl * 60 * 1000 + System.currentTimeMillis());
    authCacheClient.put(context.getHeaderTokenUUID(), context);
    return AppUtils.getEncodedString(context.getHeaderTokenUUID());
  }

  @Override
  public boolean destroyAuthenticationContext(String tokenStr) {
    tokenStr = StringUtils.isEmpty(tokenStr) ? StringUtils.EMPTY : AppUtils.getDecodedString(tokenStr);
    AuthenticationContext context = authCacheClient.get(AppUtils.getDecodedString(tokenStr));
    if(context == null){
      log.warn("authentication context not found");
      return false;
    }
    authCacheClient.remove(context.getHeaderTokenUUID(), context.getUserId());
    log.warn("authentication context destroyed");
    return true;
  }

  @Override
  public void setUserContext(String tokenStr) {
    tokenStr = StringUtils.isEmpty(tokenStr) ? StringUtils.EMPTY : AppUtils.getDecodedString(tokenStr);
    AuthenticationContext context = authCacheClient.get(tokenStr);

    if(context == null){
      log.warn("authentication is null for token: {}", tokenStr);
      throw new AppRuntimeException("authentication context is empty");
    }

    if(System.currentTimeMillis() > context.getExpiryAt()){
      log.warn("authentication expired for user: {}", context.getUserId());
      throw new AppRuntimeException("token expired");
    }

    userContextService.setUser(context.getUser());
    log.info("user context set for user: {}", context.getUser());
  }
}
