package com.example.mbblueprintbackend.service;

import com.example.mbblueprintbackend.model.Movie;
import com.example.mbblueprintbackend.repository.MovieRepository;
import com.example.mbblueprintbackend.service.impl.MovieServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ExtendWith(MockitoExtension.class)
class MovieServiceTest {

    @InjectMocks
    MovieServiceImpl movieService;

    @Mock
    MovieRepository movieRepository;

    @Test
    void testGetAllMovies() {

        when(movieRepository.getAllMovies()).thenReturn(getAllMovies());

        assertTrue(movieService.getAllMovies().size() > 0);
    }

    private Map<String, Object> getAllMovies() {
        List<Object> movieList = new ArrayList<>();
        Movie movie = new Movie("xi");
        movieList.add(movie);
        Map<String, Object> map = new HashMap<>();
        map.put("results", movieList);
        return map;
    }
}
