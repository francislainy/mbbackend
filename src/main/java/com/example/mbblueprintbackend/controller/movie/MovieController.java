package com.example.mbblueprintbackend.controller.movie;

import com.example.mbblueprintbackend.model.Movie;
import com.example.mbblueprintbackend.service.movie.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/mb")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class MovieController {

    @Autowired
    MovieService movieService;

    @GetMapping("/movie")
    public ResponseEntity<Object> getAllMovies() {

        HashMap<String, List<Movie>> map = new HashMap<>();
        map.put("movies", movieService.getAllMovies());

        return new ResponseEntity<>(map, HttpStatus.OK);
    }

    @GetMapping("/movie/{movieId}")
    public ResponseEntity<Object> getMovie(@PathVariable UUID movieId) {

        return new ResponseEntity<>(movieService.getMovie(movieId), HttpStatus.OK);
    }

    @PostMapping("/movie")
    public ResponseEntity<Movie> createMovie(@RequestBody Movie movie) {

        return new ResponseEntity<>(movieService.createMovie(movie), HttpStatus.CREATED);
    }

    @PutMapping("/movie/{movieId}")
    public ResponseEntity<Object> updateMovie(@PathVariable UUID movieId, @RequestBody Movie movie) {

        return new ResponseEntity<>(movieService.updateMovie(movieId, movie), HttpStatus.OK);
    }

    @DeleteMapping("/movie/{movieId}")
    public ResponseEntity<Object> deleteMovie(@PathVariable UUID movieId) throws Exception {

        movieService.deleteMovie(movieId);
        return new ResponseEntity<>(HttpStatus.PARTIAL_CONTENT);
    }
}
