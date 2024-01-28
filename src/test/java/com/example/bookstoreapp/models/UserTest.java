package com.example.bookstoreapp.models;

import com.example.bookstoreapp.entities.UserEntity;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class UserTest {

    @Test
    void getPassword() {
        User user = new User();

        user.setPassword("password");
        String enpass = user.getPassword();

        assertEquals("password", enpass);
    }

    @Test
    void setPassword() {
        User user = new User();
        user.setPassword("MYPASS");
        assertEquals("MYPASS",user.getPassword());
    }

    @Test
    void toEntity() {
        User user = new User();
        user.setId(1L);
        user.setEmail("test@gmail.com");
        user.setPassword("password");
        user.setFirstName("user");
        user.setLastName("1");
        user.setRole(UserRole.CUSTOMER);

        UserEntity userEntity = user.toEntity();

        assertNotNull(userEntity);
        assertEquals(user.getId(), userEntity.getId());
        assertEquals(user.getEmail(), userEntity.getEmail());
        assertEquals(user.getPassword(), userEntity.getPassword());
        assertEquals(user.getFirstName(), userEntity.getFirstName());
        assertEquals(user.getLastName(), userEntity.getLastName());
        assertEquals(user.getRole().name(), userEntity.getRole());

    }

    @Test
    void fromEntity() {
        UserEntity useren = new UserEntity();
        useren.setId(1L);
        useren.setEmail("test@gmail.com");
        useren.setPassword("password");
        useren.setFirstName("user");
        useren.setLastName("1");
        useren.setRole("CUSTOMER");

        User user = new User().fromEntity(useren);

        assertNotNull(user);
        assertEquals(useren.getId(), user.getId());
        assertEquals(useren.getEmail(), user.getEmail());
        assertEquals("[PROTECTED]", user.getPassword());
        assertEquals(useren.getFirstName(), user.getFirstName());
        assertEquals(useren.getLastName(), user.getLastName());
        assertEquals(UserRole.CUSTOMER, user.getRole());

    }
}