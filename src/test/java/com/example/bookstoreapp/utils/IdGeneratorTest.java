package com.example.bookstoreapp.utils;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class IdGeneratorTest {

  @Test
  void getLongId() {
    long id = IdGenerator.getLongId();
    long id1 = IdGenerator.getLongId();

    assertNotNull(id);
    assertNotNull(id1);
    assertNotEquals(id, id1);
  }

  @Test
  void getUUID() {
    String uuid1 = IdGenerator.getUUID();
    String uuid2 = IdGenerator.getUUID();
    assertNotNull(uuid1);
    assertNotNull(uuid2);
    assertNotEquals(uuid1, uuid2);
    assertTrue(uuid1 instanceof String);
    assertTrue(uuid2 instanceof String);
  }
}