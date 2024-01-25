package com.example.bookstoreapp.configs;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.flywaydb.core.Flyway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

/**
 * Configuration class to manage Flyway database migrations.
 */
@Configuration
@Slf4j
public class FlywayConfig {

  @Autowired
  private DataSource dataSource;

  /**
   * Executes Flyway migration on application startup.
   */
  @PostConstruct
  public void init() {
    log.info("Executing Flyway migration");
    executeFlyway(dataSource);
    log.info("Flyway migration executed");
  }

  /**
   * Executes Flyway migration using the provided DataSource.
   *
   * @param dataSource DataSource used for database connection.
   */
  private void executeFlyway(DataSource dataSource) {
    Flyway flyway = Flyway.configure()
        .dataSource(dataSource)
        .locations("classpath:/db/scripts")
            // Replace this with your custom path
        .load();

    // You can set other Flyway configurations here if needed

    flyway.migrate();
  }
}