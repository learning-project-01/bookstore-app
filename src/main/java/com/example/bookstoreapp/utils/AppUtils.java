package com.example.bookstoreapp.utils;


import org.springframework.util.AntPathMatcher;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.UUID;

public class AppUtils {
  public static String getEncodedString(String rawStr){
    return new String(Base64.getEncoder().encode(rawStr.getBytes(StandardCharsets.UTF_8)));
  }
  public static String getDecodedString(String base64Str) {
    try {
      if (base64Str == null || base64Str.isEmpty()) {
        throw new IllegalArgumentException("Input string is null or empty");
      }

      byte[] decodedBytes = Base64.getDecoder().decode(base64Str);
      return new String(decodedBytes, StandardCharsets.UTF_8);
    } catch (IllegalArgumentException e) {
      // Log or handle the exception as needed
      e.printStackTrace(); // You can replace this with appropriate logging
      return null; // Or throw a custom exception or return a default value
    }
  }



  public static boolean matchesPattern(String pattern, String requestUri) {
    AntPathMatcher matcher = new AntPathMatcher();
    return matcher.match(pattern, requestUri);
  }
}
