package com.example.mbblueprintbackend.controller.location;

import com.example.mbblueprintbackend.model.Location;
import com.example.mbblueprintbackend.service.location.LocationService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/mb")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class LocationController {

    @Autowired
    LocationService locationService;

    @GetMapping("/location")
    public ResponseEntity<Object> getAllLocation() {

        return new ResponseEntity<>(locationService.getAllLocations(), HttpStatus.OK);
    }

    @GetMapping("/location/{locationId}")
    public ResponseEntity<Object> getSingleLocation(@PathVariable UUID locationId) {

        return new ResponseEntity<>(locationService.getSingleLocation(locationId), HttpStatus.OK);
    }

    @PostMapping("/location")
    public ResponseEntity<Location> createLocation(@RequestBody Location Location) throws JsonProcessingException {

        return new ResponseEntity<>(locationService.createLocation(Location), HttpStatus.CREATED);
    }

}
