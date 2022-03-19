package com.example.mbblueprintbackend.repository;

import com.example.mbblueprintbackend.model.Actor;
import com.example.mbblueprintbackend.model.Movie;
import com.example.mbblueprintbackend.model.Room;
import com.example.mbblueprintbackend.model.SetLocation;
import com.example.mbblueprintbackend.util.Util;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.UUID;

import static com.example.mbblueprintbackend.util.Util.jsonStringFromObject;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class MovieRepositoryTest {

    MovieRepository movieRepository = new MovieRepository();

    @Test
    void testGetAllMovies() {

        assertTrue(movieRepository.getAllMovies().size() > 0);
        HashMap<String, Object> map = ((HashMap<String, Object>) movieRepository.getAllMovies());
        String json = jsonStringFromObject(map);
        String jsonExpected = jsonStringFromObject(Util.getAllMovies());
        assertEquals(jsonExpected, json);
    }

    @Test
    void testGetSingleMovie() {

        UUID movieId = UUID.fromString("1bfff94a-b70e-4b39-bd2a-be1c0f898589");

        Movie movie = movieRepository.getSingleMovie(movieId);
        String json = jsonStringFromObject(movie);
        String jsonExpected = jsonStringFromObject(Util.getSingleMovie(movieId));
        assertEquals(jsonExpected, json);
    }


    @Test
    void testCreateMovie() {

        UUID movieId = UUID.fromString("1bfff94a-b70e-4b39-bd2a-be1c0f898589");

        Actor actor = Actor.builder().name("Shakira").build();
        Room room = Room.builder().title("Bedroom").build();
        SetLocation setLocation = SetLocation.builder().title("Childhood home").build();

        Movie movie = Movie.builder()
                .actor(actor)
                .character("西")
                .imageUrl("anyUrl")
                .meaning("anyMeaning")
                .pinyin("xī")
                .room(room)
                .setLocation(setLocation)
                .scene("Shakira talking to Kanye West outside the front entrance")
                .build();

        movie = movieRepository.createMovie(movie);
        String json = jsonStringFromObject(movie);
        movie.setId(movieId);
        String jsonExpected = jsonStringFromObject(movie);
        assertEquals(jsonExpected, json);
    }
}
