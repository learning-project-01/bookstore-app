package com.example.bookstoreapp.models;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public enum MediaType {
    IMG(0), VID(1);

    private static final Map<Integer, MediaType> valueVsType = new ConcurrentHashMap<>();
    private final int value;

    MediaType(int value) {
        this.value = value;
    }

    public static MediaType toMediaType(int value) {
        if (valueVsType.isEmpty()) {
            for (MediaType mediaType : MediaType.values()) {
                valueVsType.put(mediaType.value, mediaType);
            }
        }
        return valueVsType.get(value);
    }

    public int getValue() {
        return value;
    }
}
