package com.example.mbbackend.model;

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
    private Location location;
    private Actor actor;
    private Room room;
}
