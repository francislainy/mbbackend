package com.example.mbbackend.controller.location;

import com.example.mbbackend.config.BaseIntegrationTest;
import com.example.mbbackend.model.Location;
import com.example.mbbackend.util.Utils;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Slf4j
@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class LocationControllerIT extends BaseIntegrationTest {

    @LocalServerPort
    int port;

    @Autowired
    MockMvc mockMvc;

    RequestSpecification rq;

    ObjectMapper objectMapper;

    @BeforeAll
    void test() {

        objectMapper = new ObjectMapper();

        Map<String, String> headers = new HashMap<>();
        rq = Utils.getRequestSpecification().baseUri("http://localhost:" + port)
                .headers(headers);
    }

    @AfterEach
    void cleanUp() {
        deleteAllLocations();
    }

    @Test
    void testGetAllLocations() {

        createLocation();
        List<Location> list = getLocationList();
        assertEquals(1, list.size());
    }

    @Test
    void testGetLocation() {

        createLocation();
        List<Location> list = getLocationList();

        Location location = objectMapper.convertValue(list.get(0), Location.class);
        UUID locationId = location.getId();

        Response response = rq.get("/api/mb/location/" + locationId);
        assertEquals(200, response.getStatusCode());

        Location location1 = objectMapper.convertValue(response.jsonPath().get(), Location.class);
        assertEquals("anyLocationTitle", location1.getTitle());
    }

    @Test
    void testCreateLocation() {
        createLocation();
    }

    @Test
    void testUpdateLocation() {

        createLocation();

        List<Location> list = getLocationList();

        Location location = getFirstLocationFromList(list);
        location.setTitle("updatedTitle");
        UUID locationId = location.getId();
        
        Response response = rq.body(location).put("/api/mb/location/" + locationId);
        Location updatedLocation = objectMapper.convertValue(response.jsonPath().get(), Location.class);
        assertEquals(200, response.getStatusCode());
        assertEquals("updatedTitle", updatedLocation.getTitle());

        response = rq.get("/api/mb/location/" + locationId);
        assertEquals(200, response.getStatusCode());
        Location location1 = objectMapper.convertValue(response.jsonPath().get(), Location.class);
        assertEquals("updatedTitle", location1.getTitle());
    }
    
    // Helpers
    private Location getFirstLocationFromList(List<Location> list) {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.convertValue(list.get(0), Location.class);
    }

    private List<Location> getLocationList() {
        Response response = rq.get("/api/mb/location");
        List<Location> list = response.jsonPath().getList("locations");
        assertEquals(200, response.getStatusCode());
        return list;
    }

    private void createLocation() {

        Location location = new Location();
        location.setTitle("anyLocationTitle");
        Response response = rq.body(location).post("/api/mb/location");
        assertEquals(201, response.getStatusCode());
    }

    private void deleteAllLocations() {

        Response response = rq.get("/api/mb/location");
        List<Location> list = response.jsonPath().getList("locations");

        ObjectMapper objectMapper = new ObjectMapper();
        List<Location> locationList = objectMapper.convertValue(
                list,
                new TypeReference<>() {
                });

        for (Location r : locationList) {
            deleteLocation(r);
        }
    }

    private void deleteLocation(Location location) {
        Response response = rq.body(location).delete("/api/mb/location/" + location.getId());
        assertEquals(206, response.getStatusCode());
    }

}
