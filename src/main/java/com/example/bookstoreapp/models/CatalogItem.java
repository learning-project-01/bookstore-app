package com.example.bookstoreapp.models;

import com.example.bookstoreapp.entities.CatalogItemEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CatalogItem {

  private Long id;
  private String name;
  private Float price;
  private Integer stockQuantity;
  private Integer itemLimit;

  public CatalogItemEntity toEntity() {
    Integer itemLimits = (this.getItemLimit() == null) ? 0 : this.getItemLimit();
    return new CatalogItemEntity(this.getId(), this.getName(), this.getPrice(), this.getStockQuantity(), itemLimits);
  }

  public CatalogItem fromEntity(CatalogItemEntity entity) {
    this.setId(entity.getId());
    this.setName(entity.getName());
    this.setPrice(entity.getPrice());
    this.setStockQuantity(entity.getStockQuantity());
    this.setItemLimit(entity.getItemLimit());
    return this;
  }
}