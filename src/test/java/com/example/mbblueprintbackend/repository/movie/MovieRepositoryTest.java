//package com.example.mbblueprintbackend.repository.movie;
//
//import com.example.mbblueprintbackend.model.Actor;
//import com.example.mbblueprintbackend.model.Character;
//import com.example.mbblueprintbackend.model.Location;
//import com.example.mbblueprintbackend.model.Movie;
//import com.example.mbblueprintbackend.model.Room;
//import com.example.mbblueprintbackend.util.Utils;
//import com.fasterxml.jackson.core.JsonProcessingException;
//import com.fasterxml.jackson.core.type.TypeReference;
//import com.fasterxml.jackson.databind.DeserializationFeature;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import org.junit.jupiter.api.Test;
//
//import java.util.HashMap;
//import java.util.List;
//import java.util.UUID;
//
//import static com.example.mbblueprintbackend.util.Utils.jsonStringFromObject;
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertTrue;
//
//class MovieRepositoryTest {
//
//    MovieRepository movieRepository = new MovieRepository();
//
//    @Test
//    void testGetAllMovies() throws JsonProcessingException {
//
//        UUID movieId = UUID.fromString("1bfff94a-b70e-4b39-bd2a-be1c0f898589");
//
//        ObjectMapper objectMapper = new ObjectMapper();
//        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
//
//        assertTrue(movieRepository.getAllMovies().size() > 0);
//        HashMap<String, Object> map = ((HashMap<String, Object>) movieRepository.getAllMovies());
//
//        String json = jsonStringFromObject(map);
//        String jsonExpected = jsonStringFromObject(Utils.getAllMovies());
//
//        List<Movie> movieList = (List<Movie>) objectMapper.readValue(json, HashMap.class).get("movies");
//        List<Movie> movieExpectedList = (List<Movie>) objectMapper.readValue(jsonExpected, HashMap.class).get("movies");
//
//        List<Movie> myList = objectMapper.readValue(jsonStringFromObject(movieList), new TypeReference<>() {
//        });
//        List<Movie> myListExpected = objectMapper.readValue(jsonStringFromObject(movieExpectedList), new TypeReference<>() {
//        });
//
//        Movie movie = myList.get(0);
//        Movie movieExpected = myListExpected.get(0);
//        movieExpected.setId(movieId);
//
//        assertEquals(movie, movieExpected);
//        assertEquals(movie.getId(), movieExpected.getId());
//    }
//
//    @Test
//    void testGetSingleMovie() {
//
//        UUID movieId = UUID.fromString("1bfff94a-b70e-4b39-bd2a-be1c0f898589");
//
//        Movie movie = movieRepository.getSingleMovie(movieId);
//        String json = jsonStringFromObject(movie);
//        String jsonExpected = jsonStringFromObject(Utils.getSingleMovie(movieId));
//        assertEquals(jsonExpected, json);
//    }
//
//    @Test
//    void testCreateMovie() throws JsonProcessingException {
//
//        UUID movieId = UUID.fromString("1bfff94a-b70e-4b39-bd2a-be1c0f898589");
//
//        Actor actor = Actor.builder().name("Shakira").build();
//        Room room = Room.builder().title("Bedroom").build();
//        Location location = Location.builder().title("Childhood home").build();
//
//        Movie movie = Movie.builder()
//                .actor(actor)
//                .character(new Character())
//                .imageUrl("anyUrl")
//                .room(room)
//                .location(location)
//                .scene("Shakira talking to Kanye West outside the front entrance")
//                .build();
//
//        movie = movieRepository.createMovie(movie);
//        String json = jsonStringFromObject(movie);
//        movie.setId(movieId);
//        String jsonExpected = jsonStringFromObject(movie);
//        assertEquals(jsonExpected, json);
//    }
//}
