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

import java.util.Base64;
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
    /**
     *
     * lookup db and comparing strings: cpu usage costly.
     * to avoid cpu usage we are using cache memory
     *
     * map.put(userEmail_1, uuid_1)
     *
     * if userEmail is in cache
     *   then return the existing UUID associated with userEmail
     *  else {
     *    what ever written below and put
     *    userEmail & UUID in cache
     *  }
     */
    UserEntity entity = userEntityRepository.findByEmail(user.getEmail());
    if(entity == null){
      throw new AppRuntimeException("login failed");
    }
    String encodedPassword = entity.getPassword();
    boolean passwordMatched = passwordEncoder.matches(user.getPassword(), encodedPassword);

    if(passwordMatched){
      String uuidString = UUID.randomUUID().toString();
      String base64EncodedUUID = Base64.getEncoder().encodeToString(uuidString.getBytes());
      return base64EncodedUUID;
    }
    throw new AppRuntimeException("login failed");
  }
}
