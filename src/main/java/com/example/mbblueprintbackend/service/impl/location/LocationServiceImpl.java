package com.example.mbblueprintbackend.service.impl.location;

import com.example.mbblueprintbackend.model.Location;
import com.example.mbblueprintbackend.repository.location.LocationRepository;
import com.example.mbblueprintbackend.service.location.LocationService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.UUID;

@Service
public class LocationServiceImpl implements LocationService {

    @Autowired
    LocationRepository locationRepository;

    @Override
    public Map<String, Object> getAllLocations() {

        return locationRepository.getAllLocations();
    }

    @Override
    public Location getSingleLocation(UUID uuid) {

        return locationRepository.getSingleLocation(uuid);
    }

    @Override
    public Location createLocation(Location location) throws JsonProcessingException {
        return locationRepository.createLocation(location);
    }

}
