package com.example.mbblueprintbackend.repository;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

class MovieRepositoryTest {

    MovieRepository movieRepository = new MovieRepository();

    @Test
    void testGetAllMovies() {
        assertTrue(movieRepository.getAllMovies().size() > 0);
    }
}
