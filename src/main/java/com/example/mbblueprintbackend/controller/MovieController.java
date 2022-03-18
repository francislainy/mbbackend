package com.example.mbblueprintbackend.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.mbblueprintbackend.model.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/mb")
public class MovieController {

    @GetMapping("/movies")
    public ResponseEntity<List<Movie>> getAllMovies() {
        List<Movie> movieList = new ArrayList<>();

        return new ResponseEntity<>(movieList, HttpStatus.OK);
    }

}
