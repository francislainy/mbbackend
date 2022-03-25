package com.example.mbblueprintbackend.repository.location;

import com.example.mbblueprintbackend.entity.location.LocationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface LocationRepository extends JpaRepository<LocationEntity, UUID> {

}
