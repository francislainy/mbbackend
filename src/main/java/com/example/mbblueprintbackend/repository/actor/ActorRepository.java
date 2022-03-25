package com.example.mbblueprintbackend.repository.actor;

import com.example.mbblueprintbackend.entity.actor.ActorEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ActorRepository extends JpaRepository<ActorEntity, UUID> {

}
