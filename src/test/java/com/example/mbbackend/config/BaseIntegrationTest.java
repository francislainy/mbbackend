package com.example.mbbackend.config;

import org.junit.jupiter.api.BeforeAll;
import org.springframework.test.context.ActiveProfiles;
import org.testcontainers.junit.jupiter.Container;

@ActiveProfiles("test")
public class BaseIntegrationTest {

    @Container
    public static PostgresSqlContainer postgres = PostgresSqlContainer.getInstance();

    @BeforeAll
    public static void init() {

        postgres.start();
        postgres.withInitScript("init.sql");
        System.setProperty("spring.datasource.url", postgres.getJdbcUrl());
        System.setProperty("spring.datasource.username", postgres.getUsername());
        System.setProperty("spring.datasource.password", postgres.getPassword());
        System.setProperty("spring.datasource.port", postgres.getFirstMappedPort().toString());
    }
}
