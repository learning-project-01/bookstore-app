package com.example.bookstoreapp.models;

import lombok.Data;

@Data
public class CatalogItem {

    private Long id;
    private String name;
    private Float price;
}
