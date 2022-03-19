package com.example.mbblueprintbackend.repository;

import com.example.mbblueprintbackend.model.Movie;
import com.example.mbblueprintbackend.util.Util;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class MovieRepositoryTest {

    MovieRepository movieRepository = new MovieRepository();

    @Test
    void testGetAllMovies() {

        assertTrue(movieRepository.getAllMovies().size() > 0);
        HashMap<String, Object> map = ((HashMap<String, Object>) movieRepository.getAllMovies());
        String json = Util.jsonStringFromObject(map);
        String jsonExpected = Util.jsonStringFromObject(Util.getAllMovies());
        assertEquals(jsonExpected, json);
    }

    @Test
    void testGetSingleMovie() {

        UUID movieId = UUID.fromString("1bfff94a-b70e-4b39-bd2a-be1c0f898589");

        Movie movie = movieRepository.getSingleMovie(movieId);
        String json = Util.jsonStringFromObject(movie);
        String jsonExpected = Util.jsonStringFromObject(Util.getSingleMovie(movieId));
        assertEquals(jsonExpected, json);
    }
}
