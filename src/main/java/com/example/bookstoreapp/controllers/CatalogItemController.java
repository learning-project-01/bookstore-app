package com.example.bookstoreapp.controllers;

import com.example.bookstoreapp.models.CatalogItem;
import com.example.bookstoreapp.services.CatalogItemService;
import io.opentelemetry.api.trace.Span;
import io.opentelemetry.api.trace.Tracer;
import io.opentelemetry.api.trace.SpanKind;
import io.opentelemetry.context.Scope;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/catalogItems") // URL is plural here i.e. catalogItems
public class CatalogItemController {

  @Autowired
  private CatalogItemService catalogItemService;

  @Autowired
  private Tracer tracer;

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  // as the item will be created after the execution of below method,
  // so we will return HTTPStatus as CREATED i.e. 201
  public CatalogItem createNewItem(@RequestBody CatalogItem catalogItem) {
    Span span = tracer.spanBuilder("CreateCatalogItem")
            .setSpanKind(SpanKind.SERVER)
            .startSpan();
    try (Scope scope = span.makeCurrent()) {
      span.setAttribute("action", "create");
      span.setAttribute("catalogItem.name", catalogItem.getName());
      return catalogItemService.create(catalogItem);
    } finally {
      span.end();
    }
  }

  @PutMapping("/{id}")
  @ResponseStatus(HttpStatus.OK)
  public CatalogItem updateItem(@PathVariable Long id, @RequestBody CatalogItem catalogItem) {
    Span span = tracer.spanBuilder("UpdateCatalogItem")
            .setSpanKind(SpanKind.SERVER)
            .startSpan();
    try (Scope scope = span.makeCurrent()) {
      span.setAttribute("action", "update");
      span.setAttribute("catalogItem.id", id);
      return catalogItemService.update(id, catalogItem);
    } finally {
      span.end();
    }
  }

  @GetMapping
  public List<CatalogItem> listItems() {
    Span span = tracer.spanBuilder("ListCatalogItems")
            .setSpanKind(SpanKind.SERVER)
            .startSpan();
    try (Scope scope = span.makeCurrent()) {
      span.setAttribute("action", "list");
      return catalogItemService.list();
    } finally {
      span.end();
    }
  }
}
