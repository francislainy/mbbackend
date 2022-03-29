package com.example.mbblueprintbackend.repository.movie;

import com.example.mbblueprintbackend.entity.movie.MovieEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface MovieRepository extends JpaRepository<MovieEntity, UUID> {

//    List<Object> movieList = new ArrayList<>();
//
//    public Map<String, Object> getAllMovies() {
//
//        UUID movieId = UUID.fromString("1bfff94a-b70e-4b39-bd2a-be1c0f898589");
//
//        Actor actor = Actor.builder().name("Shakira").build();
//        Room room = Room.builder().title("Bedroom").build();
//        Location location = Location.builder().title("Childhood home").build();
//
//        Movie movie = Movie.builder()
//                .id(movieId)
//                .actor(actor)
//                .character(new Character())
//                .imageUrl("anyUrl")
//                .room(room)
//                .location(location)
//                .scene("Shakira talking to Kanye West outside the front entrance")
//                .build();
//        movieList.add(movie);
//        Map<String, Object> map = new HashMap<>();
//        map.put("movies", movieList);
//
//        return map;
//    }
//
//    public Movie getSingleMovie(UUID movieId) {
//
//        Actor actor = Actor.builder()
//                .id(UUID.fromString("6b00f4e7-c499-4fd6-907d-ec0e8b9934b2"))
//                .build();
//        Room room = Room.builder()
//                .id(UUID.fromString("40325c6e-047f-452d-9a9a-f93111510764"))
//                .build();
//        Location location = Location.builder()
//                .id(UUID.fromString("c7b6f5b7-747b-490f-99d2-f6e3ebdbc060"))
//                .build();
//
//        return Movie.builder()
//                .id(movieId)
//                .actor(actor)
//                .character(new Character())
//                .imageUrl("anyUrl")
//                .room(room)
//                .location(location)
//                .scene("Shakira talking to Kanye West outside the front entrance")
//                .build();
//    }
//
//    public Movie createMovie(Movie movie) throws JsonProcessingException {
//
//        UUID movieId = UUID.fromString("1bfff94a-b70e-4b39-bd2a-be1c0f898589");
//        ObjectMapper objectMapper = new ObjectMapper();
//        String json = jsonStringFromObject(movie);
//        Movie movie1 = objectMapper.readValue(json, Movie.class);
//        movie1.setId(movieId);
//
//        movieList.add(movie1);
//
//        return movie1;
//    }
}
