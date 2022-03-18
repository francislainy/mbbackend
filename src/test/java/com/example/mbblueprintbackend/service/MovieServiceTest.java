package com.example.mbblueprintbackend.service;

import com.example.mbblueprintbackend.service.impl.MovieServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
//@SpringBootTest
class MovieServiceTest {

    //    @Autowired
    MovieService movieService = new MovieServiceImpl();

    @Test
    void getAllMovies() {

        assertTrue(movieService.getAllMovies().size() > 0);
    }
}
