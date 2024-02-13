package com.example.bookstoreapp.services.impl;

import com.example.bookstoreapp.models.User;
import com.example.bookstoreapp.services.UserContextService;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;
import org.springframework.web.context.WebApplicationContext;

@Service
@Scope(value = WebApplicationContext.SCOPE_REQUEST,
    proxyMode = ScopedProxyMode.TARGET_CLASS)
public class UserContextServiceImpl implements UserContextService {

  private User user;

  @Override
  public Long getUserId() {
    return user.getId();
  }

  @Override
  public void setUser(User user) {
    if (this.user == null) {
      this.user = user;
    }
  }
}
