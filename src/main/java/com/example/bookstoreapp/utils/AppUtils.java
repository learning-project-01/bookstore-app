package com.example.bookstoreapp.utils;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.util.AntPathMatcher;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class AppUtils {

  private static final ObjectMapper MAPPER = new ObjectMapper();

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

  public static String toString(Object object){
    String result = null;
    try {
      result = MAPPER.writeValueAsString(object);
    } catch (JsonProcessingException e) {
      throw new RuntimeException(e);
    }
    return result;
  }
}
