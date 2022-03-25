package com.example.mbblueprintbackend.service.impl.location;

import com.example.mbblueprintbackend.entity.location.LocationEntity;
import com.example.mbblueprintbackend.model.Location;
import com.example.mbblueprintbackend.repository.location.LocationRepository;
import com.example.mbblueprintbackend.service.location.LocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class LocationServiceImpl implements LocationService {

    @Autowired
    LocationRepository locationRepository;

    @Override
    public List<Location> getAllLocations() {

        List<Location> locationList = new ArrayList<>();

        locationRepository.findAll().forEach(locationEntity -> locationList.add(
                Location.builder()
                        .id(locationEntity.getId())
                        .title(locationEntity.getTitle())
                        .associatedPinyinSound(locationEntity.getAssociatedPinyinSound())
                        .build()));

        return locationList;
    }

    @Override
    public Location getSingleLocation(UUID uuid) {

        Optional<LocationEntity> locationEntityOptional = locationRepository.findById(uuid);

        if (locationRepository.findById(uuid).isPresent()) {

            LocationEntity locationEntity = locationEntityOptional.get();

            return Location.builder()
                            .id(locationEntity.getId())
                            .title(locationEntity.getTitle())
                            .associatedPinyinSound(locationEntity.getAssociatedPinyinSound())
                            .build();
        } else {
            return null;
        }
    }

    @Override
    public Location createLocation(Location location) {

        LocationEntity locationEntity = LocationEntity.builder()
                .title(location.getTitle())
                .associatedPinyinSound(location.getAssociatedPinyinSound())
                .build();

        locationEntity = locationRepository.save(locationEntity);

        return Location.builder()
                .id(locationEntity.getId())
                .title(locationEntity.getTitle())
                .associatedPinyinSound(locationEntity.getAssociatedPinyinSound())
                .build();
    }

}
