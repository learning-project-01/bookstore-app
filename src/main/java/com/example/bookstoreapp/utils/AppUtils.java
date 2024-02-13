package com.example.bookstoreapp.utils;


import org.springframework.util.AntPathMatcher;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.UUID;

public class AppUtils {
  public static String getEncodedString(String rawStr){
    return new String(Base64.getEncoder().encode(rawStr.getBytes(StandardCharsets.UTF_8)));
  }

  public static String getDecodedString(String base64Str){
    return new String(Base64.getDecoder().decode(base64Str.getBytes(StandardCharsets.UTF_8)));
  }

  public static boolean matchesPattern(String pattern, String requestUri) {
    AntPathMatcher matcher = new AntPathMatcher();
    return matcher.match(pattern, requestUri);
  }
}
