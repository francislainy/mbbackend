package com.example.mbbackend.service.impl.movie;

import com.example.mbbackend.entity.actor.ActorEntity;
import com.example.mbbackend.entity.character.CharacterEntity;
import com.example.mbbackend.entity.location.LocationEntity;
import com.example.mbbackend.entity.movie.MovieEntity;
import com.example.mbbackend.entity.room.RoomEntity;
import com.example.mbbackend.model.Character;
import com.example.mbbackend.model.*;
import com.example.mbbackend.repository.actor.ActorRepository;
import com.example.mbbackend.repository.character.CharacterRepository;
import com.example.mbbackend.repository.location.LocationRepository;
import com.example.mbbackend.repository.movie.MovieRepository;
import com.example.mbbackend.repository.room.RoomRepository;
import com.example.mbbackend.service.movie.MovieService;
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

    @Autowired
    CharacterRepository characterRepository;

    @Autowired
    ActorRepository actorRepository;

    @Autowired
    LocationRepository locationRepository;

    @Autowired
    RoomRepository roomRepository;

    @Override
    public List<Movie> getAllMovies() {
        List<Movie> movieList = new ArrayList<>();

        movieRepository.findAll().forEach(movieEntity -> {

            Character character = getCharacter(movieEntity);
            Actor actor = getActor(movieEntity);
            Location location = getLocation(movieEntity);
            Room room = getRoom(movieEntity);

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

            Character character = getCharacter(movieEntity);

            Actor actor = getActor(movieEntity);

            Location location = getLocation(movieEntity);

            Room room = getRoom(movieEntity);

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
                .hanzi(movie.getCharacter().getHanzi())
                .pinyin(movie.getCharacter().getPinyin())
                .meaning(movie.getCharacter().getMeaning())
                .build();

        characterEntity = characterRepository.save(characterEntity);

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
                .character(Character.builder()
                        .id(movieEntity.getCharacter().getId())
                        .hanzi(movieEntity.getCharacter().getHanzi())
                        .pinyin(movieEntity.getCharacter().getPinyin())
                        .meaning(movieEntity.getCharacter().getMeaning())
                        .build())
                .actor(Actor.builder()
                        .id(movieEntity.getActor().getId())
                        .name(getActor(movieEntity).getName())
                        .build())
                .location(Location.builder()
                        .id(movieEntity.getLocation().getId())
                        .title(getLocation(movieEntity).getTitle())
                        .associatedPinyinSound(getLocation(movieEntity).getAssociatedPinyinSound())
                        .build())
                .room(Room.builder()
                        .id(movieEntity.getRoom().getId())
                        .title(getRoom(movieEntity).getTitle())
                        .build())
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

    private Room getRoom(MovieEntity movieEntity) {

        if (movieEntity.getRoom() == null) {
            return null;
        }

        Room room = Room.builder()
                .id(movieEntity.getRoom().getId())
                .build();

        Optional<RoomEntity> roomEntityOptional = roomRepository.findById(room.getId());

        if (roomEntityOptional.isPresent()) {
            RoomEntity roomEntity = roomEntityOptional.get();
            room.setTitle(roomEntity.getTitle());
        }
        return room;
    }

    private Location getLocation(MovieEntity movieEntity) {

        if (movieEntity.getLocation() == null) {
            return null;
        }

        Location location = Location.builder()
                .id(movieEntity.getLocation().getId())
                .build();

        Optional<LocationEntity> locationEntityOptional = locationRepository.findById(location.getId());

        if (locationEntityOptional.isPresent()) {
            LocationEntity locationEntity = locationEntityOptional.get();
            location.setTitle(locationEntity.getTitle());
            location.setAssociatedPinyinSound(locationEntity.getAssociatedPinyinSound());
        }
        return location;
    }

    private Actor getActor(MovieEntity movieEntity) {

        if (movieEntity.getActor() == null) {
            return null;
        }

        Actor actor = Actor.builder()
                .id(movieEntity.getActor().getId())
                .build();

        Optional<ActorEntity> actorEntityOptional = actorRepository.findById(actor.getId());

        if (actorEntityOptional.isPresent()) {
            ActorEntity actorEntity = actorEntityOptional.get();
            actor.setName(actorEntity.getName());
        }
        return actor;
    }

    private Character getCharacter(MovieEntity movieEntity) {

        Character character = Character.builder()
                .id(movieEntity.getCharacter().getId())
                .build();

        Optional<CharacterEntity> characterEntityOptional = characterRepository.findById(character.getId());

        if (characterEntityOptional.isPresent()) {
            CharacterEntity characterEntity = characterEntityOptional.get();

            character.setHanzi(characterEntity.getHanzi());
            character.setPinyin(characterEntity.getPinyin());
            character.setMeaning(characterEntity.getMeaning());
        }
        return character;
    }
}
