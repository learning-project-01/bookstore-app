package com.example.bookstoreapp;

import io.opentelemetry.api.OpenTelemetry;
import io.opentelemetry.api.common.Attributes;
import io.opentelemetry.api.trace.Tracer;
import io.opentelemetry.exporter.zipkin.ZipkinSpanExporter;
import io.opentelemetry.sdk.OpenTelemetrySdk;
import io.opentelemetry.sdk.resources.Resource;
import io.opentelemetry.sdk.trace.SdkTracerProvider;
import io.opentelemetry.sdk.trace.export.BatchSpanProcessor;
import io.opentelemetry.sdk.trace.export.SimpleSpanProcessor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;


@SpringBootApplication
public class BookstoreAppApplication {

  public static void main(String[] args) {
    SpringApplication.run(BookstoreAppApplication.class, args);
  }


  @Bean
  public OpenTelemetry openTelemetry() {
    ZipkinSpanExporter zipkinExporter = ZipkinSpanExporter.builder()
            .setEndpoint("http://localhost:9411/api/v2/spans")
            .build();

    Resource serviceNameResource = Resource.create(
            Attributes.builder()
                    .put("service.name", "bookstore-app")
                    .build()
    );

    SdkTracerProvider tracerProvider = SdkTracerProvider.builder()
            .addSpanProcessor(BatchSpanProcessor.builder(zipkinExporter).build())
            .setResource(Resource.getDefault().merge(serviceNameResource))
            .build();

    OpenTelemetrySdk openTelemetrySdk = OpenTelemetrySdk.builder()
            .setTracerProvider(tracerProvider)
            .build();

    return openTelemetrySdk;
  }

  @Bean
  public Tracer tracer(OpenTelemetry openTelemetry) {
    return openTelemetry.getTracer("bookstore-app");
  }

}
