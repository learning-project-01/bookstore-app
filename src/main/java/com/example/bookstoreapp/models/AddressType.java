package com.example.bookstoreapp.models;

import java.util.HashMap;
import java.util.Map;

public enum AddressType {
    HOME(0), // this is home address and it will be  default
    WORK(1);  // this is for work address

    private static Map<Integer, AddressType> weighVsType = new HashMap<>();
    private final int weight;

    AddressType(int weight) {
        this.weight = weight;
    }

    public static AddressType mapWeightToType(int weight) {
        AddressType type = weighVsType.get(weight);
        if (type != null)
            return type;

        for (AddressType addressType : AddressType.values()) {
            weighVsType.put(addressType.getWeight(), addressType);
        }
        return weighVsType.get(weight);
    }

    public Integer getWeight() {
        return weight;
    }


}
