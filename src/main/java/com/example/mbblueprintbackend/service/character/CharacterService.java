package com.example.mbblueprintbackend.service.character;

import com.example.mbblueprintbackend.model.Character;

import java.util.List;
import java.util.UUID;

public interface CharacterService {

    List<Character> getAllCharacters();

    Character getCharacter(UUID characterId);

    void deleteCharacter(UUID characterId) throws Exception;

    Character createCharacter(Character character);

    Character updateCharacter(UUID id, Character character);
}
