package com.example.mbblueprintbackend.repository.location;

import com.example.mbblueprintbackend.model.Location;
import com.example.mbblueprintbackend.util.Utils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import static com.example.mbblueprintbackend.util.Utils.jsonStringFromObject;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class LocationRepositoryTest {

    LocationRepository locationRepository = new LocationRepository();

    @Test
    void testGetAllLocations() throws JsonProcessingException {

        UUID locationId = UUID.fromString("1bfff94a-b70e-4b39-bd2a-be1c0f898589");

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        assertTrue(locationRepository.getAllLocations().size() > 0);
        HashMap<String, Object> map = ((HashMap<String, Object>) locationRepository.getAllLocations());

        String json = jsonStringFromObject(map);
        String jsonExpected = jsonStringFromObject(Utils.getAllLocations());

        List<Location> movieList = (List<Location>) objectMapper.readValue(json, HashMap.class).get("locations");
        List<Location> movieExpectedList = (List<Location>) objectMapper.readValue(jsonExpected, HashMap.class).get("locations");

        List<Location> myList = objectMapper.readValue(jsonStringFromObject(movieList), new TypeReference<>() {
        });
        List<Location> myListExpected = objectMapper.readValue(jsonStringFromObject(movieExpectedList), new TypeReference<>() {
        });

        Location location = myList.get(0);
        Location locationExpected = myListExpected.get(0);
        locationExpected.setId(locationId);

        assertEquals(location, locationExpected);
        assertEquals(location.getId(), locationExpected.getId());
    }

    @Test
    void getSingleLocation() {

        UUID locationId = UUID.fromString("1bfff94a-b70e-4b39-bd2a-be1c0f898589");

        Location location = locationRepository.getSingleLocation(locationId);
        String json = jsonStringFromObject(location);
        String jsonExpected = jsonStringFromObject(Utils.getSingleLocation(locationId));
        assertEquals(jsonExpected, json);
    }

    @Test
    void createLocation() throws JsonProcessingException {

        UUID movieId = UUID.fromString("1bfff94a-b70e-4b39-bd2a-be1c0f898589");

        Location location = Location.builder().title("Childhood home").build();

        location = locationRepository.createLocation(location);
        String json = jsonStringFromObject(location);
        location.setId(movieId);
        String jsonExpected = jsonStringFromObject(location);
        assertEquals(jsonExpected, json);
    }
}
