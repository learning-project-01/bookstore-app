package com.example.bookstoreapp.models;

import java.util.HashMap;
import java.util.Map;

public enum StatusCode {

  PLACED(0),
  APPROVED(1),
  IN_TRANSIT(2),
  DELIVERY_COMPLETE(3),
  RETURN_IN_TRANSIT(4),
  RETURN_APPROVED(5),
  REFUNDED(6),
  RETURN_REQUESTED(7),
  RETURN_PICKED_UP(8),
  RETURN_RECIEVED(9);

  int value;

  StatusCode(int value) {
    this.value = value;
  }

  public int getValue() {
    return value;
  }

  private static final Map<Integer, StatusCode> valueVsStatusCode = new HashMap<>();
  
  public StatusCode toStatusCode(int value){
    if(valueVsStatusCode.isEmpty()){
      for(StatusCode statusCode: StatusCode.values()){
        valueVsStatusCode.put(statusCode.value, statusCode);
      }
    }
    return valueVsStatusCode.get(value);
  }

}
