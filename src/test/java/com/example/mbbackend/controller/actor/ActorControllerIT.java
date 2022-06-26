package com.example.mbbackend.controller.actor;

import com.example.mbbackend.config.ActorFamily;
import com.example.mbbackend.config.BaseIntegrationTest;
import com.example.mbbackend.model.Actor;
import com.example.mbbackend.util.Utils;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Slf4j
@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ActorControllerIT extends BaseIntegrationTest {

    @LocalServerPort
    int port;

    @Autowired
    MockMvc mockMvc;

    RequestSpecification rq;

    ObjectMapper objectMapper;

    @BeforeAll
    void test() {

        objectMapper = new ObjectMapper();

        Map<String, String> headers = new HashMap<>();
        rq = Utils.getRequestSpecification().baseUri("http://localhost:" + port)
                .headers(headers);
    }

    @BeforeEach
    void cleanUp() {
        deleteAllActors();
    }

    @Test
    void testGetAllActors() {

        createActor();
        List<Actor> list = getActorList();
        assertEquals(1, list.size());
    }

    @Test
    void testGetActor() {

        createActor();
        List<Actor> list = getActorList();

        Actor actor = objectMapper.convertValue(list.get(0), Actor.class);
        UUID actorId = actor.getId();

        Response response = rq.get("/api/mb/actor/" + actorId);
        assertEquals(200, response.getStatusCode());

        Actor actor1 = objectMapper.convertValue(response.jsonPath().get(), Actor.class);
        assertEquals("anyActorName", actor1.getName());
    }

    @Test
    void testCreateActor() {
        createActor();
    }

    @Test
    void testUpdateActor() {

        createActor();

        List<Actor> list = getActorList();

        Actor actor = getFirstActorFromList(list);
        actor.setName("updatedName");
        UUID actorId = actor.getId();

        Response response = rq.body(actor).put("/api/mb/actor/" + actorId);
        Actor updatedActor = objectMapper.convertValue(response.jsonPath().get(), Actor.class);
        assertEquals(200, response.getStatusCode());
        assertEquals("updatedName", updatedActor.getName());

        response = rq.get("/api/mb/actor/" + actorId);
        assertEquals(200, response.getStatusCode());
        Actor actor1 = objectMapper.convertValue(response.jsonPath().get(), Actor.class);
        assertEquals("updatedName", actor1.getName());
    }

    // Helpers
    private Actor getFirstActorFromList(List<Actor> list) {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.convertValue(list.get(0), Actor.class);
    }

    private List<Actor> getActorList() {
        Response response = rq.get("/api/mb/actor");
        List<Actor> list = response.jsonPath().getList("actors");
        assertEquals(200, response.getStatusCode());
        return list;
    }

    private void createActor() {

        Actor actor = new Actor();
        actor.setName("anyActorName");
        actor.setFamily(ActorFamily.FEMALE);
        actor.setAssociatedPinyinSound("ji");
        Response response = rq.body(actor).post("/api/mb/actor");
        assertEquals(201, response.getStatusCode());
    }

    private void deleteAllActors() {

        Response response = rq.get("/api/mb/actor");
        List<Actor> list = response.jsonPath().getList("actors");

        ObjectMapper objectMapper = new ObjectMapper();
        List<Actor> actorList = objectMapper.convertValue(
                list,
                new TypeReference<>() {
                });

        for (Actor r : actorList) {
            deleteActor(r);
        }
    }

    private void deleteActor(Actor actor) {
        Response response = rq.body(actor).delete("/api/mb/actor/" + actor.getId());
        assertEquals(206, response.getStatusCode());
    }

}
