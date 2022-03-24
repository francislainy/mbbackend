package com.example.mbblueprintbackend.service.location;

import com.example.mbblueprintbackend.model.Location;
import com.example.mbblueprintbackend.repository.location.LocationRepository;
import com.example.mbblueprintbackend.service.impl.location.LocationServiceImpl;
import com.example.mbblueprintbackend.util.Utils;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class LocationServiceTest {

    @InjectMocks
    LocationServiceImpl locationService;

    @Mock
    LocationRepository locationRepository;

    @Test
    void testGetAllLocations() {

        when(locationRepository.getAllLocations()).thenReturn(Utils.getAllLocations());

        assertTrue(locationService.getAllLocations().size() > 0);
    }

    @Test
    void testGetSingleLocation() {

        UUID locationId = UUID.fromString("1bfff94a-b70e-4b39-bd2a-be1c0f898589");

        when(locationRepository.getSingleLocation(locationId)).thenReturn(Utils.getSingleLocation(locationId));

        Location location = locationService.getSingleLocation(locationId);

        assertAll(
                () -> assertEquals(locationId.toString(), location.getId().toString()),
                () -> assertEquals("South London", location.getTitle()),
                () -> assertEquals("Ou", location.getAssociatedPinyinSound()));
    }

    @Test
    void testCreateLocation() throws JsonProcessingException {

        UUID locationId = UUID.fromString("1bfff94a-b70e-4b39-bd2a-be1c0f898589");

        Location location = Location.builder().title("Childhood home").build();

        when(locationRepository.createLocation(location)).thenReturn(Utils.getSingleLocation(locationId));

        Location location1 = locationService.createLocation(location);

        assertAll(
                () -> assertEquals(locationId.toString(), location1.getId().toString()),
                () -> assertEquals("South London", location1.getTitle()),
                () -> assertEquals("Ou", location1.getAssociatedPinyinSound()));
    }

}
