package com.example.mbblueprintbackend.service;

import com.example.mbblueprintbackend.model.Movie;
import com.example.mbblueprintbackend.repository.MovieRepository;
import com.example.mbblueprintbackend.service.impl.MovieServiceImpl;
import com.example.mbblueprintbackend.util.Util;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

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

    @Test
    void testGetSingleMovie() {

        UUID movieId = UUID.fromString("1bfff94a-b70e-4b39-bd2a-be1c0f898589");

        when(movieRepository.getSingleMovie(movieId)).thenReturn(Util.getSingleMovie(movieId));

        Movie movie = movieService.getSingleMovie(movieId);

        assertAll(
                () -> assertEquals(movieId.toString(), movie.getId().toString()),
                () -> assertEquals("xī", movie.getPinyin()),
                () -> assertEquals("西", movie.getCharacter()),
                () -> assertEquals("anyMeaning", movie.getMeaning()),
                () -> assertEquals("anyUrl", movie.getImageUrl()),
                () -> assertEquals("Childhood home", movie.getSetLocation().getTitle()),
                () -> assertEquals("Shakira", movie.getActor().getName()),
                () -> assertEquals("Bedroom", movie.getRoom().getTitle()),
                () -> assertEquals("Shakira talking to Kanye West outside the front entrance", movie.getScene()));
    }

}
