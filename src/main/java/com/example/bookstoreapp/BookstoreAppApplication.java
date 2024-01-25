package com.example.bookstoreapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;

@SpringBootApplication
public class BookstoreAppApplication {
  @Configuration
  static class TestConfig {
    // Configuration for tests
  }
  public static void main(String[] args) {
    SpringApplication.run(BookstoreAppApplication.class, args);
  }

}
