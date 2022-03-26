package com.example.mbblueprintbackend.repository.character;

import com.example.mbblueprintbackend.entity.character.CharacterEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface CharacterRepository extends JpaRepository<CharacterEntity, UUID> {

}
