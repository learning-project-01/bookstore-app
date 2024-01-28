package com.example.bookstoreapp.utils;

import java.util.UUID;

public class IdGenerator {

  public static Long getLongId() {
    return System.nanoTime();
  }

  public static String getUUID() {
    return UUID.randomUUID().toString();
  }
}
