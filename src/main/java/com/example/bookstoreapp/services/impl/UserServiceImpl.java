package com.example.bookstoreapp.services.impl;

import com.example.bookstoreapp.entities.UserEntity;
import com.example.bookstoreapp.exceptions.AppRuntimeException;
import com.example.bookstoreapp.models.User;
import com.example.bookstoreapp.repositories.UserEntityRepository;
import com.example.bookstoreapp.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {

  @Autowired
  private UserEntityRepository userEntityRepository;

  private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

  @Override
  public User createUser(User user) {
    user.setId(System.nanoTime());
    String rawPassword = user.getPassword();
    user.setPassword(passwordEncoder.encode(rawPassword)); // hashing
    UserEntity userEntity = userEntityRepository.save(user.toEntity());
    return new User().fromEntity(userEntity);
  }

  @Override
  public String authenticate(User user) {
    UserEntity entity = userEntityRepository.findByEmail(user.getEmail());
    String encodedPassword = entity.getPassword();
    boolean passwordMatched = passwordEncoder.matches(user.getPassword(), encodedPassword);
    if(passwordMatched){
      return UUID.randomUUID().toString();
    }
    throw new AppRuntimeException("login failed");
  }
}
