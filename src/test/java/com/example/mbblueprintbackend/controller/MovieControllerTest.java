package com.example.mbblueprintbackend.controller;

import com.example.mbblueprintbackend.model.Actor;
import com.example.mbblueprintbackend.model.Movie;
import com.example.mbblueprintbackend.model.Room;
import com.example.mbblueprintbackend.model.SetLocation;
import com.example.mbblueprintbackend.service.MovieService;
import com.example.mbblueprintbackend.util.Util;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.mockito.Mockito.when;

import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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

        String json = Util.jsonStringFromObject(Util.getAllMovies());
        when(movieService.getAllMovies()).thenReturn(Util.getAllMovies());

        mockMvc.perform(get("/api/mb/movie"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(content().json(json));
    }

    @Test
    void testGetSingleMovie() throws Exception {

        UUID movieId = UUID.fromString("1bfff94a-b70e-4b39-bd2a-be1c0f898589");

        String json = Util.jsonStringFromObject(Util.getSingleMovie(movieId));
        when(movieService.getSingleMovie(movieId)).thenReturn(Util.getSingleMovie(movieId));

        mockMvc.perform(get("/api/mb/movie/{movieId}", movieId))
                .andExpect(status().is2xxSuccessful())
                .andExpect(content().json(json));
    }


    @Test
    void testPostMovie() throws Exception {

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

        String json = Util.jsonStringFromObject(movie);

        ObjectMapper objectMapper = new ObjectMapper();
        Movie movie1 = objectMapper.readValue(json, Movie.class);

        UUID movieId = UUID.fromString("1bfff94a-b70e-4b39-bd2a-be1c0f898589");
        movie1.setId(movieId);
        String json1 = Util.jsonStringFromObject(movie1);

        when(movieService.createMovie(movie)).thenReturn(movie1);

        mockMvc.perform(post("/api/mb/movie")
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful())
                .andExpect(content().json(json1));
    }
}
