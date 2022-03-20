package com.example.mbblueprintbackend.controller;

import com.example.mbblueprintbackend.model.Movie;
import com.example.mbblueprintbackend.service.MovieService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/mb")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class MovieController {

    @Autowired
    MovieService movieService;

    @GetMapping("/movie")
    public ResponseEntity<Object> getAllMovies() {

        return new ResponseEntity<>(movieService.getAllMovies(), HttpStatus.OK);
    }

    @GetMapping("/movie/{movieId}")
    public ResponseEntity<Object> getSingleMovie(@PathVariable UUID movieId) {

        return new ResponseEntity<>(movieService.getSingleMovie(movieId), HttpStatus.OK);
    }

    @PostMapping("/movie")
    public ResponseEntity<Movie> createMovie(@RequestBody Movie movie) throws JsonProcessingException {

        return new ResponseEntity<>(movieService.createMovie(movie), HttpStatus.CREATED);
    }

}
