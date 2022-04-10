package com.example.mbbackend.repository.character;

import com.example.mbbackend.entity.character.CharacterEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface CharacterRepository extends JpaRepository<CharacterEntity, UUID> {

    Optional<CharacterEntity> findCharacterEntityByHanzi(String hanzi);
}
