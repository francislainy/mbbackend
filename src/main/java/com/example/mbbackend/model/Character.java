package com.example.mbbackend.model;

import com.example.mbbackend.config.CharacterTone;
import com.example.mbbackend.entity.character.CharacterEntity;
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
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Boolean prop;

    public Boolean isProp() {
        return prop;
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonIncludeProperties(value= {"id", "actor"})
    private Movie movie;

    public static Character convertCharacter(CharacterEntity characterEntity) {
        return Character.builder()
                .id(characterEntity.getId())
                .hanzi(characterEntity.getHanzi())
                .pinyin(characterEntity.getPinyin())
                .meaning(characterEntity.getMeaning())
                .tone(characterEntity.getTone())
                .prop(characterEntity.isProp())
//                .movie(Movie.convertMovie(characterEntity.getMovie()))
                .build();
    }

}
