package com.example.bookstoreapp.controllers;

import io.opentelemetry.api.trace.Span;
import io.opentelemetry.api.trace.Tracer;
import io.opentelemetry.api.GlobalOpenTelemetry;
import io.opentelemetry.api.trace.SpanKind;
import io.opentelemetry.context.Scope;
import org.springframework.beans.factory.annotation.Autowired;
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

  @Autowired
  private Tracer tracer;


  @Value("${application.name}")
  private String appName;

  @GetMapping("/health")
  public Map<String, String> getHealth() {

    Span span = tracer.spanBuilder("HealthCheck")
            .setSpanKind(SpanKind.SERVER)
            .startSpan();

    try (Scope scope = span.makeCurrent()) {
      span.setAttribute("health.endpoint", "/api/health");
      span.setAttribute("application.name", appName);

      Map<String, String> map = new HashMap<>();
      map.put("appName", appName);
      map.put("systemTime", Instant.now().atZone(ZoneId.of("UTC")).toLocalDateTime().toString());

      return map;
    } finally {
      span.end();
    }
  }
}
