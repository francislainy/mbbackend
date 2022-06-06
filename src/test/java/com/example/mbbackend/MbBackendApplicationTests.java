package com.example.mbbackend;

import com.example.mbbackend.entity.room.RoomEntity;
import com.example.mbbackend.repository.room.RoomRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@Testcontainers
@SpringBootTest
class MbBackendApplicationTests  {

    @Container
    public static PostgreSQLContainer container = new PostgreSQLContainer()
            .withUsername("duke")
            .withPassword("password")
            .withDatabaseName("test");

    @Autowired
    RoomRepository roomRepository;

    // requires Spring Boot >= 2.2.6
    @DynamicPropertySource
    static void properties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", container::getJdbcUrl);
        registry.add("spring.datasource.password", container::getPassword);
        registry.add("spring.datasource.username", container::getUsername);
    }

    @Test
    void contextLoads() {

        RoomEntity roomEntity = RoomEntity.builder().title("anyTitle").build();

        roomRepository.save(roomEntity);

        System.out.println("Context loads!");
    }

}
