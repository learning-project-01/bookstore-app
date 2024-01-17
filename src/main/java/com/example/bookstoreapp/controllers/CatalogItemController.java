package com.example.bookstoreapp.controllers;

import com.example.bookstoreapp.models.CatalogItem;
import com.example.bookstoreapp.services.CatalogItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/catalogItems") // URL is plural here i.e. catalogItems
public class CatalogItemController {

    @Autowired
    private CatalogItemService catalogItemService;


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    // as the item will be created after the execution of below method,
    // so we will return HTTPStatus as CREATED i.e. 201
    public CatalogItem createNewItem(@RequestBody CatalogItem catalogItem) {
        return catalogItemService.create(catalogItem);
    }

    @GetMapping
    public List<CatalogItem> listItems() {
        return catalogItemService.list();
    }
}
