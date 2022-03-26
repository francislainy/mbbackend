package com.example.mbblueprintbackend.controller.actor;

import com.example.mbblueprintbackend.model.Actor;
import com.example.mbblueprintbackend.service.actor.ActorService;
import com.example.mbblueprintbackend.util.Utils;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Slf4j
@WebMvcTest(controllers = ActorController.class)
@ExtendWith(MockitoExtension.class)
class ActorControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    ActorService actorService;

    @Test
    void testGetAllActors() throws Exception {

        List<Actor> actorList = new ArrayList<>();
        UUID actorId = UUID.fromString("1bfff94a-b70e-4b39-bd2a-be1c0f898589");

        Actor actor = Actor.builder()
                .id(actorId)
                .name("anyName")
                .associatedPinyinSound("anySound")
                .family("anyFamily")
                .imageUrl("anyUrl")
                .build();

        actorList.add(actor);

        HashMap<String, List<Actor>> map = new HashMap<>();
        map.put("actors", actorList);

        String json = Utils.jsonStringFromObject(map);
        when(actorService.getAllActors()).thenReturn(actorList);

        mockMvc.perform(get("/api/mb/actor"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(content().json(json));
    }

    @Test
    void testGetActor() throws Exception {

        UUID actorId = UUID.fromString("1bfff94a-b70e-4b39-bd2a-be1c0f898589");

        Actor actor = Actor.builder()
                .id(actorId)
                .name("anyName")
                .associatedPinyinSound("anySound")
                .family("anyFamily")
                .imageUrl("anyUrl")
                .build();

        String json = Utils.jsonStringFromObject(actor);

        when(actorService.getActor(any())).thenReturn(actor);

        mockMvc.perform(get("/api/mb/actor/{actorId}", actorId))
                .andExpect(status().is2xxSuccessful())
                .andExpect(content().json(json));
    }

    @Test
    void testCreateActor() throws Exception {

        Actor actor = Actor.builder()
                .name("anyName")
                .associatedPinyinSound("anySound")
                .family("anyFamily")
                .imageUrl("anyUrl")
                .build();

        String json = Utils.jsonStringFromObject(actor);

        ObjectMapper objectMapper = new ObjectMapper();
        Actor actor1 = objectMapper.readValue(json, Actor.class);

        UUID actorId = UUID.fromString("1bfff94a-b70e-4b39-bd2a-be1c0f898589");
        actor1.setId(actorId);
        String json1 = Utils.jsonStringFromObject(actor1);

        when(actorService.createActor(actor)).thenReturn(actor1);

        mockMvc.perform(post("/api/mb/actor")
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful())
                .andExpect(content().json(json1));
    }

    @Test
    void testDeleteLocation() throws Exception {

        UUID actorId = UUID.fromString("1bfff94a-b70e-4b39-bd2a-be1c0f898589");

        Actor actor = Actor.builder()
                .id(actorId)
                .build();

        when(actorService.getActor(actorId)).thenReturn(actor);

        mockMvc.perform(delete("/api/mb/actor/{actorId}", actorId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful());
    }

    @Test
    void testUpdateActor() throws Exception {

        Actor actor = Actor.builder()
                .name("anyName")
                .associatedPinyinSound("anySound")
                .family("anyFamily")
                .imageUrl("anyUrl")
                .build();

        String json = Utils.jsonStringFromObject(actor);

        ObjectMapper objectMapper = new ObjectMapper();
        Actor actor1 = objectMapper.readValue(json, Actor.class);

        UUID actorId = UUID.fromString("1bfff94a-b70e-4b39-bd2a-be1c0f898589");
        actor1.setId(actorId);
        String json1 = Utils.jsonStringFromObject(actor1);

        when(actorService.updateActor(actorId, actor)).thenReturn(actor1);

        mockMvc.perform(put("/api/mb/actor/{actorId}", actorId)
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful())
                .andExpect(content().json(json1));
    }
}
