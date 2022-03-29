package com.example.mbblueprintbackend.controller.movie;

import com.example.mbblueprintbackend.model.Character;
import com.example.mbblueprintbackend.model.*;
import com.example.mbblueprintbackend.service.movie.MovieService;
import com.example.mbblueprintbackend.util.Utils;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Slf4j
@WebMvcTest(controllers = MovieController.class)
@ExtendWith(MockitoExtension.class)
class MovieControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    MovieService movieService;

    @Test
    void testGetAllMovies() throws Exception {

        UUID actorId = UUID.fromString("1bfff94a-b70e-4b39-bd2a-be1c0f898589");
        UUID characterId = UUID.fromString("2bfff94a-b70e-4b39-bd2a-be1c0f898589");
        UUID locationId = UUID.fromString("3cfff94a-b70e-4b39-bd2a-be1c0f898589");
        UUID roomId = UUID.fromString("4dfff94a-b70e-4b39-bd2a-be1c0f898589");

        Actor actor = Actor.builder()
                .id(actorId)
                .build();

        Character character = Character.builder()
                .id(characterId)
                .build();

        Location location = Location.builder()
                .id(locationId)
                .build();

        Room room = Room.builder()
                .id(roomId)
                .build();

        Movie movie = Movie.builder()
                .id(roomId)
                .scene("anyScene")
                .imageUrl("anyUrl")
                .actor(actor)
                .character(character)
                .location(location)
                .room(room)
                .build();

        List<Movie> movieList = new ArrayList<>();
        movieList.add(movie);

        HashMap<String, List<Movie>> map = new HashMap<>();
        map.put("movies", movieList);

        String json = Utils.jsonStringFromObject(map);
        when(movieService.getAllMovies()).thenReturn(movieList);

        mockMvc.perform(get("/api/mb/movie"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(content().json(json));
    }

    @Test
    void testGetMovie() throws Exception {

        UUID actorId = UUID.fromString("1bfff94a-b70e-4b39-bd2a-be1c0f898589");
        UUID characterId = UUID.fromString("2bfff94a-b70e-4b39-bd2a-be1c0f898589");
        UUID locationId = UUID.fromString("3cfff94a-b70e-4b39-bd2a-be1c0f898589");
        UUID roomId = UUID.fromString("4dfff94a-b70e-4b39-bd2a-be1c0f898589");

        Actor actor = Actor.builder()
                .id(actorId)
                .build();

        Character character = Character.builder()
                .id(characterId)
                .build();

        Location location = Location.builder()
                .id(locationId)
                .build();

        Room room = Room.builder()
                .id(roomId)
                .build();

        Movie movie = Movie.builder()
                .id(roomId)
                .scene("anyScene")
                .imageUrl("anyUrl")
                .actor(actor)
                .character(character)
                .location(location)
                .room(room)
                .build();

        String json = Utils.jsonStringFromObject(movie);

        when(movieService.getMovie(any())).thenReturn(movie);

        mockMvc.perform(get("/api/mb/movie/{movieId}", roomId))
                .andExpect(status().is2xxSuccessful())
                .andExpect(content().json(json));
    }

    @Test
    void testPostMovie() throws Exception {

        UUID actorId = UUID.fromString("1bfff94a-b70e-4b39-bd2a-be1c0f898589");
        UUID characterId = UUID.fromString("2bfff94a-b70e-4b39-bd2a-be1c0f898589");
        UUID locationId = UUID.fromString("3cfff94a-b70e-4b39-bd2a-be1c0f898589");
        UUID roomId = UUID.fromString("4dfff94a-b70e-4b39-bd2a-be1c0f898589");

        Actor actor = Actor.builder()
                .id(actorId)
                .build();

        Character character = Character.builder()
                .id(characterId)
                .build();

        Location location = Location.builder()
                .id(locationId)
                .build();

        Room room = Room.builder()
                .id(roomId)
                .build();

        Movie movie = Movie.builder()
                .id(roomId)
                .scene("anyScene")
                .imageUrl("anyUrl")
                .actor(actor)
                .character(character)
                .location(location)
                .room(room)
                .build();

        String json = Utils.jsonStringFromObject(movie);

        ObjectMapper objectMapper = new ObjectMapper();
        Movie movie1 = objectMapper.readValue(json, Movie.class);

        UUID movieId = UUID.fromString("1bfff94a-b70e-4b39-bd2a-be1c0f898589");
        movie1.setId(movieId);
        String json1 = Utils.jsonStringFromObject(movie1);

        when(movieService.createMovie(movie)).thenReturn(movie1);

        mockMvc.perform(post("/api/mb/movie")
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful())
                .andExpect(content().json(json1));
    }

    @Test
    void testDeleteMovie() throws Exception {

        UUID movieId = UUID.fromString("1bfff94a-b70e-4b39-bd2a-be1c0f898589");

        Movie movie = Movie.builder()
                .id(movieId)
                .build();

        when(movieService.getMovie(movieId)).thenReturn(movie);

        mockMvc.perform(delete("/api/mb/movie/{movieId}", movieId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful());
    }

    @Test
    void testUpdateCharacter() throws Exception {

        UUID actorId = UUID.fromString("1bfff94a-b70e-4b39-bd2a-be1c0f898589");
        UUID characterId = UUID.fromString("2bfff94a-b70e-4b39-bd2a-be1c0f898589");
        UUID locationId = UUID.fromString("3cfff94a-b70e-4b39-bd2a-be1c0f898589");
        UUID roomId = UUID.fromString("4dfff94a-b70e-4b39-bd2a-be1c0f898589");

        Actor actor = Actor.builder()
                .id(actorId)
                .build();

        Character character = Character.builder()
                .id(characterId)
                .build();

        Location location = Location.builder()
                .id(locationId)
                .build();

        Room room = Room.builder()
                .id(roomId)
                .build();

        Movie movie = Movie.builder()
                .id(roomId)
                .scene("anyScene")
                .imageUrl("anyUrl")
                .actor(actor)
                .character(character)
                .location(location)
                .room(room)
                .build();

        String json = Utils.jsonStringFromObject(movie);

        ObjectMapper objectMapper = new ObjectMapper();
        Movie movie1 = objectMapper.readValue(json, Movie.class);

        UUID movieId = UUID.fromString("1bfff94a-b70e-4b39-bd2a-be1c0f898589");
        movie1.setId(movieId);
        String json1 = Utils.jsonStringFromObject(movie1);

        when(movieService.updateMovie(characterId, movie)).thenReturn(movie1);

        mockMvc.perform(put("/api/mb/movie/{movieId}", characterId)
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful())
                .andExpect(content().json(json1));
    }

}
