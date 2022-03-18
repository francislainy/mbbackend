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

        String json = Util.jsonMap2String(Util.getAllMovies());
        when(movieService.getAllMovies()).thenReturn(Util.getAllMovies());

        mockMvc.perform(get("/api/mb/movie"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(content().json(json));
    }
}
