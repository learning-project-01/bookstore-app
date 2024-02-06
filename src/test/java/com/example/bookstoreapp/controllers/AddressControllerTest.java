package com.example.bookstoreapp.controllers;

import com.example.bookstoreapp.models.Address;
import com.example.bookstoreapp.services.AddressService;
import com.example.bookstoreapp.services.UserSessionService;
import com.example.bookstoreapp.utils.IdGenerator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(MockitoExtension.class)
class AddressControllerTest {

    @InjectMocks
    AddressController addressController;

    @Mock
    AddressService addressService;
    @Mock
    UserSessionService userSessionService;

    @Test
    void create() {
        Address address = new Address();
        Long addressID = IdGenerator.getLongId();
        address.setId(addressID);

        Mockito.when(userSessionService.getUserId()).thenReturn(434175530633000L);
        address.setUserId(userSessionService.getUserId());

        Mockito.when(addressService.create(Mockito.any(Address.class))).thenReturn(address);
        Address createdAddress = addressController.create(address);

        assertEquals(addressID, address.getId());
        assertEquals(434175530633000L, address.getUserId());

    }

    @Test
    void list() {
        Address address = new Address();
        address.setUserId(434175530633000L);
        List<Address> addressCollection = new ArrayList<>();
        Mockito.when(addressService.findUserAddress(0L)).thenReturn(addressCollection);

        List<Address> addressList = addressController.list();

        assertEquals(addressCollection, addressList);
        assertNotNull(addressList);
        assertEquals(0, addressList.size());

    }
}