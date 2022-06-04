package com.example.mbbackend.repository;

import com.example.mbbackend.entity.actor.ActorEntity;
import com.example.mbbackend.entity.character.CharacterEntity;
import com.example.mbbackend.entity.location.LocationEntity;
import com.example.mbbackend.entity.movie.MovieEntity;
import com.example.mbbackend.entity.room.RoomEntity;
import com.example.mbbackend.repository.actor.ActorRepository;
import com.example.mbbackend.repository.character.CharacterRepository;
import com.example.mbbackend.repository.location.LocationRepository;
import com.example.mbbackend.repository.movie.MovieCustomRepository;
import com.example.mbbackend.repository.movie.MovieRepository;
import com.example.mbbackend.repository.room.RoomRepository;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import(MovieCustomRepository.class)
class MovieCustomRepositoryTest {

    @Autowired
    MovieRepository movieRepository;

    @Autowired
    EntityManager entityManager;

    @Autowired
    MovieCustomRepository movieCustomRepository;

    @Autowired
    CharacterRepository characterRepository;

    @Autowired
    ActorRepository actorRepository;

    @Autowired
    LocationRepository locationRepository;

    @Autowired
    RoomRepository roomRepository;

    @Test
    void findMoviesWithCustomFilter() {

        CharacterEntity characterEntity = CharacterEntity.builder()
                .hanzi("anyHanzi")
                .pinyin("anyPinyin")
                .build();
        characterRepository.save(characterEntity);

        ActorEntity actorEntity = ActorEntity.builder().build();
        actorRepository.save(actorEntity);

        LocationEntity locationEntity = LocationEntity.builder().build();
        locationRepository.save(locationEntity);

        RoomEntity roomEntity = RoomEntity.builder().build();
        roomRepository.save(roomEntity);

        MovieEntity movieEntity = MovieEntity.builder()
                .character(characterEntity)
                .actor(actorEntity)
                .location(locationEntity)
                .room(roomEntity)
                .scene("anyScene")
                .build();
        movieEntity = movieRepository.save(movieEntity);

        List<MovieEntity> fetchedMovie = movieCustomRepository.find(movieEntity.getId(), movieEntity.getCharacter().getId(), movieEntity.getCharacter().getHanzi(), movieEntity.getCharacter().getPinyin(), movieEntity.getScene(), movieEntity.getActor().getId(), movieEntity.getLocation().getId(), movieEntity.getRoom().getId());
        assertNotNull(fetchedMovie);
        assertTrue(fetchedMovie.size() > 0);
    }

}
