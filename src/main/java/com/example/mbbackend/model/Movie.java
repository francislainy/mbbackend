package com.example.mbbackend.model;

import com.example.mbbackend.entity.movie.MovieEntity;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Movie {

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private UUID id;
    private String scene;
    private String imageUrl;

    private Character character;
    private Actor actor;
    private Location location;
    private Room room;

    public static Movie convertMovie(MovieEntity movieEntity) {
        return Movie.builder()
                .id(movieEntity.getId())
                .scene(movieEntity.getScene())
                .imageUrl(movieEntity.getImageUrl())
                .character(Character.convertCharacter(movieEntity.getCharacter()))
                .actor(Actor.convertActor(movieEntity.getActor()))
                .location(Location.convertLocation(movieEntity.getLocation()))
                .room(Room.convertRoom(movieEntity.getRoom()))
                .build();
    }
}
