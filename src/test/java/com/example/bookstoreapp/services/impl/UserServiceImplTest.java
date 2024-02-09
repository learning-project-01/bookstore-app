package com.example.bookstoreapp.services.impl;

import com.example.bookstoreapp.entities.UserEntity;
import com.example.bookstoreapp.exceptions.AppRuntimeException;
import com.example.bookstoreapp.models.User;
import com.example.bookstoreapp.models.UserRole;
import com.example.bookstoreapp.repositories.UserEntityRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {
    @InjectMocks
    UserServiceImpl userServiceImpl;
    @Mock
    UserEntityRepository userEntityRepository;
    @Mock
    PasswordEncoder passwordEncoder;

    @Test
    void createUser() {

        User user = Mockito.mock(User.class);
        UserEntity userEntity = Mockito.mock(UserEntity.class);
        Long userid = System.nanoTime();

        when(user.getPassword()).thenReturn("rawPassword");
        when(userEntity.getRole()).thenReturn(String.valueOf(UserRole.ADMIN));
        when(userEntity.getId()).thenReturn(userid);
        when(user.toEntity()).thenReturn(userEntity);
        when(userEntityRepository.save(any(UserEntity.class))).thenReturn(userEntity);

        User createdUser = userServiceImpl.createUser(user);
        System.out.println(createdUser);

        verify(user).toEntity();
        verify(userEntityRepository).save(any(UserEntity.class));

        assertEquals("[PROTECTED]", createdUser.getPassword());
        assertEquals(userid, createdUser.getId());
    }

    @Test
    void authenticate() {
        User user = Mockito.mock(User.class);
        Assertions.assertThrows(AppRuntimeException.class, () -> userServiceImpl.authenticate(user));
    }
}