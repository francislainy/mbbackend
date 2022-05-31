package com.example.mbbackend.service.movie;

import com.example.mbbackend.model.Movie;

import java.util.List;
import java.util.UUID;

public interface MovieService {

    List<Movie> getAllMovies();

    Movie getMovie(UUID movieId);

    List<Movie> getMoviesForActor(UUID actorId);

    List<Movie> getMoviesWithCustomFilter(UUID movieId, String scene, UUID actorId, UUID locationId, UUID roomId);

    void deleteMovie(UUID movieId) throws Exception;

    Movie createMovie(Movie movie);

    Movie updateMovie(UUID movieId, Movie movie);

}
