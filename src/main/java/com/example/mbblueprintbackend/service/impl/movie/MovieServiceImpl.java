package com.example.mbblueprintbackend.service.impl.movie;

import com.example.mbblueprintbackend.model.Movie;
import com.example.mbblueprintbackend.repository.movie.MovieRepository;
import com.example.mbblueprintbackend.service.movie.MovieService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.UUID;

@Service
public class MovieServiceImpl implements MovieService {

    @Autowired
    MovieRepository movieRepository;

    @Override
    public Map<String, Object> getAllMovies() {

        return movieRepository.getAllMovies();
    }

    @Override
    public Movie getSingleMovie(UUID uuid) {

        return movieRepository.getSingleMovie(uuid);
    }

    @Override
    public Movie createMovie(Movie movie) throws JsonProcessingException {
        return movieRepository.createMovie(movie);
    }

}
