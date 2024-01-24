package com.example.bookstoreapp.controllers;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;
import java.time.ZoneId;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping
public class HealthController {

  @Value("${application.name}")
  private String appName;

  @GetMapping("/health")
  public Map<String, String> getHealth() {
    Map<String, String> map = new HashMap<>();
    map.put("appName", appName);
    map.put("systemTime", Instant.now().atZone(ZoneId.of("UTC")).toLocalDateTime().toString());
    return map;
  }
}
