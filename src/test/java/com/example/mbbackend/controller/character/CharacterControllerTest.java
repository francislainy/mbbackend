package com.example.mbbackend.controller.character;

import com.example.mbbackend.model.Character;
import com.example.mbbackend.service.character.CharacterService;
import com.example.mbbackend.util.Utils;
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
@WebMvcTest(controllers = CharacterController.class)
@ExtendWith(MockitoExtension.class)
class CharacterControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    CharacterService characterService;

    @Test
    void testGetAllCharacters() throws Exception {

        List<Character> characterList = new ArrayList<>();
        UUID characterId = UUID.fromString("1bfff94a-b70e-4b39-bd2a-be1c0f898589");

        Character character = Character.builder()
                .id(characterId)
                .hanzi("anyHanzi")
                .pinyin("anyPinyin")
                .meaning("anyMeaning")
                .build();

        characterList.add(character);

        HashMap<String, List<Character>> map = new HashMap<>();
        map.put("characters", characterList);

        String json = Utils.jsonStringFromObject(map);
        when(characterService.getAllCharacters()).thenReturn(characterList);

        mockMvc.perform(get("/api/mb/character"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(content().json(json));
    }

    @Test
    void testGetCharacter() throws Exception {

        UUID characterId = UUID.fromString("1bfff94a-b70e-4b39-bd2a-be1c0f898589");

        Character character = Character.builder()
                .id(characterId)
                .hanzi("anyHanzi")
                .pinyin("anyPinyin")
                .meaning("anyMeaning")
                .build();

        String json = Utils.jsonStringFromObject(character);

        when(characterService.getCharacter(any())).thenReturn(character);

        mockMvc.perform(get("/api/mb/character/{characterId}", characterId))
                .andExpect(status().is2xxSuccessful())
                .andExpect(content().json(json));
    }

    @Test
    void testCreateCharacter() throws Exception {

        Character character = Character.builder()
                .hanzi("anyHanzi")
                .pinyin("anyPinyin")
                .meaning("anyMeaning")
                .build();

        String json = Utils.jsonStringFromObject(character);

        ObjectMapper objectMapper = new ObjectMapper();
        Character character1 = objectMapper.readValue(json, Character.class);

        UUID characterId = UUID.fromString("1bfff94a-b70e-4b39-bd2a-be1c0f898589");
        character1.setId(characterId);
        String json1 = Utils.jsonStringFromObject(character1);

        when(characterService.createCharacter(character)).thenReturn(character1);

        mockMvc.perform(post("/api/mb/character")
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful())
                .andExpect(content().json(json1));
    }

    @Test
    void testDeleteCharacter() throws Exception {

        UUID characterId = UUID.fromString("1bfff94a-b70e-4b39-bd2a-be1c0f898589");

        Character character = Character.builder()
                .id(characterId)
                .build();

        when(characterService.getCharacter(characterId)).thenReturn(character);

        mockMvc.perform(delete("/api/mb/character/{characterId}", characterId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful());
    }

    @Test
    void testUpdateCharacter() throws Exception {

        Character character = Character.builder()
                .hanzi("anyHanzi")
                .pinyin("anyPinyin")
                .meaning("anyMeaning")
                .build();

        String json = Utils.jsonStringFromObject(character);

        ObjectMapper objectMapper = new ObjectMapper();
        Character character1 = objectMapper.readValue(json, Character.class);

        UUID characterId = UUID.fromString("1bfff94a-b70e-4b39-bd2a-be1c0f898589");
        character1.setId(characterId);
        String json1 = Utils.jsonStringFromObject(character1);

        when(characterService.updateCharacter(characterId, character)).thenReturn(character1);

        mockMvc.perform(put("/api/mb/character/{characterId}", characterId)
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful())
                .andExpect(content().json(json1));
    }
}
