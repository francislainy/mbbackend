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
public class Character {

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private UUID id;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String character;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String pinyin;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String meaning;
}