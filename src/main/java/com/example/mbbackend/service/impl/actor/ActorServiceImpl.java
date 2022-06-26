package com.example.mbbackend.service.impl.actor;

import com.example.mbbackend.config.ActorFamily;
import com.example.mbbackend.entity.actor.ActorEntity;
import com.example.mbbackend.model.Actor;
import com.example.mbbackend.repository.actor.ActorRepository;
import com.example.mbbackend.service.actor.ActorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

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

        if (!checkValidSoundForActor(actor.getAssociatedPinyinSound(), actor.getFamily())) {
            return null;
        }

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

        if (!checkValidSoundForActor(actor.getAssociatedPinyinSound(), actor.getFamily())) {
            return null;
        }

        Optional<ActorEntity> actorEntityOptional = actorRepository.findById(uuid);

        if (actorEntityOptional.isPresent()) {

            ActorEntity actorEntity = ActorEntity.builder()
                    .id(actor.getId())
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

    public boolean checkValidSoundForActor(String pinyinSound, ActorFamily actorFamily) {

        String[] femaleSounds = new String[]{"y", "bi", "pi", "mi", "di", "ti", "ni", "li", "ji", "qi", "xi"};
        String[] maleSounds = new String[]{"b", "p", "m", "f", "d", "t", "n", "l", "g", "k", "zh", "ch", "sh", "r", "z", "c", "s", "zhi", "chi", "shi", "ri", "zi", "ci", "si"};
        String[] godSounds = new String[]{"yu", "nu", "lu", "ju", "qu", "xu"};
        String[] fictionalSounds = new String[]{"w", "bu", "pu", "mu", "fu", "du", "tu", "nu", "lu", "gu", "ku", "hu", "zhu", "chu", "shu", "ru", "zu", "cu", "su"};

        return switch (actorFamily) {
            case FEMALE -> Arrays.asList(femaleSounds).contains(pinyinSound);
            case MALE -> Arrays.asList(maleSounds).contains(pinyinSound);
            case GOD -> Arrays.asList(godSounds).contains(pinyinSound);
            case FICTIONAL -> Arrays.asList(fictionalSounds).contains(pinyinSound);
        };

    }
}
