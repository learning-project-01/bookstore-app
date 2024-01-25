package com.example.bookstoreapp.utils;

import org.junit.jupiter.api.Test;

class IdGenerator2Test {

    @Test
    void getGenLongID() {

        System.out.println(IdGenerator2.getGenLongID());
        System.out.println(IdGenerator2.getGenLongID().length());
    }
}