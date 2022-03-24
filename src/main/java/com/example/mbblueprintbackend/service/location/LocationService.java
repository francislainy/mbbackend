package com.example.mbblueprintbackend.service.location;

import com.example.mbblueprintbackend.model.Location;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.util.Map;
import java.util.UUID;

public interface LocationService {

    Map<String, Object> getAllLocations();

    Location getSingleLocation(UUID uuid);

    Location createLocation(Location location) throws JsonProcessingException;
}
