package com.example.bookstoreapp.auth.cache;

import com.example.bookstoreapp.auth.models.AuthenticationContext;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class AuthCacheClient {
  private static final Map<String, AuthenticationContext> tokenMap = new ConcurrentHashMap<>();

  public String put(String key, AuthenticationContext context){
    tokenMap.put(key, context);
    return key;
  }

  public AuthenticationContext get(String key){
    return tokenMap.get(key);
  }

  public void remove(String... keys){
    for(String key: keys){
      tokenMap.remove(key);
    }
  }
}
