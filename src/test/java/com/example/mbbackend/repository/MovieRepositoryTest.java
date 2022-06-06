package com.example.mbbackend.repository;

import com.example.mbbackend.config.BaseIntegrationTest;
import com.example.mbbackend.entity.actor.ActorEntity;
import com.example.mbbackend.entity.character.CharacterEntity;
import com.example.mbbackend.entity.movie.MovieEntity;
import com.example.mbbackend.repository.actor.ActorRepository;
import com.example.mbbackend.repository.character.CharacterRepository;
import com.example.mbbackend.repository.movie.MovieRepository;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class MovieRepositoryTest extends BaseIntegrationTest {

    @Autowired
    MovieRepository movieRepository;

    @Autowired
    ActorRepository actorRepository;

    @Autowired
    CharacterRepository characterRepository;

    @Autowired
    EntityManager entityManager;

    @Test
    void findOne() {
        MovieEntity movieEntity = new MovieEntity();
        movieEntity.setScene("anyScene");
        movieEntity = movieRepository.save(movieEntity);
        MovieEntity fetchedMovie = movieRepository.findById(movieEntity.getId()).get();
        assertNotNull(fetchedMovie);
    }

    @Test
    void findMovies() {
        MovieEntity movieEntity = new MovieEntity();
        movieEntity.setScene("anyScene");
        List<MovieEntity> fetchedMovie = movieRepository.findAll();
        assertNotNull(fetchedMovie);
    }

    @Test
    void findMoviesForActor() {

        ActorEntity actorEntity = ActorEntity.builder().build();
        actorEntity = actorRepository.save(actorEntity);

        MovieEntity movieEntity = new MovieEntity();
        movieEntity.setScene("anyScene");
        movieEntity.setActor(actorEntity);
        movieEntity = movieRepository.save(movieEntity);

        List<MovieEntity> fetchedMovieList = movieRepository.findMoviesByActorId(actorEntity.getId());
        assertNotNull(fetchedMovieList);
        assertTrue(fetchedMovieList.size() > 0);
    }

    @Test
    void findMoviesByCharacterId() {

        CharacterEntity characterEntity = CharacterEntity.builder().build();
        characterEntity = characterRepository.save(characterEntity);

        MovieEntity movieEntity = new MovieEntity();
        movieEntity.setScene("anyScene");
        movieEntity.setCharacter(characterEntity);
        movieEntity = movieRepository.save(movieEntity);

        List<MovieEntity> fetchedMovieList = movieRepository.findMoviesByCharacterId(characterEntity.getId());
        assertNotNull(fetchedMovieList);
        assertTrue(fetchedMovieList.size() > 0);
    }

}
