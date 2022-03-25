package com.example.mbblueprintbackend.service.location;

import com.example.mbblueprintbackend.model.Location;

import java.util.List;
import java.util.UUID;

public interface LocationService {

    List<Location> getAllLocations();

    Location getSingleLocation(UUID uuid);

    Location createLocation(Location location);
}
