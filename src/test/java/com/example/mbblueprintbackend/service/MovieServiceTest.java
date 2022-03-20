package com.example.mbblueprintbackend.service;

import com.example.mbblueprintbackend.model.Actor;
import com.example.mbblueprintbackend.model.Movie;
import com.example.mbblueprintbackend.model.Room;
import com.example.mbblueprintbackend.model.SetLocation;
import com.example.mbblueprintbackend.repository.MovieRepository;
import com.example.mbblueprintbackend.service.impl.MovieServiceImpl;
import com.example.mbblueprintbackend.util.Util;
import com.fasterxml.jackson.core.JsonProcessingException;
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

    @Test
    void testCreateMovie() throws JsonProcessingException {

        UUID movieId = UUID.fromString("1bfff94a-b70e-4b39-bd2a-be1c0f898589");

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

        when(movieRepository.createMovie(movie)).thenReturn(Util.getSingleMovie(movieId));

        Movie movie1 = movieService.createMovie(movie);

        assertAll(
                () -> assertEquals(movieId.toString(), movie1.getId().toString()),
                () -> assertEquals("xī", movie1.getPinyin()),
                () -> assertEquals("西", movie1.getCharacter()),
                () -> assertEquals("anyMeaning", movie1.getMeaning()),
                () -> assertEquals("anyUrl", movie1.getImageUrl()),
                () -> assertEquals("Childhood home", movie1.getSetLocation().getTitle()),
                () -> assertEquals("Shakira", movie1.getActor().getName()),
                () -> assertEquals("Bedroom", movie1.getRoom().getTitle()),
                () -> assertEquals("Shakira talking to Kanye West outside the front entrance", movie1.getScene()));
    }

}
