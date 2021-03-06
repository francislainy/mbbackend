package com.example.mbbackend.service.character;

import com.example.mbbackend.config.CharacterTone;
import com.example.mbbackend.entity.actor.ActorEntity;
import com.example.mbbackend.entity.character.CharacterEntity;
import com.example.mbbackend.entity.movie.MovieEntity;
import com.example.mbbackend.model.Character;
import com.example.mbbackend.repository.actor.ActorRepository;
import com.example.mbbackend.repository.character.CharacterRepository;
import com.example.mbbackend.repository.movie.MovieRepository;
import com.example.mbbackend.service.impl.character.CharacterServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

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

    @Mock
    ActorRepository actorRepository;

    @Test
    void testGetAllCharacters() {

        List<CharacterEntity> characterEntityList = new ArrayList<>();
        UUID characterId = UUID.fromString("1bfff94a-b70e-4b39-bd2a-be1c0f898589");
        UUID movieId = UUID.fromString("1bfff94a-b70e-4b39-bd2a-be1c0f898589");

        MovieEntity movieEntity = MovieEntity.builder()
                .id(movieId)
                .build();
        List<MovieEntity> movieEntityList = new ArrayList<>();
        movieEntityList.add(movieEntity);

        CharacterEntity characterEntity = CharacterEntity.builder()
                .id(characterId)
                .hanzi("anyHanzi")
                .pinyin("anyPinyin")
                .meaning("anyMeaning")
                .tone(CharacterTone.FIRST)
                .prop(true)
                .movie(movieEntity)
                .build();

        characterEntityList.add(characterEntity);

        when(characterRepository.findAll()).thenReturn(characterEntityList);
        when(movieRepository.findMoviesByCharacterId(characterId)).thenReturn(movieEntityList);

        List<Character> characterList = characterService.getAllCharacters();

        assertTrue(characterList.size() > 0);

        Character character = characterList.get(0);

        assertAll(
                () -> assertEquals(characterEntity.getId().toString(), character.getId().toString()),
                () -> assertEquals(characterEntity.getHanzi(), character.getHanzi()),
                () -> assertEquals(characterEntity.getPinyin(), character.getPinyin()),
                () -> assertEquals(characterEntity.getTone(), character.getTone()),
                () -> assertEquals(characterEntity.getMeaning(), character.getMeaning()),
                () -> assertTrue(character.isProp()),
                () -> assertEquals(movieId.toString(), character.getMovie().getId().toString()));
    }


    @Test
    void testGetAllCharactersWhenNoMovie() {

        List<CharacterEntity> characterEntityList = new ArrayList<>();
        UUID characterId = UUID.fromString("1bfff94a-b70e-4b39-bd2a-be1c0f898589");

        CharacterEntity characterEntity = CharacterEntity.builder()
                .id(characterId)
                .hanzi("anyHanzi")
                .pinyin("anyPinyin")
                .meaning("anyMeaning")
                .tone(CharacterTone.FIRST)
                .prop(true)
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
                () -> assertEquals(characterEntity.getTone(), character.getTone()),
                () -> assertEquals(characterEntity.getMeaning(), character.getMeaning()),
                () -> assertTrue(character.isProp()),
                () -> assertNull(character.getMovie()));
    }

    @Test
    void testGetCharacter() {

        UUID characterId = UUID.fromString("1bfff94a-b70e-4b39-bd2a-be1c0f898589");
        UUID movieId = UUID.fromString("1bfff94a-b70e-4b39-bd2a-be1c0f898589");

        MovieEntity movieEntity = MovieEntity.builder()
                .id(movieId)
                .build();
        List<MovieEntity> movieEntityList = new ArrayList<>();
        movieEntityList.add(movieEntity);

        Optional<CharacterEntity> optionalLocationEntity = Optional.ofNullable(CharacterEntity.builder()
                .id(characterId)
                .hanzi("anyHanzi")
                .pinyin("anyPinyin")
                .meaning("anyMeaning")
                .tone(CharacterTone.FIRST)
                .prop(true)
                .movie(movieEntity)
                .build());

        when(characterRepository.findById(characterId)).thenReturn(optionalLocationEntity);
        when(movieRepository.findMoviesByCharacterId(characterId)).thenReturn(movieEntityList);

        Character character = characterService.getCharacter(characterId);

        assertAll(
                () -> assertEquals(characterId.toString(), character.getId().toString()),
                () -> assertEquals("anyHanzi", character.getHanzi()),
                () -> assertEquals("anyPinyin", character.getPinyin()),
                () -> assertEquals("FIRST", character.getTone().name()),
                () -> assertEquals("anyMeaning", character.getMeaning()),
                () -> assertTrue(character.isProp()),
                () -> assertEquals(movieId.toString(), character.getMovie().getId().toString())
        );
    }

    @Test
    void testGetCharacterWithNoMovie() {

        UUID characterId = UUID.fromString("1bfff94a-b70e-4b39-bd2a-be1c0f898589");

        Optional<CharacterEntity> optionalLocationEntity = Optional.ofNullable(CharacterEntity.builder()
                .id(characterId)
                .hanzi("anyHanzi")
                .pinyin("anyPinyin")
                .meaning("anyMeaning")
                .tone(CharacterTone.FIRST)
                .prop(true)
                .build());

        when(characterRepository.findById(characterId)).thenReturn(optionalLocationEntity);

        Character character = characterService.getCharacter(characterId);

        assertAll(
                () -> assertEquals(characterId.toString(), character.getId().toString()),
                () -> assertEquals("anyHanzi", character.getHanzi()),
                () -> assertEquals("anyPinyin", character.getPinyin()),
                () -> assertEquals("FIRST", character.getTone().name()),
                () -> assertEquals("anyMeaning", character.getMeaning()),
                () -> assertTrue(character.isProp()),
                () -> assertNull(character.getMovie())
        );
    }

    @Test
    void testCreateCharacter() {

        UUID characterId = UUID.fromString("1bfff94a-b70e-4b39-bd2a-be1c0f898589");

        CharacterEntity characterEntity = CharacterEntity.builder()
                .id(characterId)
                .hanzi("anyHanzi")
                .pinyin("anyPinyin")
                .meaning("anyMeaning")
                .tone(CharacterTone.FIRST)
                .prop(true)
                .build();

        MovieEntity movieEntity = MovieEntity.builder()
                .id(UUID.randomUUID())
                .suggestedMovie(true)
                .scene("")
                .imageUrl("")
                .character(characterEntity)
                .actor(ActorEntity.builder()
                        .id(UUID.randomUUID())
                        .name("Ji")
                        .associatedPinyinSound("ji")
                        .build())
                .location(null)
                .room(null)
                .build();

        when(characterRepository.save(any())).thenReturn(characterEntity);
        when(movieRepository.save(any())).thenReturn(movieEntity);

        Character character = characterService.createCharacter(Character.builder()
                .meaning("anyMeaning")
                .hanzi("ji")
                .pinyin("ji")
                .build());

        assertAll(
                () -> assertEquals(characterId.toString(), character.getId().toString()),
                () -> assertEquals(characterEntity.getHanzi(), character.getHanzi()),
                () -> assertEquals(characterEntity.getPinyin(), character.getPinyin()),
                () -> assertEquals(characterEntity.getTone(), character.getTone()),
                () -> assertEquals(characterEntity.getMeaning(), character.getMeaning()),
                () -> assertTrue(character.isProp()),
                () -> assertNotNull(character.getMovie()),
                () -> assertNotNull(character.getMovie()),
                () -> assertNotNull(character.getMovie().getId()),
//                () -> assertNotNull(character.getMovie().getScene()),
                () -> assertNotNull(character.getMovie().getActor().getId()),
                () -> assertNotNull(character.getMovie().getActor().getName()),
                () -> assertEquals("Ji", character.getMovie().getActor().getName()),
                () -> assertEquals("ji", character.getMovie().getActor().getAssociatedPinyinSound()))
//                () -> assertNotNull(character.getMovie().getLocation()),
//                () -> assertNotNull(character.getMovie().getRoom()))
        ;
    }

    @Test
    void testCreateCharacterBlockedWhenCharacterHanziAlreadyExists() {

        UUID characterId = UUID.fromString("1bfff94a-b70e-4b39-bd2a-be1c0f898589");

        CharacterEntity characterEntity = CharacterEntity.builder()
                .id(characterId)
                .hanzi("anyHanzi")
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
                .tone(CharacterTone.FIRST)
                .prop(true)
                .build();

        Character character0 = Character.builder()
                .id(characterId)
                .hanzi("anyHanzi")
                .pinyin("anyPinyin")
                .meaning("anyMeaning")
                .tone(CharacterTone.FIRST)
                .prop(true)
                .build();

        when(characterRepository.findById(characterId)).thenReturn(Optional.ofNullable(characterEntity));
        when(characterRepository.save(any())).thenReturn(characterEntity);

        Character character = characterService.updateCharacter(characterId, character0);

        assertAll(
                () -> assertEquals(characterId.toString(), character.getId().toString()),
                () -> assertEquals(characterEntity.getHanzi(), character.getHanzi()),
                () -> assertEquals(characterEntity.getPinyin(), character.getPinyin()),
                () -> assertEquals(characterEntity.getTone(), character.getTone()),
                () -> assertEquals(characterEntity.getMeaning(), character.getMeaning()),
                () -> assertTrue(characterEntity.isProp()));
    }

    @Test
    void testGetActorFromCharacter() {
        
//        b-, p-, m-, f-, d-, t-, n-, l-, g-, k-, h-, zh-, ch-, sh-, r-, z-, c-, s-
//        y-, bi, pi, mi-, di-, ti-, ni-, li-, ji-, qi-, xi-
//        w-, bu-, pu, mu-, fu-, du-, tu-, nu-, lu-, gu-, ku-, hu-, zhu-, chu-, shu-, ru-, zu-, cu-, su-
//        yu-, n??-, l??-, ju-, qu-, xu-

        List<ActorEntity> actorEntityList = new ArrayList<>();
        actorEntityList.add(ActorEntity.builder().name("jennie").associatedPinyinSound("ji").build());
        actorEntityList.add(ActorEntity.builder().name("jackie chan").associatedPinyinSound("void").build());
        actorEntityList.add(ActorEntity.builder().name("shakira").associatedPinyinSound("xi").build());
        actorEntityList.add(ActorEntity.builder().name("fidel castro").associatedPinyinSound("f").build());
        actorEntityList.add(ActorEntity.builder().name("brad pitt").associatedPinyinSound("b").build());
        actorEntityList.add(ActorEntity.builder().name("li zi qing").associatedPinyinSound("li").build());
        actorEntityList.add(ActorEntity.builder().name("lex luthor").associatedPinyinSound("lu").build());
        actorEntityList.add(ActorEntity.builder().name("yasser arafat").associatedPinyinSound("yu").build());

        when(actorRepository.findAll()).thenReturn(actorEntityList);
        when(actorRepository.findActorEntityByAssociatedPinyinSound("void")).thenReturn(Optional.of(ActorEntity.builder().name("jackie chan").associatedPinyinSound("void").build()));

        assertAll(
                () -> assertEquals("jennie", characterService.getActorFromCharacter(CharacterEntity.builder().pinyin("jiang").build()).getName()),
                () -> assertEquals("shakira", characterService.getActorFromCharacter(CharacterEntity.builder().pinyin("xian").build()).getName()),
                () -> assertEquals("fidel castro", characterService.getActorFromCharacter(CharacterEntity.builder().pinyin("fei").build()).getName()),
                () -> assertEquals("brad pitt", characterService.getActorFromCharacter(CharacterEntity.builder().pinyin("bang").build()).getName()),
                () -> assertEquals("lex luthor", characterService.getActorFromCharacter(CharacterEntity.builder().pinyin("luo").build()).getName()),
                () -> assertEquals("yasser arafat", characterService.getActorFromCharacter(CharacterEntity.builder().pinyin("yuan").build()).getName()),
                () -> assertEquals("li zi qing", characterService.getActorFromCharacter(CharacterEntity.builder().pinyin("liang").build()).getName()),
                () -> assertEquals("jackie chan", characterService.getActorFromCharacter(CharacterEntity.builder().pinyin("e").build()).getName())
        );
    }
}
