package com.example.bookstoreapp.utils;

import java.util.UUID;

public class IdGenerator {

  public static Long getLongId() {
    return System.nanoTime();
  }

  private static String getUUID() {
    return UUID.randomUUID().toString();
  }
}