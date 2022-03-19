package com.example.mbblueprintbackend.service;

import com.example.mbblueprintbackend.model.Movie;

import java.util.Map;
import java.util.UUID;

public interface MovieService {

    Map<String, Object> getAllMovies();

    Movie getSingleMovie(UUID uuid);
}
