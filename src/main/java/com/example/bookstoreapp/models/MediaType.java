package com.example.bookstoreapp.models;

import java.util.HashMap;
import java.util.Map;

public enum MediaType {
    IMAGE(0), VIDEO(1);
    private static final Map<Integer, MediaType> weightVsType = new HashMap<>();
    private final int weight;

    MediaType(int weight) {
        this.weight = weight;
    }

    public static MediaType mapWeighTotype(int weight) {
        MediaType type = weightVsType.get(weight);
        if (type != null) {
            return type;
        }
        for (MediaType mediaType : MediaType.values()) {
            weightVsType.put(mediaType.getWeight(), mediaType);
        }
        return weightVsType.get(weight);
    }

    public int getWeight() {
        return weight;
    }
}
