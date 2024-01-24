package com.example.bookstoreapp.services.impl;

import com.example.bookstoreapp.services.UserSessionService;
import org.springframework.stereotype.Service;

@Service
public class UserSessionServiceImpl implements UserSessionService {
  @Override
  public Long getUserId() {
    return 434175530633000L;
  }
}
