package com.example.mbbackend.service.character;

import com.example.mbbackend.config.CharacterTone;
import com.example.mbbackend.entity.actor.ActorEntity;
import com.example.mbbackend.entity.character.CharacterEntity;
import com.example.mbbackend.entity.location.LocationEntity;
import com.example.mbbackend.entity.movie.MovieEntity;
import com.example.mbbackend.entity.room.RoomEntity;
import com.example.mbbackend.model.Character;
import com.example.mbbackend.repository.actor.ActorRepository;
import com.example.mbbackend.repository.character.CharacterRepository;
import com.example.mbbackend.repository.location.LocationRepository;
import com.example.mbbackend.repository.movie.MovieRepository;
import com.example.mbbackend.repository.room.RoomRepository;
import com.example.mbbackend.service.impl.character.CharacterServiceImpl;
import org.jetbrains.annotations.NotNull;
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

    @Mock
    ActorRepository actorRepository;

    @Mock
    LocationRepository locationRepository;

    @Mock
    RoomRepository roomRepository;

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
                        .name("anyName")
                        .associatedPinyinSound("ji")
                        .build())
                .location(LocationEntity.builder()
                        .id(UUID.randomUUID())
                        .title("anyTitle")
                        .build())
                .room(RoomEntity.builder()
                        .id(UUID.randomUUID())
                        .title("fourth room")
                        .tone(CharacterTone.FOURTH)
                        .build())
                .build();

        List<RoomEntity> roomEntityList = new ArrayList<>();
        roomEntityList.add(RoomEntity.builder().title("second room").tone(CharacterTone.SECOND).build());
        roomEntityList.add(RoomEntity.builder().title("first room").tone(CharacterTone.FIRST).build());
        roomEntityList.add(RoomEntity.builder().title("third room").tone(CharacterTone.THIRD).build());
        roomEntityList.add(RoomEntity.builder().title("fourth room").tone(CharacterTone.FOURTH).build());
        roomEntityList.add(RoomEntity.builder().title("fifth room").tone(CharacterTone.FIFTH).build());

        when(roomRepository.findAll()).thenReturn(roomEntityList);

        when(characterRepository.save(any())).thenReturn(characterEntity);
        when(movieRepository.save(any())).thenReturn(movieEntity);
        List<ActorEntity> actorEntityList = getActorEntities();
        when(actorRepository.findAll()).thenReturn(actorEntityList);

        Character character = characterService.createCharacter(Character.builder()
                .meaning("anyMeaning")
                .hanzi("anyHanzi")
                .pinyin("ji")
                .tone(CharacterTone.FOURTH)
                .build());

        assertAll(
                () -> assertEquals(characterId.toString(), character.getId().toString()),
                () -> assertEquals(characterEntity.getHanzi(), character.getHanzi()),
                () -> assertEquals(characterEntity.getPinyin(), character.getPinyin()),
                () -> assertEquals(characterEntity.getTone(), character.getTone()),
                () -> assertEquals(characterEntity.getMeaning(), character.getMeaning()),
                () -> assertTrue(character.isProp()),
                () -> assertNotNull(character.getMovie()),
                () -> assertNotNull(character.getMovie().getId()),
                () -> assertTrue(character.getMovie().getSuggestedMovie()),
                () -> assertNotNull(character.getMovie().getScene()),
                () -> assertNotNull(character.getMovie().getActor().getId()),
                () -> assertNotNull(character.getMovie().getActor().getName()),
                () -> assertEquals("anyName", character.getMovie().getActor().getName()),
                () -> assertEquals("ji", character.getMovie().getActor().getAssociatedPinyinSound()),
                () -> assertNotNull(character.getMovie().getLocation()),
                () -> assertEquals("fourth room", character.getMovie().getRoom().getTitle()),
                () -> assertEquals(CharacterTone.FOURTH, character.getMovie().getRoom().getTone()),
                () -> assertNotNull(character.getMovie().getRoom()))
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

        assertAll(() -> assertNull(character));
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
//        yu-, nü-, lü-, ju-, qu-, xu-

        List<ActorEntity> actorEntityList = new ArrayList<>();
        actorEntityList.add(ActorEntity.builder().name("jennie").associatedPinyinSound("ji").build());
        actorEntityList.add(ActorEntity.builder().name("jackie chan").associatedPinyinSound("void").build());
        actorEntityList.add(ActorEntity.builder().name("shakira").associatedPinyinSound("xi").build());
        actorEntityList.add(ActorEntity.builder().name("fidel castro").associatedPinyinSound("f").build());
        actorEntityList.add(ActorEntity.builder().name("brad pitt").associatedPinyinSound("b").build());
        actorEntityList.add(ActorEntity.builder().name("li zi qing").associatedPinyinSound("li").build());
        actorEntityList.add(ActorEntity.builder().name("lex luthor").associatedPinyinSound("lu").build());
        actorEntityList.add(ActorEntity.builder().name("yasser arafat").associatedPinyinSound("yu").build());
        actorEntityList.add(ActorEntity.builder().name("chitaozinho").associatedPinyinSound("ch").build());
        actorEntityList.add(ActorEntity.builder().name("prince").associatedPinyinSound("p").build());

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
                () -> assertEquals("prince", characterService.getActorFromCharacter(CharacterEntity.builder().pinyin("pa").build()).getName()),
                () -> assertEquals("chitaozinho", characterService.getActorFromCharacter(CharacterEntity.builder().pinyin("chou").build()).getName()),
                () -> assertEquals("jackie chan", characterService.getActorFromCharacter(CharacterEntity.builder().pinyin("e").build()).getName())
        );
    }

    @Test
    void testGetLocationFromCharacter() {

        List<ActorEntity> actorEntityList = getActorEntities();
        when(actorRepository.findAll()).thenReturn(actorEntityList);

        List<LocationEntity> locationEntity = new ArrayList<>();
        locationEntity.add(LocationEntity.builder().title("south london").associatedPinyinSound("ou").build());
        locationEntity.add(LocationEntity.builder().title("england").associatedPinyinSound("ang").build());
        locationEntity.add(LocationEntity.builder().title("adailton's house").associatedPinyinSound("ei").build());
        locationEntity.add(LocationEntity.builder().title("lonnier's house").associatedPinyinSound("ai").build());
        locationEntity.add(LocationEntity.builder().title("aunt's house").associatedPinyinSound("an").build());
        locationEntity.add(LocationEntity.builder().title("gym").associatedPinyinSound("ong").build());
        locationEntity.add(LocationEntity.builder().title("amie's house").associatedPinyinSound("a").build());
        locationEntity.add(LocationEntity.builder().title("childhood home").associatedPinyinSound("void").build());

        when(locationRepository.findAll()).thenReturn(locationEntity);
        when(locationRepository.findLocationEntityByAssociatedPinyinSound("void")).thenReturn(Optional.of(LocationEntity.builder().title("childhood home").associatedPinyinSound("void").build()));

        assertAll(
                () -> assertEquals("south london", characterService.getLocationFromCharacter(CharacterEntity.builder().pinyin("chou").build()).getTitle()),
                () -> assertEquals("england", characterService.getLocationFromCharacter(CharacterEntity.builder().pinyin("jiang").build()).getTitle()),
                () -> assertEquals("aunt's house", characterService.getLocationFromCharacter(CharacterEntity.builder().pinyin("xian").build()).getTitle()),
                () -> assertEquals("aunt's house", characterService.getLocationFromCharacter(CharacterEntity.builder().pinyin("yuan").build()).getTitle()),
                () -> assertEquals("england", characterService.getLocationFromCharacter(CharacterEntity.builder().pinyin("bang").build()).getTitle()),
                () -> assertEquals("amie's house", characterService.getLocationFromCharacter(CharacterEntity.builder().pinyin("pa").build()).getTitle()),
                () -> assertEquals("childhood home", characterService.getLocationFromCharacter(CharacterEntity.builder().pinyin("e").build()).getTitle())
        );
    }

    @Test
    void testGetRoomFromCharacter() {

        RoomEntity firstRoom = RoomEntity.builder().title("first room").tone(CharacterTone.FIRST).build();
        RoomEntity secondRoom = RoomEntity.builder().title("second room").tone(CharacterTone.SECOND).build();
        RoomEntity thirdRoom = RoomEntity.builder().title("third room").tone(CharacterTone.THIRD).build();
        RoomEntity fourthRoom = RoomEntity.builder().title("fourth room").tone(CharacterTone.FOURTH).build();
        RoomEntity fifthRoom = RoomEntity.builder().title("fifth room").tone(CharacterTone.FIFTH).build();

        when(roomRepository.findRoomByTone("1")).thenReturn(firstRoom);
        when(roomRepository.findRoomByTone("2")).thenReturn(secondRoom);
        when(roomRepository.findRoomByTone("3")).thenReturn(thirdRoom);
        when(roomRepository.findRoomByTone("4")).thenReturn(fourthRoom);
        when(roomRepository.findRoomByTone("5")).thenReturn(fifthRoom);

        assertAll(
                () -> assertEquals("first room", characterService.getRoomFromCharacter(CharacterEntity.builder().tone(CharacterTone.FIRST).build()).getTitle()),
                () -> assertEquals("second room", characterService.getRoomFromCharacter(CharacterEntity.builder().tone(CharacterTone.SECOND).build()).getTitle()),
                () -> assertEquals("third room", characterService.getRoomFromCharacter(CharacterEntity.builder().tone(CharacterTone.THIRD).build()).getTitle()),
                () -> assertEquals("fourth room", characterService.getRoomFromCharacter(CharacterEntity.builder().tone(CharacterTone.FOURTH).build()).getTitle()),
                () -> assertEquals("fifth room", characterService.getRoomFromCharacter(CharacterEntity.builder().tone(CharacterTone.FIFTH).build()).getTitle())
        );

    }

    @NotNull
    private List<ActorEntity> getActorEntities() {
        List<ActorEntity> actorEntityList = new ArrayList<>();
        actorEntityList.add(ActorEntity.builder().name("jennie").associatedPinyinSound("ji").build());
        actorEntityList.add(ActorEntity.builder().name("jackie chan").associatedPinyinSound("void").build());
        actorEntityList.add(ActorEntity.builder().name("shakira").associatedPinyinSound("xi").build());
        actorEntityList.add(ActorEntity.builder().name("fidel castro").associatedPinyinSound("f").build());
        actorEntityList.add(ActorEntity.builder().name("brad pitt").associatedPinyinSound("b").build());
        actorEntityList.add(ActorEntity.builder().name("li zi qing").associatedPinyinSound("li").build());
        actorEntityList.add(ActorEntity.builder().name("lex luthor").associatedPinyinSound("lu").build());
        actorEntityList.add(ActorEntity.builder().name("yasser arafat").associatedPinyinSound("yu").build());
        actorEntityList.add(ActorEntity.builder().name("strong man").associatedPinyinSound("o").build());
        actorEntityList.add(ActorEntity.builder().name("chitaozinho").associatedPinyinSound("ch").build());
        actorEntityList.add(ActorEntity.builder().name("prince").associatedPinyinSound("p").build());
        actorEntityList.add(ActorEntity.builder().name("mike tyson").associatedPinyinSound("m").build());
        return actorEntityList;
    }

}
