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
public class Character {

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private UUID id;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String hanzi;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String pinyin;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String meaning;
}
