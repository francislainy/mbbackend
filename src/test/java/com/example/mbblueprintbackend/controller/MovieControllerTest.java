package com.example.mbblueprintbackend.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.mbblueprintbackend.model.*;

@WebMvcTest(controllers = MovieController.class)
@ExtendWith(SpringExtension.class)
class MovieControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Test
    void getAllMovies() throws Exception {

        List<Object> movieList = new ArrayList<>();
        Movie movie = new Movie("xi");
        movieList.add(movie);
        Map<String, Object> map = new HashMap<>();
        map.put("results", movieList);

        String json = jsonMap2String(map);

        mockMvc.perform(get("/api/mb/movies"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(content().json(json));
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
//            log.error(e.toString());
            throw new RuntimeException(e.getMessage()); // NOSONAR
        }
        return jsonStr;
    }

}
