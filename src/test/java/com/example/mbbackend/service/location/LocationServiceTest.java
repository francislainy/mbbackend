package com.example.mbbackend.service.location;

import com.example.mbbackend.entity.location.LocationEntity;
import com.example.mbbackend.model.Location;
import com.example.mbbackend.repository.location.LocationRepository;
import com.example.mbbackend.service.impl.location.LocationServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
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

        List<LocationEntity> locationEntityList = new ArrayList<>();
        UUID locationId = UUID.fromString("1bfff94a-b70e-4b39-bd2a-be1c0f898589");

        LocationEntity locationEntity = LocationEntity.builder()
                .id(locationId)
                .title("anyTitle")
                .associatedPinyinSound("anySound")
                .build();

        locationEntityList.add(locationEntity);

        when(locationRepository.findAll()).thenReturn(locationEntityList);

        List<Location> locationList = locationService.getAllLocations();

        assertTrue(locationList.size() > 0);

        Location location = locationList.get(0);

        assertAll(
                () -> assertEquals(locationEntity.getId().toString(), location.getId().toString()),
                () -> assertEquals(locationEntity.getTitle(), location.getTitle()),
                () -> assertEquals(locationEntity.getAssociatedPinyinSound(), location.getAssociatedPinyinSound()));

    }

    @Test
    void testGetSingleLocation() {

        UUID locationId = UUID.fromString("1bfff94a-b70e-4b39-bd2a-be1c0f898589");

        Optional<LocationEntity> optionalLocationEntity = Optional.ofNullable(LocationEntity.builder()
                .id(locationId)
                .title("anyTitle")
                .associatedPinyinSound("anySound")
                .build());

        when(locationRepository.findById(locationId)).thenReturn(optionalLocationEntity);

        Location location = locationService.getLocation(locationId);

        assertAll(
                () -> assertEquals(locationId.toString(), location.getId().toString()),
                () -> assertEquals("anyTitle", location.getTitle()),
                () -> assertEquals("anySound", location.getAssociatedPinyinSound()));
    }

    @Test
    void testCreateLocation() {

        UUID locationId = UUID.fromString("1bfff94a-b70e-4b39-bd2a-be1c0f898589");

        LocationEntity locationEntity = LocationEntity.builder()
                .id(locationId)
                .title("anyTitle")
                .associatedPinyinSound("anySound")
                .build();

        when(locationRepository.save(any())).thenReturn(locationEntity);

        Location location = locationService.createLocation(new Location());

        assertAll(
                () -> assertEquals(locationId.toString(), location.getId().toString()),
                () -> assertEquals(locationEntity.getTitle(), location.getTitle()),
                () -> assertEquals(locationEntity.getAssociatedPinyinSound(), location.getAssociatedPinyinSound()));
    }

    @Test
    void testDeleteLocation() {

        UUID locationId = UUID.fromString("1bfff94a-b70e-4b39-bd2a-be1c0f898589");

        LocationEntity locationEntity = LocationEntity.builder()
                .id(locationId)
                .build();

        when(locationRepository.findById(any())).thenReturn(java.util.Optional.ofNullable(locationEntity));

        assertDoesNotThrow(() -> locationService.deleteLocation(locationId));
    }

    @Test
    void testDeleteLocation_ItemNotFound_ThrowsException() {

        UUID locationId = UUID.fromString("1bfff94a-b70e-4b39-bd2a-be1c0f898589");

        assertThrows(Exception.class, () -> locationService.deleteLocation(locationId));
    }

    @Test
    void testUpdateLocation() {

        UUID locationId = UUID.fromString("1bfff94a-b70e-4b39-bd2a-be1c0f898589");

        LocationEntity locationEntity = LocationEntity.builder()
                .id(locationId)
                .title("anyTitle")
                .associatedPinyinSound("anySound")
                .build();

        Location location0 = Location.builder()
                .id(locationId)
                .title("anyTitle")
                .associatedPinyinSound("anySound")
                .build();

        when(locationRepository.findById(locationId)).thenReturn(Optional.ofNullable(locationEntity));
        when(locationRepository.save(any())).thenReturn(locationEntity);

        Location location = locationService.updateLocation(locationId, location0);

        assertAll(
                () -> assertEquals(locationId.toString(), location.getId().toString()),
                () -> assertEquals(locationEntity.getTitle(), location.getTitle()),
                () -> assertEquals(locationEntity.getAssociatedPinyinSound(), location.getAssociatedPinyinSound()));
    }

}
