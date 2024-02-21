package com.example.bookstoreapp.models;

public enum StatusCode {

  PLACED(0),
  APPROVED(1),
  IN_TRANSIT(2),
  DELIVERY_COMPLETE(3),
  REFUND_IN_TRANSIT(4),
  REFUND_APPROVED(5),
  REFUNDED(6);

  int weight;

  StatusCode(int weight) {
    this.weight = weight;
  }
}
