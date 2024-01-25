package com.example.bookstoreapp.utils;

import java.time.Instant;

public class IdGenerator2 {
    public static String hostId = "15"; // This is hardcoded here for checking we can define it in properties.

    public static String getGenLongID() {
        Instant instant = Instant.now();
        return instant.toString().substring(0, instant.toString().length() - hostId.length()) + hostId;
    }


}
