package com.example.mbblueprintbackend.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.example.mbblueprintbackend.model.*;
import org.springframework.stereotype.Service;

@Service
public class MovieService {

    public Map<String, Object> getAllMovies() {

        List<Object> movieList = new ArrayList<>();
        Movie movie = new Movie("xi");
        movieList.add(movie);
        Map<String, Object> map = new HashMap<>();
        map.put("results", movieList);

        return map;
    }
}
