package com.example.mbblueprintbackend.controller;

import com.example.mbblueprintbackend.service.MovieService;
import com.example.mbblueprintbackend.util.Util;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.mockito.Mockito.when;

import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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
}
