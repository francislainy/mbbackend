package com.example.mbblueprintbackend.controller;

import com.example.mbblueprintbackend.service.MovieService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import static org.mockito.Mockito.when;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.mbblueprintbackend.model.*;

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

        String json = getAllMoviesJson();
        when(movieService.getAllMovies()).thenReturn(getAllMovies());

        mockMvc.perform(get("/api/mb/movie"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(content().json(json));
    }

    private String getAllMoviesJson() {
        Map<String, Object> map = getAllMovies();

        return jsonMap2String(map);
    }

    private Map<String, Object> getAllMovies() {
        List<Object> movieList = new ArrayList<>();
        Movie movie = new Movie("xi");
        movieList.add(movie);
        Map<String, Object> map = new HashMap<>();
        map.put("results", movieList);
        return map;
    }

    public static String jsonMap2String(Map<String, Object> jsonObject) {
        if (jsonObject == null) {
            return "";
        }

        ObjectMapper mapper = new ObjectMapper();
        String jsonStr;
        try {
            jsonStr = mapper.writeValueAsString(jsonObject);
        } catch (JsonProcessingException e) {
            log.error(e.toString());
            throw new RuntimeException(e.getMessage()); // NOSONAR
        }
        return jsonStr;
    }

}
