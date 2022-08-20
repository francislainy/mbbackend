package com.example.mbbackend.model;

import com.example.mbbackend.config.ActorFamily;
import com.example.mbbackend.entity.actor.ActorEntity;
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
public class Actor {

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private UUID id;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String name;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String associatedPinyinSound;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private ActorFamily family;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String imageUrl;

    public static Actor convertActor(ActorEntity actorEntity) {
        
        if (actorEntity != null) {

            return Actor.builder()
                    .id(actorEntity.getId())
                    .name(actorEntity.getName())
                    .associatedPinyinSound(actorEntity.getAssociatedPinyinSound())
                    .family(actorEntity.getFamily())
                    .imageUrl(actorEntity.getImageUrl())
                    .build();
        }
        
        else {
            return null;
        }
    }
}
