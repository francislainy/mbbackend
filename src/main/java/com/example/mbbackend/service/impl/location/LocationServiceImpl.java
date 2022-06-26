package com.example.mbbackend.service.impl.location;

import com.example.mbbackend.entity.location.LocationEntity;
import com.example.mbbackend.model.Location;
import com.example.mbbackend.repository.location.LocationRepository;
import com.example.mbbackend.service.location.LocationService;
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
    public Location getLocation(UUID uuid) {

        Optional<LocationEntity> locationEntityOptional = locationRepository.findById(uuid);

        if (locationEntityOptional.isPresent()) {

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

    @Override
    public void deleteLocation(UUID uuid) throws Exception {

        Optional<LocationEntity> optionalLocation = locationRepository.findById(uuid);

        if (optionalLocation.isPresent()) {

            LocationEntity locationEntity = optionalLocation.get();

            locationRepository.delete(locationEntity);
        } else {
            throw new Exception();
        }
    }

    @Override
    public Location updateLocation(UUID uuid, Location location) {

        Optional<LocationEntity> locationEntityOptional = locationRepository.findById(uuid);

        if (locationEntityOptional.isPresent()) {

            LocationEntity locationEntity = LocationEntity.builder()
                    .id(location.getId())
                    .title(location.getTitle())
                    .associatedPinyinSound(location.getAssociatedPinyinSound())
                    .build();

            locationEntity = locationRepository.save(locationEntity);

            return Location.builder()
                    .id(locationEntity.getId())
                    .title(locationEntity.getTitle())
                    .associatedPinyinSound(locationEntity.getAssociatedPinyinSound())
                    .build();
        } else {
            return null;
        }
    }

}
