package com.example.mbbackend.service.actor;

import com.example.mbbackend.model.Actor;

import java.util.List;
import java.util.UUID;

public interface ActorService {

    List<Actor> getAllActors();

    Actor getActor(UUID actorId);

    void deleteActor(UUID actorId) throws Exception;

    Actor createActor(Actor actor);

    Actor updateActor(UUID uuid, Actor actor);
}
