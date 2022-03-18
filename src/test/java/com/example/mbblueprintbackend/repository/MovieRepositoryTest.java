package com.example.mbblueprintbackend.repository;

import com.example.mbblueprintbackend.util.Util;
import org.junit.jupiter.api.Test;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class MovieRepositoryTest {

    MovieRepository movieRepository = new MovieRepository();

    @Test
    void testGetAllMovies() {

        assertTrue(movieRepository.getAllMovies().size() > 0);
        HashMap<String, Object> map = ((HashMap<String, Object>) movieRepository.getAllMovies());
        String json = Util.jsonMap2String(map);
        String jsonExpected = Util.jsonMap2String(Util.getAllMovies());
        assertEquals(jsonExpected, json);
    }
}
