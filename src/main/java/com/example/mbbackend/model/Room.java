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
public class Room {

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private UUID id;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String title;
}