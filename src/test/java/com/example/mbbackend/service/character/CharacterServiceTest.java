package com.example.mbbackend.service.character;

import com.example.mbbackend.entity.character.CharacterEntity;
import com.example.mbbackend.entity.movie.MovieEntity;
import com.example.mbbackend.model.Character;
import com.example.mbbackend.repository.character.CharacterRepository;
import com.example.mbbackend.repository.movie.MovieRepository;
import com.example.mbbackend.service.impl.character.CharacterServiceImpl;
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
class CharacterServiceTest {

    @InjectMocks
    CharacterServiceImpl characterService;

    @Mock
    CharacterRepository characterRepository;

    @Mock
    MovieRepository movieRepository;

    @Test
    void testGetAllCharacters() {

        List<CharacterEntity> characterEntityList = new ArrayList<>();
        UUID characterId = UUID.fromString("1bfff94a-b70e-4b39-bd2a-be1c0f898589");

        CharacterEntity characterEntity = CharacterEntity.builder()
                .id(characterId)
                .hanzi("anyHanzi")
                .pinyin("anyPinyin")
                .meaning("anyMeaning")
                .build();

        characterEntityList.add(characterEntity);

        when(characterRepository.findAll()).thenReturn(characterEntityList);

        List<Character> characterList = characterService.getAllCharacters();

        assertTrue(characterList.size() > 0);

        Character character = characterList.get(0);

        assertAll(
                () -> assertEquals(characterEntity.getId().toString(), character.getId().toString()),
                () -> assertEquals(characterEntity.getHanzi(), character.getHanzi()),
                () -> assertEquals(characterEntity.getPinyin(), character.getPinyin()),
                () -> assertEquals(characterEntity.getMeaning(), character.getMeaning()));
    }

    @Test
    void testGetCharacter() {

        UUID characterId = UUID.fromString("1bfff94a-b70e-4b39-bd2a-be1c0f898589");

        Optional<CharacterEntity> optionalLocationEntity = Optional.ofNullable(CharacterEntity.builder()
                .id(characterId)
                .hanzi("anyHanzi")
                .pinyin("anyPinyin")
                .meaning("anyMeaning")
                .build());

        when(characterRepository.findById(characterId)).thenReturn(optionalLocationEntity);

        Character character = characterService.getCharacter(characterId);

        assertAll(
                () -> assertEquals(characterId.toString(), character.getId().toString()),
                () -> assertEquals("anyHanzi", character.getHanzi()),
                () -> assertEquals("anyPinyin", character.getPinyin()),
                () -> assertEquals("anyMeaning", character.getMeaning()));
    }

    @Test
    void testCreateCharacter() {

        UUID characterId = UUID.fromString("1bfff94a-b70e-4b39-bd2a-be1c0f898589");

        CharacterEntity characterEntity = CharacterEntity.builder()
                .id(characterId)
                .hanzi("anyHanzi")
                .pinyin("anyPinyin")
                .meaning("anyMeaning")
                .build();

        MovieEntity movieEntity = MovieEntity.builder()
                .id(UUID.randomUUID())
                .scene("")
                .imageUrl("")
                .character(characterEntity)
                .actor(null)
                .location(null)
                .room(null)
                .build();

        when(characterRepository.save(any())).thenReturn(characterEntity);
        when(movieRepository.save(any())).thenReturn(movieEntity);

        Character character = characterService.createCharacter(new Character());

        assertAll(
                () -> assertEquals(characterId.toString(), character.getId().toString()),
                () -> assertEquals(characterEntity.getHanzi(), character.getHanzi()),
                () -> assertEquals(characterEntity.getPinyin(), character.getPinyin()),
                () -> assertEquals(characterEntity.getMeaning(), character.getMeaning()),
                () -> assertNotNull(character.getMovie()),
                () -> assertNotNull(character.getMovie().getId()),
                () -> assertEquals(character.getId(), character.getMovie().getCharacter().getId()),
                () -> assertEquals(characterEntity.getHanzi(), character.getMovie().getCharacter().getHanzi()),
                () -> assertEquals(characterEntity.getPinyin(), character.getMovie().getCharacter().getPinyin()),
                () -> assertEquals(characterEntity.getMeaning(), character.getMovie().getCharacter().getMeaning()));
    }

    @Test
    void testCreateCharacterBlockedWhenCharacterHanziAlreadyExists() {

        UUID characterId = UUID.fromString("1bfff94a-b70e-4b39-bd2a-be1c0f898589");

        CharacterEntity characterEntity = CharacterEntity.builder()
                .id(characterId)
                .hanzi("anyHanzi")
                .pinyin("anyPinyin")
                .meaning("anyMeaning")
                .build();

        MovieEntity movieEntity = MovieEntity.builder()
                .id(UUID.randomUUID())
                .scene("")
                .imageUrl("")
                .character(characterEntity)
                .actor(null)
                .location(null)
                .room(null)
                .build();

        when(characterRepository.findCharacterEntityByHanzi(any())).thenReturn(Optional.of(characterEntity));

        Character character = characterService.createCharacter(new Character());

        assertAll(
                () -> assertNull(character));
    }

    @Test
    void testDeleteCharacter() {

        UUID characterId = UUID.fromString("1bfff94a-b70e-4b39-bd2a-be1c0f898589");

        CharacterEntity characterEntity = CharacterEntity.builder()
                .id(characterId)
                .build();

        when(characterRepository.findById(any())).thenReturn(Optional.ofNullable(characterEntity));

        assertDoesNotThrow(() -> characterService.deleteCharacter(characterId));
    }

    @Test
    void testDeleteCharacter_ItemNotFound_ThrowsException() {

        UUID characterId = UUID.fromString("1bfff94a-b70e-4b39-bd2a-be1c0f898589");

        assertThrows(Exception.class, () -> characterService.deleteCharacter(characterId));
    }

    @Test
    void testUpdateCharacter() {

        UUID characterId = UUID.fromString("1bfff94a-b70e-4b39-bd2a-be1c0f898589");

        CharacterEntity characterEntity = CharacterEntity.builder()
                .id(characterId)
                .hanzi("anyHanzi")
                .pinyin("anyPinyin")
                .meaning("anyMeaning")
                .build();

        Optional<CharacterEntity> characterEntity1 = Optional.ofNullable(CharacterEntity.builder()
                .id(characterId)
                .hanzi("anyHanzi")
                .pinyin("anyPinyin")
                .meaning("anyMeaning")
                .build());

        Character character0 = Character.builder()
                .id(characterId)
                .hanzi("anyHanzi")
                .pinyin("anyPinyin")
                .meaning("anyMeaning")
                .build();

        when(characterRepository.findById(characterId)).thenReturn(characterEntity1);
        when(characterRepository.save(any())).thenReturn(characterEntity);

        Character character = characterService.updateCharacter(characterId, character0);

        assertAll(
                () -> assertEquals(characterId.toString(), character.getId().toString()),
                () -> assertEquals(characterEntity.getHanzi(), character.getHanzi()),
                () -> assertEquals(characterEntity.getPinyin(), character.getPinyin()),
                () -> assertEquals(characterEntity.getMeaning(), character.getMeaning()));
    }

}
