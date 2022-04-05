package com.example.mbbackend.service.location;

import com.example.mbbackend.model.Location;

import java.util.List;
import java.util.UUID;

public interface LocationService {

    List<Location> getAllLocations();

    Location getLocation(UUID uuid);

    void deleteLocation(UUID uuid) throws Exception;

    Location createLocation(Location location);

    Location updateLocation(UUID id, Location location);
}
