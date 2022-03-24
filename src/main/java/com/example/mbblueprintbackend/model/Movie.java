package com.example.mbblueprintbackend.model;

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
    private String pinyin;
    private String character;
    private String meaning;
    private String scene;
    private String imageUrl;

    private Location location;
    private Actor actor;
    private Room room;
}
