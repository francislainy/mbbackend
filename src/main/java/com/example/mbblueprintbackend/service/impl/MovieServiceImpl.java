package com.example.mbblueprintbackend.service.impl;

import com.example.mbblueprintbackend.service.MovieService;
import com.example.mbblueprintbackend.repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class MovieServiceImpl implements MovieService {

    @Autowired
    MovieRepository movieRepository;

    @Override
    public Map<String, Object> getAllMovies() {

       return movieRepository.getAllMovies();
    }
}
