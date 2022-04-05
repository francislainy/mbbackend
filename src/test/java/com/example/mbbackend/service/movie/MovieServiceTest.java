package com.example.mbbackend.service.movie;

import com.example.mbbackend.entity.actor.ActorEntity;
import com.example.mbbackend.entity.character.CharacterEntity;
import com.example.mbbackend.entity.location.LocationEntity;
import com.example.mbbackend.entity.movie.MovieEntity;
import com.example.mbbackend.entity.room.RoomEntity;
import com.example.mbbackend.model.Character;
import com.example.mbbackend.model.*;
import com.example.mbbackend.repository.actor.ActorRepository;
import com.example.mbbackend.repository.character.CharacterRepository;
import com.example.mbbackend.repository.location.LocationRepository;
import com.example.mbbackend.repository.movie.MovieRepository;
import com.example.mbbackend.repository.room.RoomRepository;
import com.example.mbbackend.service.impl.movie.MovieServiceImpl;
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
class MovieServiceTest {

    @InjectMocks
    MovieServiceImpl movieService;

    @Mock
    MovieRepository movieRepository;

    @Mock
    CharacterRepository characterRepository;

    @Mock
    ActorRepository actorRepository;

    @Mock
    LocationRepository locationRepository;

    @Mock
    RoomRepository roomRepository;

    @Test
    void testGetAllMovies() {

        UUID movieId = UUID.fromString("1bfff94a-b70e-4b39-bd2a-be1c0f898589");
        UUID characterId = UUID.fromString("2afff94a-b70e-4b39-bd2a-be1c0f898589");
        UUID actorId = UUID.fromString("2afff94a-b70e-4b39-bd2a-be1c0f898589");
        UUID locationId = UUID.fromString("2afff94a-b70e-4b39-bd2a-be1c0f898589");
        UUID roomId = UUID.fromString("2afff94a-b70e-4b39-bd2a-be1c0f898589");

        CharacterEntity characterEntity = CharacterEntity.builder()
                .id(characterId)
                .hanzi("anyHanzi")
                .pinyin("anyPinyin")
                .meaning("anyMeaning")
                .build();

        ActorEntity actorEntity = ActorEntity.builder()
                .id(actorId)
                .name("anyName")
                .build();

        LocationEntity locationEntity = LocationEntity.builder()
                .id(locationId)
                .title("anyTitle")
                .associatedPinyinSound("anySound")
                .build();

        RoomEntity roomEntity = RoomEntity.builder()
                .id(roomId)
                .title("anyTitle")
                .build();

        MovieEntity movieEntity = MovieEntity.builder()
                .id(movieId)
                .scene("anyScene")
                .imageUrl("anyUrl")
                .actor(actorEntity)
                .character(characterEntity)
                .location(locationEntity)
                .room(roomEntity)
                .build();

        List<MovieEntity> movieEntityList = new ArrayList<>();
        movieEntityList.add(movieEntity);

        when(actorRepository.findById(characterId)).thenReturn(Optional.ofNullable(actorEntity));
        when(characterRepository.findById(characterId)).thenReturn(Optional.ofNullable(characterEntity));
        when(locationRepository.findById(locationId)).thenReturn(Optional.ofNullable(locationEntity));
        when(roomRepository.findById(roomId)).thenReturn(Optional.ofNullable(roomEntity));
        when(movieRepository.findAll()).thenReturn(movieEntityList);

        List<Movie> movieList = movieService.getAllMovies();

        assertTrue(movieList.size() > 0);

        Movie movie = movieList.get(0);

        assertAll(
                () -> assertEquals(movieEntity.getId().toString(), movie.getId().toString()),
                () -> assertEquals(movieEntity.getScene(), movie.getScene()),
                () -> assertEquals(movieEntity.getImageUrl(), movie.getImageUrl()),
                () -> assertEquals(movieEntity.getCharacter().getId(), movie.getCharacter().getId()),
                () -> assertEquals(movieEntity.getCharacter().getHanzi(), movie.getCharacter().getHanzi()),
                () -> assertEquals(movieEntity.getCharacter().getPinyin(), movie.getCharacter().getPinyin()),
                () -> assertEquals(movieEntity.getCharacter().getMeaning(), movie.getCharacter().getMeaning()),
                () -> assertEquals(movieEntity.getActor().getId(), movie.getActor().getId()),
                () -> assertEquals(movieEntity.getActor().getName(), movie.getActor().getName()),
                () -> assertEquals(movieEntity.getLocation().getId(), movie.getLocation().getId()),
                () -> assertEquals(movieEntity.getLocation().getTitle(), movie.getLocation().getTitle()),
                () -> assertEquals(movieEntity.getRoom().getId(), movie.getRoom().getId()),
                () -> assertEquals(movieEntity.getRoom().getTitle(), movie.getRoom().getTitle()));
    }

    @Test
    void testGetMovie() {

        UUID movieId = UUID.fromString("1bfff94a-b70e-4b39-bd2a-be1c0f898589");
        UUID characterId = UUID.fromString("2afff94a-b70e-4b39-bd2a-be1c0f898589");
        UUID actorId = UUID.fromString("2afff94a-b70e-4b39-bd2a-be1c0f898589");
        UUID locationId = UUID.fromString("2afff94a-b70e-4b39-bd2a-be1c0f898589");
        UUID roomId = UUID.fromString("2afff94a-b70e-4b39-bd2a-be1c0f898589");

        Optional<CharacterEntity> optionalCharacterEntity = Optional.ofNullable(CharacterEntity.builder()
                .id(characterId)
                .hanzi("anyHanzi")
                .pinyin("anyPinyin")
                .meaning("anyMeaning")
                .build());

        Optional<ActorEntity> optionalActorEntity = Optional.ofNullable(ActorEntity.builder()
                .id(actorId)
                .name("anyName")
                .build());

        Optional<LocationEntity> optionalLocationEntity = Optional.ofNullable(LocationEntity.builder()
                .id(locationId)
                .title("anyTitle")
                .associatedPinyinSound("anySound")
                .build());

        Optional<RoomEntity> optionalRoomEntity = Optional.ofNullable(RoomEntity.builder()
                .id(roomId)
                .title("anyTitle")
                .build());

        Optional<MovieEntity> optionalMovieEntity = Optional.ofNullable(MovieEntity.builder()
                .id(movieId)
                .scene("anyScene")
                .imageUrl("anyUrl")
                .actor(ActorEntity.builder()
                        .id(actorId)
                        .name("anyName")
                        .build())
                .character(CharacterEntity.builder()
                        .id(characterId)
                        .hanzi("anyHanzi")
                        .meaning("anyMeaning")
                        .pinyin("anyPinyin")
                        .build())
                .location(LocationEntity.builder()
                        .id(locationId)
                        .title("anyTitle")
                        .associatedPinyinSound("anySound")
                        .build())
                .room(RoomEntity.builder()
                        .id(roomId)
                        .title("title")
                        .build())
                .build());

        when(actorRepository.findById(characterId)).thenReturn(optionalActorEntity);
        when(characterRepository.findById(characterId)).thenReturn(optionalCharacterEntity);
        when(locationRepository.findById(locationId)).thenReturn(optionalLocationEntity);
        when(roomRepository.findById(roomId)).thenReturn(optionalRoomEntity);
        when(movieRepository.findById(movieId)).thenReturn(optionalMovieEntity);

        Movie movie = movieService.getMovie(movieId);

        assertAll(
                () -> assertEquals(movieId.toString(), movie.getId().toString()),
                () -> assertEquals("anyScene", movie.getScene()),
                () -> assertEquals("anyUrl", movie.getImageUrl()),
                () -> assertEquals(actorId.toString(), movie.getActor().getId().toString()),
                () -> assertEquals("anyName", movie.getActor().getName()),
                () -> assertEquals(characterId.toString(), movie.getCharacter().getId().toString()),
                () -> assertEquals("anyHanzi", movie.getCharacter().getHanzi()),
                () -> assertEquals("anyPinyin", movie.getCharacter().getPinyin()),
                () -> assertEquals("anyMeaning", movie.getCharacter().getMeaning()),
                () -> assertEquals(locationId.toString(), movie.getLocation().getId().toString()),
                () -> assertEquals("anyTitle", movie.getLocation().getTitle()),
                () -> assertEquals("anySound", movie.getLocation().getAssociatedPinyinSound()),
                () -> assertEquals(roomId.toString(), movie.getRoom().getId().toString()),
                () -> assertEquals("anyTitle", movie.getRoom().getTitle()));
    }

    @Test
    void testCreateMovie() {

        UUID movieId = UUID.fromString("1bfff94a-b70e-4b39-bd2a-be1c0f898589");
        UUID characterId = UUID.fromString("2afff94a-b70e-4b39-bd2a-be1c0f898589");
        UUID actorId = UUID.fromString("2afff94a-b70e-4b39-bd2a-be1c0f898589");
        UUID locationId = UUID.fromString("2afff94a-b70e-4b39-bd2a-be1c0f898589");
        UUID roomId = UUID.fromString("2afff94a-b70e-4b39-bd2a-be1c0f898589");

        Optional<CharacterEntity> optionalCharacterEntity = Optional.ofNullable(CharacterEntity.builder()
                .id(characterId)
                .hanzi("anyHanzi")
                .pinyin("anyPinyin")
                .meaning("anyMeaning")
                .build());

        CharacterEntity characterEntity = optionalCharacterEntity.get();

        MovieEntity movieEntity = MovieEntity.builder()
                .id(movieId)
                .scene("anyScene")
                .imageUrl("anyUrl")
                .character(CharacterEntity.builder()
                        .id(characterId)
                        .hanzi(characterEntity.getHanzi())
                        .pinyin(characterEntity.getPinyin())
                        .meaning(characterEntity.getMeaning())
                        .build())
                .actor(ActorEntity.builder()
                        .id(actorId)
                        .name("anyName")
                        .build())
                .location(LocationEntity.builder()
                        .id(locationId)
                        .title("anyTitle")
                        .associatedPinyinSound("anySound")
                        .build())
                .room(RoomEntity.builder()
                        .id(roomId)
                        .title("anyTitle")
                        .build())
                .build();

        when(characterRepository.save(any())).thenReturn(characterEntity);
        when(movieRepository.save(any())).thenReturn(movieEntity);
        when(actorRepository.findById(any())).thenReturn(Optional.ofNullable(ActorEntity.builder()
                .id(actorId)
                .name("anyName")
                .build()));
        when(locationRepository.findById(any())).thenReturn(Optional.ofNullable(LocationEntity.builder()
                .id(locationId)
                .title("anyTitle")
                .associatedPinyinSound("anySound")
                .build()));
        when(roomRepository.findById(any())).thenReturn(Optional.ofNullable(RoomEntity.builder()
                .id(roomId)
                .title("anyTitle")
                .build()));

        Movie movie0 = Movie.builder()
                .id(movieId)
                .scene(movieEntity.getScene())
                .imageUrl(movieEntity.getScene())
                .character(Character.builder().id(characterId).build())
                .actor(Actor.builder().id(actorId).build())
                .location(Location.builder().id(locationId).build())
                .room(Room.builder().id(roomId).build())
                .build();

        Movie movie = movieService.createMovie(movie0);

        assertAll(
                () -> assertEquals(movieId.toString(), movie.getId().toString()),
                () -> assertEquals(movieEntity.getScene(), movie.getScene()),
                () -> assertEquals(movieEntity.getImageUrl(), movie.getImageUrl()),
                () -> assertEquals(movieEntity.getActor().getId().toString(), movie.getActor().getId().toString()),
                () -> assertEquals(movieEntity.getActor().getName(), movie.getActor().getName()),
                () -> assertEquals(movieEntity.getCharacter().getId().toString(), movie.getCharacter().getId().toString()),
                () -> assertEquals(movieEntity.getCharacter().getHanzi(), movie.getCharacter().getHanzi()),
                () -> assertEquals(movieEntity.getCharacter().getPinyin(), movie.getCharacter().getPinyin()),
                () -> assertEquals(movieEntity.getCharacter().getMeaning(), movie.getCharacter().getMeaning()),
                () -> assertEquals(movieEntity.getLocation().getId().toString(), movie.getLocation().getId().toString()),
                () -> assertEquals(movieEntity.getLocation().getTitle(), movie.getLocation().getTitle()),
                () -> assertEquals(movieEntity.getLocation().getAssociatedPinyinSound(), movie.getLocation().getAssociatedPinyinSound()),
                () -> assertEquals(movieEntity.getRoom().getId().toString(), movie.getRoom().getId().toString()),
                () -> assertEquals(movieEntity.getRoom().getTitle(), movie.getRoom().getTitle()));
    }

    @Test
    void testDeleteMovie() {

        UUID movieId = UUID.fromString("1bfff94a-b70e-4b39-bd2a-be1c0f898589");

        MovieEntity movieEntity = MovieEntity.builder()
                .id(movieId)
                .build();

        when(movieRepository.findById(any())).thenReturn(Optional.ofNullable(movieEntity));

        assertDoesNotThrow(() -> movieService.deleteMovie(movieId));
    }

    @Test
    void testDeleteMovie_ItemNotFound_ThrowsException() {

        UUID movieId = UUID.fromString("1bfff94a-b70e-4b39-bd2a-be1c0f898589");

        assertThrows(Exception.class, () -> movieService.deleteMovie(movieId));
    }

    @Test
    void testUpdateMovie() {

        UUID movieId = UUID.fromString("1bfff94a-b70e-4b39-bd2a-be1c0f898589");
        UUID characterId = UUID.fromString("2afff94a-b70e-4b39-bd2a-be1c0f898589");
        UUID actorId = UUID.fromString("2afff94a-b70e-4b39-bd2a-be1c0f898589");
        UUID locationId = UUID.fromString("2afff94a-b70e-4b39-bd2a-be1c0f898589");
        UUID roomId = UUID.fromString("2afff94a-b70e-4b39-bd2a-be1c0f898589");

        MovieEntity movieEntity = MovieEntity.builder()
                .id(movieId)
                .scene("anyScene")
                .imageUrl("anyUrl")
                .character(CharacterEntity.builder().id(characterId).build())
                .actor(ActorEntity.builder().id(actorId).build())
                .location(LocationEntity.builder().id(locationId).build())
                .room(RoomEntity.builder().id(roomId).build())
                .build();

        Optional<MovieEntity> movieEntity1 = Optional.ofNullable(MovieEntity.builder()
                .id(movieId)
                .scene("anyScene")
                .imageUrl("anyUrl")
                .character(CharacterEntity.builder().id(characterId).build())
                .actor(ActorEntity.builder().id(actorId).build())
                .location(LocationEntity.builder().id(locationId).build())
                .room(RoomEntity.builder().id(roomId).build())
                .build());

        Movie movie0 = Movie.builder()
                .id(movieId)
                .scene("anyScene")
                .imageUrl("anyUrl")
                .character(Character.builder().id(characterId).build())
                .actor(Actor.builder().id(actorId).build())
                .location(Location.builder().id(locationId).build())
                .room(Room.builder().id(roomId).build())
                .build();

        when(movieRepository.findById(movieId)).thenReturn(movieEntity1);
        when(movieRepository.save(any())).thenReturn(movieEntity);

        Movie movie = movieService.updateMovie(movieId, movie0);

        assertAll(
                () -> assertEquals(movieId.toString(), movie.getId().toString()),
                () -> assertEquals(movieEntity.getScene(), movie.getScene()),
                () -> assertEquals(movieEntity.getCharacter().getId(), movie.getCharacter().getId()),
                () -> assertEquals(movieEntity.getActor().getId(), movie.getActor().getId()),
                () -> assertEquals(movieEntity.getLocation().getId(), movie.getLocation().getId()),
                () -> assertEquals(movieEntity.getRoom().getId(), movie.getRoom().getId()));
    }

}
