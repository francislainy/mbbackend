package com.example.mbblueprintbackend.service.actor;

import com.example.mbblueprintbackend.entity.actor.ActorEntity;
import com.example.mbblueprintbackend.model.Actor;
import com.example.mbblueprintbackend.repository.actor.ActorRepository;
import com.example.mbblueprintbackend.service.impl.actor.ActorServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ActorServiceTest {

    @InjectMocks
    ActorServiceImpl actorService;

    @Mock
    ActorRepository actorRepository;

    @Test
    void testGetAllActors() {

        List<ActorEntity> actorEntityList = new ArrayList<>();
        UUID locationId = UUID.fromString("1bfff94a-b70e-4b39-bd2a-be1c0f898589");

        ActorEntity actorEntity = ActorEntity.builder()
                .id(locationId)
                .name("Shakira")
                .associatedPinyinSound("Ou")
                .family("Female i sound")
                .imageUrl("anyUrl")
                .build();

        actorEntityList.add(actorEntity);

        when(actorRepository.findAll()).thenReturn(actorEntityList);

        List<Actor> actorList = actorService.getAllActors();
        assertTrue(actorList.size() > 0);

        Actor actor = actorList.get(0);

        assertAll(
                () -> assertEquals(actorEntity.getId().toString(), actor.getId().toString()),
                () -> assertEquals(actorEntity.getName(), actor.getName()),
                () -> assertEquals(actorEntity.getAssociatedPinyinSound(), actor.getAssociatedPinyinSound()),
                () -> assertEquals(actorEntity.getFamily(), actor.getFamily()),
                () -> assertEquals(actorEntity.getImageUrl(), actor.getImageUrl()));
    }

    @Test
    void testGetActor() {

        UUID actorId = UUID.fromString("1bfff94a-b70e-4b39-bd2a-be1c0f898589");

        Optional<ActorEntity> actorEntity = Optional.ofNullable(ActorEntity.builder()
                .id(actorId)
                .name("anyName")
                .associatedPinyinSound("anySound")
                .family("anyFamily")
                .imageUrl("anyUrl")
                .build());

        when(actorRepository.findById(actorId)).thenReturn(actorEntity);

        Actor actor = actorService.getActor(actorId);

        assertAll(
                () -> assertEquals(actorId.toString(), actor.getId().toString()),
                () -> assertEquals("anyName", actor.getName()),
                () -> assertEquals("anySound", actor.getAssociatedPinyinSound()),
                () -> assertEquals("anyFamily", actor.getFamily()),
                () -> assertEquals("anyUrl", actor.getImageUrl()));
    }

    @Test
    void testCreateActor() {

        UUID actorId = UUID.fromString("1bfff94a-b70e-4b39-bd2a-be1c0f898589");

        ActorEntity actorEntity = ActorEntity.builder()
                .id(actorId)
                .name("anyName")
                .associatedPinyinSound("anySound")
                .family("anyFamily")
                .imageUrl("imageUrl")
                .build();

        when(actorRepository.save(any())).thenReturn(actorEntity);

        Actor actor = actorService.createActor(new Actor());

        assertAll(
                () -> assertEquals(actorId.toString(), actor.getId().toString()),
                () -> assertEquals(actorEntity.getName(), actor.getName()),
                () -> assertEquals(actorEntity.getAssociatedPinyinSound(), actor.getAssociatedPinyinSound()),
                () -> assertEquals(actorEntity.getFamily(), actor.getFamily()),
                () -> assertEquals(actorEntity.getImageUrl(), actor.getImageUrl()));
    }

    @Test
    void testDeleteActor() {

        UUID actorId = UUID.fromString("1bfff94a-b70e-4b39-bd2a-be1c0f898589");

        ActorEntity actorEntity = ActorEntity.builder()
                .id(actorId)
                .build();

        when(actorRepository.findById(any())).thenReturn(Optional.ofNullable(actorEntity));

        assertDoesNotThrow(() -> actorService.deleteActor(actorId));
    }

    @Test
    void testDeleteActor_ItemNotFound_ThrowsException() {

        UUID actorId = UUID.fromString("1bfff94a-b70e-4b39-bd2a-be1c0f898589");

        assertThrows(Exception.class, () -> actorService.deleteActor(actorId));
    }

    @Test
    void testUpdateActor() {

        UUID locationId = UUID.fromString("1bfff94a-b70e-4b39-bd2a-be1c0f898589");

        ActorEntity locationEntity = ActorEntity.builder()
                .id(locationId)
                .name("anyName")
                .family("anyFamily")
                .associatedPinyinSound("Ou")
                .imageUrl("anyUrl")
                .build();

        Optional<ActorEntity> locationEntity1 = Optional.ofNullable(ActorEntity.builder()
                .id(locationId)
                .name("anyName")
                .family("anyFamily")
                .associatedPinyinSound("Ou")
                .imageUrl("anyUrl")
                .build());

        Actor actor0 = Actor.builder()
                .id(locationId)
                .name("anyName")
                .family("anyFamily")
                .associatedPinyinSound("Ou")
                .imageUrl("anyUrl")
                .build();

        when(actorRepository.findById(locationId)).thenReturn(locationEntity1);
        when(actorRepository.save(any())).thenReturn(locationEntity);

        Actor actor = actorService.updateActor(locationId, actor0);

        assertAll(
                () -> assertEquals(locationId.toString(), actor.getId().toString()),
                () -> assertEquals(locationEntity.getName(), actor.getName()),
                () -> assertEquals(locationEntity.getAssociatedPinyinSound(), actor.getAssociatedPinyinSound()),
                () -> assertEquals(locationEntity.getFamily(), actor.getFamily()),
                () -> assertEquals(locationEntity.getImageUrl(), actor.getImageUrl()));
    }

}
