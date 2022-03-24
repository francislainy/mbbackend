package com.example.mbblueprintbackend.service.movie;

import com.example.mbblueprintbackend.model.Movie;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.util.Map;
import java.util.UUID;

public interface MovieService {

    Map<String, Object> getAllMovies();

    Movie getSingleMovie(UUID uuid);

    Movie createMovie(Movie movie) throws JsonProcessingException;
}
