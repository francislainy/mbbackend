package com.example.mbblueprintbackend.service.location;

import com.example.mbblueprintbackend.entity.location.LocationEntity;
import com.example.mbblueprintbackend.model.Location;
import com.example.mbblueprintbackend.repository.location.LocationRepository;
import com.example.mbblueprintbackend.service.impl.location.LocationServiceImpl;
import com.example.mbblueprintbackend.util.Utils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class LocationServiceTest {

    @InjectMocks
    LocationServiceImpl locationService;

    @Mock
    LocationRepository locationRepository;

    @Test
    void testGetAllLocations() {

        when(locationRepository.findAll()).thenReturn(Utils.getAllLocations());

        assertTrue(locationService.getAllLocations().size() > 0);
    }

    @Test
    void testGetSingleLocation() {

        UUID locationId = UUID.fromString("1bfff94a-b70e-4b39-bd2a-be1c0f898589");

        when(locationRepository.findById(locationId)).thenReturn(Utils.getSingleLocation(locationId));

        Location location = locationService.getSingleLocation(locationId);

        assertAll(
                () -> assertEquals(locationId.toString(), location.getId().toString()),
                () -> assertEquals("South London", location.getTitle()),
                () -> assertEquals("Ou", location.getAssociatedPinyinSound()));
    }

    @Test
    void testCreateLocation() {

        UUID locationId = UUID.fromString("1bfff94a-b70e-4b39-bd2a-be1c0f898589");

        LocationEntity locationEntity = LocationEntity.builder()
                .id(locationId)
                .title("South London")
                .associatedPinyinSound("Ou")
                .build();

        when(locationRepository.save(any())).thenReturn(locationEntity);

        Location location = locationService.createLocation(new Location());

        assertAll(
                () -> assertEquals(locationId.toString(), location.getId().toString()),
                () -> assertEquals(locationEntity.getTitle(), location.getTitle()),
                () -> assertEquals(locationEntity.getAssociatedPinyinSound(), location.getAssociatedPinyinSound()));
    }

}
