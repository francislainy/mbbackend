package com.example.mbblueprintbackend.controller.location;

import com.example.mbblueprintbackend.model.Location;
import com.example.mbblueprintbackend.service.location.LocationService;
import com.example.mbblueprintbackend.util.Utils;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Slf4j
@WebMvcTest(controllers = LocationController.class)
@ExtendWith(MockitoExtension.class)
class LocationControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    LocationService LocationService;

    @Test
    void testGetAllLocations() throws Exception {

        List<Location> locationList = new ArrayList<>();
        UUID locationId = UUID.fromString("1bfff94a-b70e-4b39-bd2a-be1c0f898589");

        Location location = Location.builder()
                .id(locationId)
                .title("South London")
                .associatedPinyinSound("Ou")
                .build();

        locationList.add(location);

        HashMap<String, List<Location>> map = new HashMap<>();
        map.put("locations", locationList);

        String json = Utils.jsonStringFromObject(map);
        when(LocationService.getAllLocations()).thenReturn(locationList);

        mockMvc.perform(get("/api/mb/location"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(content().json(json));
    }

    @Test
    void testGetSingleLocation() throws Exception {

        UUID locationId = UUID.fromString("1bfff94a-b70e-4b39-bd2a-be1c0f898589");

        Location location = Location.builder()
                .id(locationId)
                .title("South London")
                .associatedPinyinSound("Ou")
                .build();

        String json = Utils.jsonStringFromObject(location);

        when(LocationService.getSingleLocation(any())).thenReturn(location);

        mockMvc.perform(get("/api/mb/location/{locationId}", locationId))
                .andExpect(status().is2xxSuccessful())
                .andExpect(content().json(json));
    }

    @Test
    void testPostLocation() throws Exception {

        Location location = Location.builder()
                .title("South London")
                .associatedPinyinSound("Ou")
                .build();

        String json = Utils.jsonStringFromObject(location);

        ObjectMapper objectMapper = new ObjectMapper();
        Location location1 = objectMapper.readValue(json, Location.class);

        UUID locationId = UUID.fromString("1bfff94a-b70e-4b39-bd2a-be1c0f898589");
        location1.setId(locationId);
        String json1 = Utils.jsonStringFromObject(location1);

        when(LocationService.createLocation(location)).thenReturn(location1);

        mockMvc.perform(post("/api/mb/location")
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful())
                .andExpect(content().json(json1));
    }
}
