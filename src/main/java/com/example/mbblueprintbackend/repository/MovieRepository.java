package com.example.mbblueprintbackend.repository;

import com.example.mbblueprintbackend.model.Movie;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class MovieRepository {

    public Map<String, Object> getAllMovies() {

        List<Object> movieList = new ArrayList<>();
        Movie movie = new Movie("xi");
        movieList.add(movie);
        Map<String, Object> map = new HashMap<>();
        map.put("results", movieList);

        return map;
    }
}
