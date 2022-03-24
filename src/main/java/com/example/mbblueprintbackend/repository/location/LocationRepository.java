package com.example.mbblueprintbackend.repository.location;

import com.example.mbblueprintbackend.model.Location;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Repository;

import java.util.*;

import static com.example.mbblueprintbackend.util.Util.jsonStringFromObject;

@Repository
public class LocationRepository {

    List<Object> locationList = new ArrayList<>();

    public Map<String, Object> getAllLocations() {

        UUID locationId = UUID.fromString("1bfff94a-b70e-4b39-bd2a-be1c0f898589");

        Location location = Location.builder()
                .id(locationId)
                .title("South London")
                .associatedPinyinSound("Ou")
                .build();

        locationList.add(location);
        Map<String, Object> map = new HashMap<>();
        map.put("locations", locationList);

        return map;
    }

    public Location getSingleLocation(UUID locationId) {

        return Location.builder()
                .id(locationId)
                .title("South London")
                .associatedPinyinSound("Ou")
                .build();
    }

    public Location createLocation(Location location) throws JsonProcessingException {

        UUID locationId = UUID.fromString("1bfff94a-b70e-4b39-bd2a-be1c0f898589");
        ObjectMapper objectMapper = new ObjectMapper();
        String json = jsonStringFromObject(location);
        Location location1 = objectMapper.readValue(json, Location.class);
        location1.setId(locationId);

        locationList.add(location1);

        return location1;
    }
}
