package com.example.mbblueprintbackend.service.movie;

import com.example.mbblueprintbackend.model.Movie;

import java.util.List;
import java.util.UUID;

public interface MovieService {

    List<Movie> getAllMovies();

    Movie getMovie(UUID movieId);

    void deleteMovie(UUID movieId) throws Exception;

    Movie createMovie(Movie movie);

    Movie updateMovie(UUID id, Movie movie);}
