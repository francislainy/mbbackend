package com.example.mbbackend.repository;

import com.example.mbbackend.entity.movie.MovieEntity;
import com.example.mbbackend.repository.movie.MovieCustomRepository;
import com.example.mbbackend.repository.movie.MovieRepository;
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

    @Test
    void findMoviesWithCustomFilter() {
        UUID movieId = UUID.fromString("02c903f7-7a55-470d-8449-cf7587f5a3fb");
        MovieEntity movieEntity = new MovieEntity();
        movieEntity.setId(movieId);
        movieEntity.setScene("anyScene");
        movieEntity = movieRepository.save(movieEntity);
        entityManager.persist(movieEntity);
        List<MovieEntity> fetchedMovie = movieCustomRepository.find(movieId, "anyScene");
        assertNotNull(fetchedMovie);
//        assertTrue(fetchedMovie.size() > 0);
    }

}
