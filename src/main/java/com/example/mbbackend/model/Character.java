package com.example.mbbackend.model;

import com.example.mbbackend.config.CharacterTone;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonIncludeProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Character {

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private UUID id;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String hanzi;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String pinyin;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String meaning;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private CharacterTone tone;

    @JsonIncludeProperties(value = "id")
    private Movie movie;
}
