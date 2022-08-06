package com.example.mbbackend.controller.character;

import com.example.mbbackend.config.BaseIntegrationTest;
import com.example.mbbackend.config.CharacterTone;
import com.example.mbbackend.model.Character;
import com.example.mbbackend.util.Utils;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
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
class CharacterControllerIT extends BaseIntegrationTest {

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
        deleteAllCharacters();
    }

    @Test
    void testGetAllCharacters() {

        createCharacter();
        List<Character> list = getCharacterList();
        assertEquals(1, list.size());
    }

    @Test
    void testGetCharacter() {

        createCharacter();
        List<Character> list = getCharacterList();

        Character character = objectMapper.convertValue(list.get(0), Character.class);
        UUID characterId = character.getId();

        Response response = rq.get("/api/mb/character/" + characterId);
        assertEquals(200, response.getStatusCode());

        Character character1 = objectMapper.convertValue(response.jsonPath().get(), Character.class);
        assertEquals("anyHanzi", character1.getHanzi());
    }

    @Test
    void testCreateCharacter() {
        createCharacter();
    }

    @Test
    void testUpdateCharacter() {

        createCharacter();

        List<Character> list = getCharacterList();

        Character character = getFirstCharacterFromList(list);
        character.setHanzi("updatedHanzi");
        UUID characterId = character.getId();

        Response response = rq.body(character).put("/api/mb/character/" + characterId);
        Character updatedCharacter = objectMapper.convertValue(response.jsonPath().get(), Character.class);
        assertEquals(200, response.getStatusCode());
        assertEquals("updatedHanzi", updatedCharacter.getHanzi());

        response = rq.get("/api/mb/character/" + characterId);
        assertEquals(200, response.getStatusCode());
        Character character1 = objectMapper.convertValue(response.jsonPath().get(), Character.class);
        assertEquals("updatedHanzi", character1.getHanzi());
    }

    // Helpers
    private Character getFirstCharacterFromList(List<Character> list) {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.convertValue(list.get(0), Character.class);
    }

    private List<Character> getCharacterList() {
        Response response = rq.get("/api/mb/character");
        List<Character> list = response.jsonPath().getList("characters");
        assertEquals(200, response.getStatusCode());
        return list;
    }

    private void createCharacter() {

        Character character = Character.builder()
                .hanzi("anyHanzi")
                .pinyin("anyPinyin")
                .meaning("anyMeaning")
                .tone(CharacterTone.FIFTH)
                .prop(true)
                .build();

        Response response = rq.body(character).post("/api/mb/character");
        assertEquals(201, response.getStatusCode());
    }

    private void deleteAllCharacters() {

        Response response = rq.get("/api/mb/character");
        List<Character> list = response.jsonPath().getList("characters");

        ObjectMapper objectMapper = new ObjectMapper();
        List<Character> characterList = objectMapper.convertValue(
                list,
                new TypeReference<>() {
                });

        for (Character r : characterList) {
            deleteCharacter(r);
        }
    }

    private void deleteCharacter(Character character) {
        Response response = rq.body(character).delete("/api/mb/character/" + character.getId());
        assertEquals(206, response.getStatusCode());
    }

}
