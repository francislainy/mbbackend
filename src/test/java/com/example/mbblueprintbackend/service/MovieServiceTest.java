package com.example.mbblueprintbackend.service;

import com.example.mbblueprintbackend.repository.MovieRepository;
import com.example.mbblueprintbackend.service.impl.MovieServiceImpl;
import com.example.mbblueprintbackend.util.Util;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class MovieServiceTest {

    @InjectMocks
    MovieServiceImpl movieService;

    @Mock
    MovieRepository movieRepository;

    @Test
    void testGetAllMovies() {

        when(movieRepository.getAllMovies()).thenReturn(Util.getAllMovies());

        assertTrue(movieService.getAllMovies().size() > 0);
    }

}
