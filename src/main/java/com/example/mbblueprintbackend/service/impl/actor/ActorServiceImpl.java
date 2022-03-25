package com.example.mbblueprintbackend.service.impl.actor;

import com.example.mbblueprintbackend.entity.actor.ActorEntity;
import com.example.mbblueprintbackend.model.Actor;
import com.example.mbblueprintbackend.repository.actor.ActorRepository;
import com.example.mbblueprintbackend.service.location.ActorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ActorServiceImpl implements ActorService {

    @Autowired
    ActorRepository actorRepository;

    @Override
    public List<Actor> getAllActors() {

        List<Actor> actorList = new ArrayList<>();

        actorRepository.findAll().forEach(actorEntity -> actorList.add(
                Actor.builder()
                        .id(actorEntity.getId())
                        .name(actorEntity.getName())
                        .associatedPinyinSound(actorEntity.getAssociatedPinyinSound())
                        .imageUrl(actorEntity.getImageUrl())
                        .family(actorEntity.getFamily())
                        .build()));

        return actorList;
    }

    @Override
    public Actor getActor(UUID actorId) {

        Optional<ActorEntity> actorEntityOptional = actorRepository.findById(actorId);

        if (actorEntityOptional.isPresent()) {

            ActorEntity actorEntity = actorEntityOptional.get();

            return Actor.builder()
                    .id(actorEntity.getId())
                    .name(actorEntity.getName())
                    .associatedPinyinSound(actorEntity.getAssociatedPinyinSound())
                    .family(actorEntity.getFamily())
                    .imageUrl(actorEntity.getImageUrl())
                    .build();
        } else {
            return null;
        }
    }

    @Override
    public Actor createActor(Actor actor) {

        ActorEntity actorEntity = ActorEntity.builder()
                .name(actor.getName())
                .associatedPinyinSound(actor.getAssociatedPinyinSound())
                .family(actor.getFamily())
                .imageUrl(actor.getImageUrl())
                .build();

        actorEntity = actorRepository.save(actorEntity);

        return Actor.builder()
                .id(actorEntity.getId())
                .name(actorEntity.getName())
                .associatedPinyinSound(actorEntity.getAssociatedPinyinSound())
                .family(actorEntity.getFamily())
                .imageUrl(actorEntity.getImageUrl())
                .build();
    }

    @Override
    public void deleteActor(UUID uuid) throws Exception {

        Optional<ActorEntity> optionalActor = actorRepository.findById(uuid);

        if (optionalActor.isPresent()) {

            ActorEntity actorEntity = optionalActor.get();

            actorRepository.delete(actorEntity);
        } else {
            throw new Exception();
        }
    }

    @Override
    public Actor updateActor(UUID uuid, Actor actor) {

        Optional<ActorEntity> actorEntityOptional = actorRepository.findById(uuid);

        if (actorEntityOptional.isPresent()) {

            ActorEntity actorEntity = ActorEntity.builder()
                    .name(actor.getName())
                    .associatedPinyinSound(actor.getAssociatedPinyinSound())
                    .family(actor.getFamily())
                    .imageUrl(actor.getImageUrl())
                    .build();

            actorEntity = actorRepository.save(actorEntity);

            return Actor.builder()
                    .id(actorEntity.getId())
                    .name(actorEntity.getName())
                    .associatedPinyinSound(actorEntity.getAssociatedPinyinSound())
                    .family(actorEntity.getFamily())
                    .imageUrl(actorEntity.getImageUrl())
                    .build();
        } else {
            return null;
        }

    }

}
