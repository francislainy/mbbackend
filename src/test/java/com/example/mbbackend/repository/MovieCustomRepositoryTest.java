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
        MovieEntity movieEntity = new MovieEntity();
        movieEntity.setScene("anyScene");
        movieEntity = movieRepository.save(movieEntity);
        List<MovieEntity> fetchedMovie = movieCustomRepository.find(movieEntity.getId(), "anyScene", UUID.randomUUID());
        assertNotNull(fetchedMovie);
        assertTrue(fetchedMovie.size() > 0);
    }

}
