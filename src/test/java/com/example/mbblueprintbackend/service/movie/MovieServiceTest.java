package com.example.mbblueprintbackend.service.movie;

import com.example.mbblueprintbackend.entity.actor.ActorEntity;
import com.example.mbblueprintbackend.entity.character.CharacterEntity;
import com.example.mbblueprintbackend.entity.location.LocationEntity;
import com.example.mbblueprintbackend.entity.movie.MovieEntity;
import com.example.mbblueprintbackend.entity.room.RoomEntity;
import com.example.mbblueprintbackend.model.Character;
import com.example.mbblueprintbackend.model.*;
import com.example.mbblueprintbackend.repository.movie.MovieRepository;
import com.example.mbblueprintbackend.service.impl.movie.MovieServiceImpl;
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

    @Test
    void testGetAllMovies() {

        UUID movieId = UUID.fromString("1bfff94a-b70e-4b39-bd2a-be1c0f898589");
        UUID characterId = UUID.fromString("2afff94a-b70e-4b39-bd2a-be1c0f898589");
        UUID actorId = UUID.fromString("2afff94a-b70e-4b39-bd2a-be1c0f898589");
        UUID locationId = UUID.fromString("2afff94a-b70e-4b39-bd2a-be1c0f898589");
        UUID roomId = UUID.fromString("2afff94a-b70e-4b39-bd2a-be1c0f898589");

        MovieEntity movieEntity = MovieEntity.builder()
                .id(movieId)
                .scene("anyScene")
                .imageUrl("anyUrl")
                .actor(ActorEntity.builder().id(actorId).build())
                .character(CharacterEntity.builder().id(characterId).build())
                .location(LocationEntity.builder().id(locationId).build())
                .room(RoomEntity.builder().id(roomId).build())
                .build();

        List<MovieEntity> movieEntityList = new ArrayList<>();
        movieEntityList.add(movieEntity);

        when(movieRepository.findAll()).thenReturn(movieEntityList);

        List<Movie> movieList = movieService.getAllMovies();

        assertTrue(movieList.size() > 0);

        Movie movie = movieList.get(0);

        assertAll(
                () -> assertEquals(movieEntity.getId().toString(), movie.getId().toString()),
                () -> assertEquals(movieEntity.getScene(), movie.getScene()),
                () -> assertEquals(movieEntity.getImageUrl(), movie.getImageUrl()),
                () -> assertEquals(movieEntity.getCharacter().getId(), movie.getCharacter().getId()),
                () -> assertEquals(movieEntity.getActor().getId(), movie.getActor().getId()),
                () -> assertEquals(movieEntity.getLocation().getId(), movie.getLocation().getId()),
                () -> assertEquals(movieEntity.getRoom().getId(), movie.getRoom().getId()));
    }

    @Test
    void testGetSingleMovie() {

        UUID movieId = UUID.fromString("1bfff94a-b70e-4b39-bd2a-be1c0f898589");
        UUID characterId = UUID.fromString("2afff94a-b70e-4b39-bd2a-be1c0f898589");
        UUID actorId = UUID.fromString("2afff94a-b70e-4b39-bd2a-be1c0f898589");
        UUID locationId = UUID.fromString("2afff94a-b70e-4b39-bd2a-be1c0f898589");
        UUID roomId = UUID.fromString("2afff94a-b70e-4b39-bd2a-be1c0f898589");

        Optional<MovieEntity> optionalLocationEntity = Optional.ofNullable(MovieEntity.builder()
                .id(movieId)
                .scene("anyScene")
                .imageUrl("anyUrl")
                .actor(ActorEntity.builder().id(actorId).build())
                .character(CharacterEntity.builder().id(characterId).build())
                .location(LocationEntity.builder().id(locationId).build())
                .room(RoomEntity.builder().id(roomId).build())
                .build());

        when(movieRepository.findById(movieId)).thenReturn(optionalLocationEntity);

        Movie movie = movieService.getMovie(movieId);

        assertAll(
                () -> assertEquals(movieId.toString(), movie.getId().toString()),
                () -> assertEquals("anyScene", movie.getScene()),
                () -> assertEquals("anyUrl", movie.getImageUrl()),
                () -> assertEquals(actorId.toString(), movie.getActor().getId().toString()),
                () -> assertEquals(characterId.toString(), movie.getCharacter().getId().toString()),
                () -> assertEquals(locationId.toString(), movie.getLocation().getId().toString()),
                () -> assertEquals(roomId.toString(), movie.getRoom().getId().toString()));
    }

    @Test
    void testCreateMovie() {

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

        when(movieRepository.save(any())).thenReturn(movieEntity);

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
                () -> assertEquals(movieEntity.getCharacter().getId().toString(), movie.getCharacter().getId().toString()),
                () -> assertEquals(movieEntity.getLocation().getId().toString(), movie.getLocation().getId().toString()),
                () -> assertEquals(movieEntity.getRoom().getId().toString(), movie.getRoom().getId().toString()));
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
