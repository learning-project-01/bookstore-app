package com.example.bookstoreapp.auth.cache;

import com.example.bookstoreapp.auth.models.AuthenticationContext;
import com.example.bookstoreapp.cache.AbstractCacheClient;
import org.springframework.stereotype.Component;

@Component
public class AuthCacheClient extends AbstractCacheClient<String, AuthenticationContext> {

  @Override
  public AuthenticationContext put(String key, AuthenticationContext value){
    return super.put(key, value);
  }

  public AuthenticationContext get(String key){
    return super.get(key);
  }

  public void remove(String... keys){
   super.remove(keys);
  }
}
