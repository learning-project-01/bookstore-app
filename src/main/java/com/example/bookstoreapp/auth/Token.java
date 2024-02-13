package com.example.bookstoreapp.auth;

import lombok.Data;

@Data
public class Token {
  private static enum TokenType{
    ENCODED, JWT
  }
  private String type;
  private String value;

  public void setEncodedTokenValue(String value) {
    this.value = value;
    this.type = TokenType.ENCODED.name();
  }
}
