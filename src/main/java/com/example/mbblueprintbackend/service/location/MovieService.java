package com.example.mbblueprintbackend.service.location;

import com.example.mbblueprintbackend.model.Location;
import com.example.mbblueprintbackend.model.Movie;

import java.util.List;
import java.util.UUID;

public interface MovieService {

    List<Movie> getAllMovies();

    Movie getMovie(UUID uuid);

    void deleteMovie(UUID uuid) throws Exception;

    Movie createMovie(Location location);

    Movie updateMovie(UUID id, Location location);
}
