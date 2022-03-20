package com.example.mbblueprintbackend.repository;

import com.example.mbblueprintbackend.model.Actor;
import com.example.mbblueprintbackend.model.Movie;
import com.example.mbblueprintbackend.model.Room;
import com.example.mbblueprintbackend.model.SetLocation;
import com.example.mbblueprintbackend.util.Util;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Repository;

import java.util.*;

import static com.example.mbblueprintbackend.util.Util.jsonStringFromObject;

@Repository
public class MovieRepository {

    List<Object> movieList = new ArrayList<>();

    public Map<String, Object> getAllMovies() {

        UUID movieId = UUID.fromString("1bfff94a-b70e-4b39-bd2a-be1c0f898589");

        Actor actor = Actor.builder().name("Shakira").build();
        Room room = Room.builder().title("Bedroom").build();
        SetLocation setLocation = SetLocation.builder().title("Childhood home").build();

        Movie movie = Movie.builder()
                .id(movieId)
                .actor(actor)
                .character("西")
                .imageUrl("anyUrl")
                .meaning("anyMeaning")
                .pinyin("xī")
                .room(room)
                .setLocation(setLocation)
                .scene("Shakira talking to Kanye West outside the front entrance")
                .build();
        movieList.add(movie);
        Map<String, Object> map = new HashMap<>();
        map.put("movies", movieList);

        return map;
    }

    public Movie getSingleMovie(UUID movieId) {

        Actor actor = Actor.builder().name("Shakira").build();
        Room room = Room.builder().title("Bedroom").build();
        SetLocation setLocation = SetLocation.builder().title("Childhood home").build();

        return Movie.builder()
                .id(movieId)
                .actor(actor)
                .character("西")
                .imageUrl("anyUrl")
                .meaning("anyMeaning")
                .pinyin("xī")
                .room(room)
                .setLocation(setLocation)
                .scene("Shakira talking to Kanye West outside the front entrance")
                .build();
    }

    public Movie createMovie(Movie movie) throws JsonProcessingException {

        UUID movieId = UUID.fromString("1bfff94a-b70e-4b39-bd2a-be1c0f898589");
        ObjectMapper objectMapper = new ObjectMapper();
        String json = jsonStringFromObject(movie);
        Movie movie1 = objectMapper.readValue(json, Movie.class);
        movie1.setId(movieId);

        movieList.add(movie1);

        return movie1;
    }
}
