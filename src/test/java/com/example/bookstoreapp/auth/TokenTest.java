package com.example.bookstoreapp.auth;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.UUID;


class TokenTest {

  @Test
  void setEncodedTokenValue() {
    Token token = new Token();
    String uuidValue = UUID.randomUUID().toString();
    token.setEncodedTokenValue(uuidValue);
    Assertions.assertEquals(uuidValue, token.getValue());
  }
}