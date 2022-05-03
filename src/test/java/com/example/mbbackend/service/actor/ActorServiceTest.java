package com.example.mbbackend.service.actor;

import com.example.mbbackend.config.ActorFamily;
import com.example.mbbackend.entity.actor.ActorEntity;
import com.example.mbbackend.entity.movie.MovieEntity;
import com.example.mbbackend.model.Actor;
import com.example.mbbackend.model.Movie;
import com.example.mbbackend.repository.actor.ActorRepository;
import com.example.mbbackend.service.impl.actor.ActorServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ActorServiceTest {

    @InjectMocks
    ActorServiceImpl actorService;

    @Mock
    ActorRepository actorRepository;

    @Test
    void testGetAllActors() {

        List<ActorEntity> actorEntityList = new ArrayList<>();
        UUID locationId = UUID.fromString("1bfff94a-b70e-4b39-bd2a-be1c0f898589");

        ActorEntity actorEntity = ActorEntity.builder()
                .id(locationId)
                .name("Shakira")
                .associatedPinyinSound("Ou")
                .family(ActorFamily.FEMALE)
                .imageUrl("anyUrl")
                .build();

        actorEntityList.add(actorEntity);

        when(actorRepository.findAll()).thenReturn(actorEntityList);

        List<Actor> actorList = actorService.getAllActors();
        assertTrue(actorList.size() > 0);

        Actor actor = actorList.get(0);

        assertAll(
                () -> assertEquals(actorEntity.getId().toString(), actor.getId().toString()),
                () -> assertEquals(actorEntity.getName(), actor.getName()),
                () -> assertEquals(actorEntity.getAssociatedPinyinSound(), actor.getAssociatedPinyinSound()),
                () -> assertEquals(actorEntity.getFamily(), actor.getFamily()),
                () -> assertEquals(actorEntity.getImageUrl(), actor.getImageUrl()));
    }

    @Test
    void testGetActor() {

        UUID actorId = UUID.fromString("1bfff94a-b70e-4b39-bd2a-be1c0f898589");

        Optional<ActorEntity> actorEntity = Optional.ofNullable(ActorEntity.builder()
                .id(actorId)
                .name("anyName")
                .associatedPinyinSound("anySound")
                .family(ActorFamily.FEMALE)
                .imageUrl("anyUrl")
                .build());

        when(actorRepository.findById(actorId)).thenReturn(actorEntity);

        Actor actor = actorService.getActor(actorId);

        assertAll(
                () -> assertEquals(actorId.toString(), actor.getId().toString()),
                () -> assertEquals("anyName", actor.getName()),
                () -> assertEquals("anySound", actor.getAssociatedPinyinSound()),
                () -> assertEquals(ActorFamily.FEMALE, actor.getFamily()),
                () -> assertEquals("anyUrl", actor.getImageUrl()));
    }

    @Test
    void testCreateActor() {

        UUID actorId = UUID.fromString("1bfff94a-b70e-4b39-bd2a-be1c0f898589");

        ActorEntity actorEntity = ActorEntity.builder()
                .id(actorId)
                .name("anyName")
                .associatedPinyinSound("ji")
                .family(ActorFamily.FEMALE)
                .imageUrl("imageUrl")
                .build();

        when(actorRepository.save(any())).thenReturn(actorEntity);

        Actor actor = actorService.createActor(Actor.builder()
                .associatedPinyinSound(actorEntity.getAssociatedPinyinSound())
                .family(actorEntity.getFamily())
                .build());

        assertAll(
                () -> assertEquals(actorId.toString(), actor.getId().toString()),
                () -> assertEquals(actorEntity.getName(), actor.getName()),
                () -> assertEquals(actorEntity.getAssociatedPinyinSound(), actor.getAssociatedPinyinSound()),
                () -> assertEquals(actorEntity.getFamily(), actor.getFamily()),
                () -> assertEquals(actorEntity.getImageUrl(), actor.getImageUrl()));
    }

    @Test
    void testCreateActor_WrongSound_ReturnsNull() {

        UUID actorId = UUID.fromString("1bfff94a-b70e-4b39-bd2a-be1c0f898589");

        ActorEntity actorEntity = ActorEntity.builder()
                .id(actorId)
                .name("anyName")
                .associatedPinyinSound("wrongSound")
                .family(ActorFamily.FEMALE)
                .imageUrl("imageUrl")
                .build();

        Actor actor = actorService.createActor(Actor.builder()
                .associatedPinyinSound(actorEntity.getAssociatedPinyinSound())
                .family(actorEntity.getFamily())
                .build());

        assertNull(actor);
    }


    @Test
    void testCheckActor() {

        assertTrue(actorService.checkValidSoundForActor("ji", ActorFamily.FEMALE));
    }

    @Test
    void testDeleteActor() {

        UUID actorId = UUID.fromString("1bfff94a-b70e-4b39-bd2a-be1c0f898589");

        ActorEntity actorEntity = ActorEntity.builder()
                .id(actorId)
                .build();

        when(actorRepository.findById(any())).thenReturn(Optional.ofNullable(actorEntity));

        assertDoesNotThrow(() -> actorService.deleteActor(actorId));
    }

    @Test
    void testDeleteActor_ItemNotFound_ThrowsException() {

        UUID actorId = UUID.fromString("1bfff94a-b70e-4b39-bd2a-be1c0f898589");

        assertThrows(Exception.class, () -> actorService.deleteActor(actorId));
    }

    @Test
    void testUpdateActor() {

        UUID locationId = UUID.fromString("1bfff94a-b70e-4b39-bd2a-be1c0f898589");

        ActorEntity actorEntity = ActorEntity.builder()
                .id(locationId)
                .name("anyName")
                .family(ActorFamily.FEMALE)
                .associatedPinyinSound("ji")
                .imageUrl("anyUrl")
                .build();

        Actor actor0 = Actor.builder()
                .id(locationId)
                .name("anyName")
                .family(ActorFamily.FEMALE)
                .associatedPinyinSound("ji")
                .imageUrl("anyUrl")
                .build();

        when(actorRepository.findById(locationId)).thenReturn(Optional.ofNullable(actorEntity));
        when(actorRepository.save(any())).thenReturn(actorEntity);

        Actor actor = actorService.updateActor(locationId, actor0);

        assertAll(
                () -> assertEquals(locationId.toString(), actor.getId().toString()),
                () -> assertEquals(actorEntity.getName(), actor.getName()),
                () -> assertEquals(actorEntity.getAssociatedPinyinSound(), actor.getAssociatedPinyinSound()),
                () -> assertEquals(actorEntity.getFamily(), actor.getFamily()),
                () -> assertEquals(actorEntity.getImageUrl(), actor.getImageUrl()));
    }

    @Test
    void testGetActorWithMovies() {

        UUID actorId = UUID.fromString("1bfff94a-b70e-4b39-bd2a-be1c0f898589");

        MovieEntity movieEntity = MovieEntity.builder().id(UUID.randomUUID()).build();
        Set<MovieEntity> movieEntitySet = new HashSet<>();
        movieEntitySet.add(movieEntity);

        Map<Object, Object> mapEntity = movieEntitySet.stream().collect(Collectors.toMap(x -> x, x -> 0));
        Map.Entry<Object, Object> entryEntity = mapEntity.entrySet().iterator().next();
        MovieEntity movieEntity1 = (MovieEntity) entryEntity.getKey();

        ActorEntity actorEntity = ActorEntity.builder()
                .id(actorId)
                .name("anyName")
                .family(ActorFamily.FEMALE)
                .associatedPinyinSound("ji")
                .imageUrl("anyUrl")
                .movie(movieEntitySet)
                .build();

        Movie movie = Movie.builder().id(UUID.randomUUID()).build();
        Set<Movie> movieSet = new HashSet<>();
        movieSet.add(movie);

        when(actorRepository.findById(actorId)).thenReturn(Optional.ofNullable(actorEntity));

        Actor actor = actorService.getActorWithMovies(actorId);

        Set<Movie> movie1 = actor.getMovieSet();
        Map<Object, Object> map = movie1.stream().collect(Collectors.toMap(x -> x, x -> 0));
        Map.Entry<Object, Object> entry = map.entrySet().iterator().next();
        Movie movie0 = (Movie) entry.getKey();

        assertAll(
                () -> assertEquals(actorId.toString(), actor.getId().toString()),
                () -> assertNotNull(actorEntity),
                () -> assertEquals(actorEntity.getName(), actor.getName()),
                () -> assertEquals(actorEntity.getAssociatedPinyinSound(), actor.getAssociatedPinyinSound()),
                () -> assertEquals(actorEntity.getFamily(), actor.getFamily()),
                () -> assertEquals(actorEntity.getImageUrl(), actor.getImageUrl()),
                () -> assertEquals(movieEntity1.getId().toString(), movie0.getId().toString()));

    }

}
