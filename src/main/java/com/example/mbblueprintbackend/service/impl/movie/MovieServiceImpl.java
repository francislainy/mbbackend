package com.example.mbblueprintbackend.service.impl.movie;

import com.example.mbblueprintbackend.entity.actor.ActorEntity;
import com.example.mbblueprintbackend.entity.character.CharacterEntity;
import com.example.mbblueprintbackend.entity.location.LocationEntity;
import com.example.mbblueprintbackend.entity.movie.MovieEntity;
import com.example.mbblueprintbackend.entity.room.RoomEntity;
import com.example.mbblueprintbackend.model.Character;
import com.example.mbblueprintbackend.model.*;
import com.example.mbblueprintbackend.repository.movie.MovieRepository;
import com.example.mbblueprintbackend.service.movie.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class MovieServiceImpl implements MovieService {

    @Autowired
    MovieRepository movieRepository;

    @Override
    public List<Movie> getAllMovies() {
        List<Movie> movieList = new ArrayList<>();

        movieRepository.findAll().forEach(movieEntity -> {

            Character character = Character.builder()
                    .id(movieEntity.getCharacter().getId())
                    .build();

            Actor actor = Actor.builder()
                    .id(movieEntity.getCharacter().getId())
                    .build();

            Location location = Location.builder()
                    .id(movieEntity.getLocation().getId())
                    .build();

            Room room = Room.builder()
                    .id(movieEntity.getRoom().getId())
                    .build();

            movieList.add(
                    Movie.builder()
                            .id(movieEntity.getId())
                            .scene(movieEntity.getScene())
                            .imageUrl(movieEntity.getImageUrl())
                            .actor(actor)
                            .character(character)
                            .location(location)
                            .room(room)
                            .build());

        });

        return movieList;
    }

    @Override
    public Movie getMovie(UUID movieId) {

        Optional<MovieEntity> movieEntityOptional = movieRepository.findById(movieId);

        if (movieEntityOptional.isPresent()) {

            MovieEntity movieEntity = movieEntityOptional.get();

            Character character = Character.builder()
                    .id(movieEntity.getCharacter().getId())
                    .build();

            Actor actor = Actor.builder()
                    .id(movieEntity.getCharacter().getId())
                    .build();

            Location location = Location.builder()
                    .id(movieEntity.getLocation().getId())
                    .build();

            Room room = Room.builder()
                    .id(movieEntity.getRoom().getId())
                    .build();

            return Movie.builder()
                    .id(movieEntity.getId())
                    .scene(movieEntity.getScene())
                    .imageUrl(movieEntity.getImageUrl())
                    .actor(actor)
                    .character(character)
                    .location(location)
                    .room(room)
                    .build();
        } else {
            return null;
        }
    }

    @Override
    public void deleteMovie(UUID movieId) throws Exception {

        Optional<MovieEntity> optionalMovieEntity = movieRepository.findById(movieId);

        if (optionalMovieEntity.isPresent()) {

            MovieEntity movieEntity = optionalMovieEntity.get();

            movieRepository.delete(movieEntity);
        } else {
            throw new Exception();
        }

    }

    @Override
    public Movie createMovie(Movie movie) {

        CharacterEntity characterEntity = CharacterEntity.builder()
                .id(movie.getCharacter().getId())
                .build();

        ActorEntity actorEntity = ActorEntity.builder()
                .id(movie.getActor().getId())
                .build();

        LocationEntity locationEntity = LocationEntity.builder()
                .id(movie.getLocation().getId())
                .build();

        RoomEntity roomEntity = RoomEntity.builder()
                .id(movie.getRoom().getId())
                .build();

        MovieEntity movieEntity = MovieEntity.builder()
                .scene(movie.getScene())
                .imageUrl(movie.getImageUrl())
                .character(characterEntity)
                .actor(actorEntity)
                .location(locationEntity)
                .room(roomEntity)
                .build();

        movieEntity = movieRepository.save(movieEntity);

        return Movie.builder()
                .id(movieEntity.getId())
                .scene(movieEntity.getScene())
                .imageUrl(movieEntity.getImageUrl())
                .character(Character.builder().id(movieEntity.getCharacter().getId()).build())
                .actor(Actor.builder().id(movieEntity.getActor().getId()).build())
                .location(Location.builder().id(movieEntity.getLocation().getId()).build())
                .room(Room.builder().id(movieEntity.getRoom().getId()).build())
                .build();

    }

    @Override
    public Movie updateMovie(UUID uuid, Movie movie) {
        Optional<MovieEntity> characterEntityOptional = movieRepository.findById(uuid);

        if (characterEntityOptional.isPresent()) {

            CharacterEntity characterEntity = CharacterEntity.builder()
                    .id(movie.getCharacter().getId())
                    .build();

            ActorEntity actorEntity = ActorEntity.builder()
                    .id(movie.getActor().getId())
                    .build();

            LocationEntity locationEntity = LocationEntity.builder()
                    .id(movie.getLocation().getId())
                    .build();

            RoomEntity roomEntity = RoomEntity.builder()
                    .id(movie.getRoom().getId())
                    .build();

            MovieEntity movieEntity = MovieEntity.builder()
                    .scene(movie.getScene())
                    .imageUrl(movie.getImageUrl())
                    .character(characterEntity)
                    .actor(actorEntity)
                    .location(locationEntity)
                    .room(roomEntity)
                    .build();

            movieEntity = movieRepository.save(movieEntity);

            return Movie.builder()
                    .id(movieEntity.getId())
                    .scene(movieEntity.getScene())
                    .imageUrl(movieEntity.getImageUrl())
                    .character(Character.builder().id(movieEntity.getCharacter().getId()).build())
                    .actor(Actor.builder().id(movieEntity.getActor().getId()).build())
                    .location(Location.builder().id(movieEntity.getLocation().getId()).build())
                    .room(Room.builder().id(movieEntity.getRoom().getId()).build())
                    .build();
        } else {
            return null;
        }
    }

}
