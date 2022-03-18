package com.example.mbblueprintbackend.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = MovieController.class)
@ExtendWith(SpringExtension.class)
class MovieControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Test
    void getAllMovies() throws Exception {

        mockMvc.perform(get("/api/mb/movies"))
                .andExpect(status().is2xxSuccessful());
    }

}
