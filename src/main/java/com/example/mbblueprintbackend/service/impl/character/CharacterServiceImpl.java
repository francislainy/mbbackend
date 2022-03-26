package com.example.mbblueprintbackend.service.impl.character;

import com.example.mbblueprintbackend.entity.character.CharacterEntity;
import com.example.mbblueprintbackend.model.Character;
import com.example.mbblueprintbackend.repository.character.CharacterRepository;
import com.example.mbblueprintbackend.service.character.CharacterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class CharacterServiceImpl implements CharacterService {

    @Autowired
    CharacterRepository characterRepository;

    @Override
    public List<Character> getAllCharacters() {

        List<Character> characterList = new ArrayList<>();

        characterRepository.findAll().forEach(characterEntity -> characterList.add(
                Character.builder()
                        .id(characterEntity.getId())
                        .character(characterEntity.getCharacter())
                        .pinyin(characterEntity.getPinyin())
                        .meaning(characterEntity.getMeaning())
                        .build()));

        return characterList;
    }

    @Override
    public Character getCharacter(UUID characterId) {

        Optional<CharacterEntity> characterEntityOptional = characterRepository.findById(characterId);

        if (characterEntityOptional.isPresent()) {

            CharacterEntity characterEntity = characterEntityOptional.get();

            return Character.builder()
                    .id(characterEntity.getId())
                    .character(characterEntity.getCharacter())
                    .pinyin(characterEntity.getPinyin())
                    .meaning(characterEntity.getMeaning())
                    .build();
        } else {
            return null;
        }
    }

    @Override
    public Character createCharacter(Character character) {

        CharacterEntity characterEntity = CharacterEntity.builder()
                .character(character.getCharacter())
                .pinyin(character.getPinyin())
                .meaning(character.getMeaning())
                .build();

        characterEntity = characterRepository.save(characterEntity);

        return Character.builder()
                .id(characterEntity.getId())
                .character(characterEntity.getCharacter())
                .pinyin(characterEntity.getPinyin())
                .meaning(characterEntity.getMeaning())
                .build();
    }

    @Override
    public void deleteCharacter(UUID characterId) throws Exception {

        Optional<CharacterEntity> optionalCharacter = characterRepository.findById(characterId);

        if (optionalCharacter.isPresent()) {

            CharacterEntity characterEntity = optionalCharacter.get();

            characterRepository.delete(characterEntity);
        } else {
            throw new Exception();
        }
    }

    @Override
    public Character updateCharacter(UUID uuid, Character character) {

        Optional<CharacterEntity> characterEntityOptional = characterRepository.findById(uuid);

        if (characterEntityOptional.isPresent()) {

            CharacterEntity characterEntity = CharacterEntity.builder()
                    .character(character.getCharacter())
                    .pinyin(character.getPinyin())
                    .meaning(character.getMeaning())
                    .build();

            characterEntity = characterRepository.save(characterEntity);

            return Character.builder()
                    .id(characterEntity.getId())
                    .character(character.getCharacter())
                    .pinyin(character.getPinyin())
                    .meaning(character.getMeaning())
                    .build();
        } else {
            return null;
        }
    }

}