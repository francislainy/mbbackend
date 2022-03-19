package com.example.mbblueprintbackend.model;

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

    private UUID id;
    private String pinyin;
    private String character;
    private String meaning;
    private String scene;
    private String imageUrl;

    private SetLocation setLocation;
    private Actor actor;
    private Room room;
}
