package com.upravad.cookbot.config;

import static org.testcontainers.utility.DockerImageName.parse;

import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.testcontainers.containers.PostgreSQLContainer;

@TestConfiguration(proxyBeanMethods = false)
@ImportAutoConfiguration({BotConfiguration.class})
public class ContainerConfiguration {
  @Bean
  @ServiceConnection
  PostgreSQLContainer<?> postgresContainer(DynamicPropertyRegistry registry) {
    PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>(parse("postgres:latest"));
    registry.add("spring.datasource.url", postgres::getJdbcUrl);
    return postgres;
  }
}
