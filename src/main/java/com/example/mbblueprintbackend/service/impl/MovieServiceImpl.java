package com.example.mbblueprintbackend.service.impl;

import com.example.mbblueprintbackend.model.Movie;
import com.example.mbblueprintbackend.service.MovieService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class MovieServiceImpl implements MovieService {

    @Override
    public Map<String, Object> getAllMovies() {

        List<Object> movieList = new ArrayList<>();
        Movie movie = new Movie("xi");
        movieList.add(movie);
        Map<String, Object> map = new HashMap<>();
        map.put("results", movieList);

        return map;
    }
}
