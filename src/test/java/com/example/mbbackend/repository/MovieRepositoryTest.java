package com.example.mbbackend.repository;

import com.example.mbbackend.entity.actor.ActorEntity;
import com.example.mbbackend.entity.movie.MovieEntity;
import com.example.mbbackend.repository.actor.ActorRepository;
import com.example.mbbackend.repository.movie.MovieRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class CategoryRepositoryTest {

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private ActorRepository actorRepository;


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

        UUID actorId = UUID.fromString("02c903f7-7a55-470d-8449-cf7587f5a3fb");
        ActorEntity actorEntity = ActorEntity.builder().id(actorId).build();

        actorEntity = actorRepository.save(actorEntity);

        MovieEntity movieEntity = new MovieEntity();
        movieEntity.setScene("anyScene");
        movieEntity.setActor(actorEntity);
        movieEntity = movieRepository.save(movieEntity);
        List<MovieEntity> fetchedMovie = movieRepository.findMoviesByActorId(actorId);
        assertNotNull(fetchedMovie);
    }

}
