package com.example.bookstoreapp.services.impl;

import com.example.bookstoreapp.entities.CartItemEntity;
import com.example.bookstoreapp.exceptions.AppRuntimeException;
import com.example.bookstoreapp.models.*;
import com.example.bookstoreapp.repositories.CartItemEntityRepository;
import com.example.bookstoreapp.services.CartItemStateService;
import com.example.bookstoreapp.services.UserContextService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CartItemStateServiceImpl implements CartItemStateService {
    @Autowired
    private CartItemEntityRepository cartItemEntityRepository;
    @Autowired
    private UserContextService userContextService;
    @Autowired
    private CartItemServiceImpl cartItemServiceImpl;
    @Override
    public CartItemStateUpdate updateStste(Long catalogItemId, CartItemStateUpdate cartItemStateUpdate) {
        Optional<CartItemEntity> cartItemEntity = cartItemEntityRepository.findById(catalogItemId);
        if(cartItemEntity==null)
        {
            throw new AppRuntimeException("Item not found");
        }
        CartItemEntity updateItem = cartItemStateUpdate.toEntity();
        cartItemEntityRepository.save(updateItem);
        return new CartItemStateUpdate().fromEntity(cartItemEntity);
    }
}
