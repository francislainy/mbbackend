package com.example.mbbackend.controller.location;

import com.example.mbbackend.model.Location;
import com.example.mbbackend.service.location.LocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/mb/location")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class LocationController {

    @Autowired
    LocationService locationService;

    @GetMapping({"", "/"})
    public ResponseEntity<Object> getAllLocation() {

        HashMap<String, List<Location>> map = new HashMap<>();
        map.put("locations", locationService.getAllLocations());

        return new ResponseEntity<>(map, HttpStatus.OK);
    }

    @GetMapping({"/{locationId}", "/{locationId}/"})
    public ResponseEntity<Object> getSingleLocation(@PathVariable UUID locationId) {

        return new ResponseEntity<>(locationService.getLocation(locationId), HttpStatus.OK);
    }

    @PostMapping({"", "/"})
    public ResponseEntity<Location> createLocation(@RequestBody Location location) {

        return new ResponseEntity<>(locationService.createLocation(location), HttpStatus.CREATED);
    }

    @DeleteMapping({"/{locationId}", "/{locationId}/"})
    public ResponseEntity<Object> deleteLocation(@PathVariable UUID locationId) throws Exception {

        locationService.deleteLocation(locationId);
        return new ResponseEntity<>(HttpStatus.PARTIAL_CONTENT);
    }

    @PutMapping({"/{locationId}", "/{locationId}/"})
    public ResponseEntity<Object> updateLocation(@PathVariable UUID locationId, @RequestBody Location location) {

        return new ResponseEntity<>(locationService.updateLocation(locationId, location), HttpStatus.OK);
    }

}
