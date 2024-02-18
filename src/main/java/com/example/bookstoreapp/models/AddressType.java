package com.example.bookstoreapp.models;

import java.util.HashMap;
import java.util.Map;

public enum AddressType {
    HOME(01), OFFICE(02);
    private static final Map<Integer, AddressType> weightVsType = new HashMap<>();
    private final int weight;

    AddressType(int weight) {
        this.weight = weight;
    }

    public static AddressType mapWeighToType(int weight) {
        AddressType type = weightVsType.get(weight);
        if (type != null) {
            return type;
        }
        for (AddressType addressType : AddressType.values()) {
            weightVsType.put(addressType.getWeight(), addressType);
        }
        return weightVsType.get(weight);
    }

    public int getWeight() {
        return weight;
    }
}

