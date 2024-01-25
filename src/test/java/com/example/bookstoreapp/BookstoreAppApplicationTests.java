package com.example.bookstoreapp;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest  (classes = BookstoreAppApplicationTests.class)
class BookstoreAppApplicationTests {

	@Test
	void contextLoads() {
		assertTrue(true, "Application context should load successfully.");
	}


}
