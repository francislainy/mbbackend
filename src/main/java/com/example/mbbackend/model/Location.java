package com.example.mbbackend.model;

import com.example.mbbackend.entity.location.LocationEntity;
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
public class Location {

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private UUID id;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String title;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String associatedPinyinSound;

    public static Location convertLocation(LocationEntity locationEntity) {
        return Location.builder()
                .id(locationEntity.getId())
                .title(locationEntity.getTitle())
                .associatedPinyinSound(locationEntity.getAssociatedPinyinSound())
                .build();
    }
}
