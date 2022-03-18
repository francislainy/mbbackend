package com.example.mbblueprintbackend.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.mbblueprintbackend.model.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/mb")
public class MovieController {

    @GetMapping("/movies")
    public ResponseEntity<Object> getAllMovies() {

        List<Object> movieList = new ArrayList<>();
        Movie movie = new Movie("xi");
        movieList.add(movie);
        Map<String, Object> map = new HashMap<>();
        map.put("results", movieList);

        return new ResponseEntity<>(map, HttpStatus.OK);
    }

}
