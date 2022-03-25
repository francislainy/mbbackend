package com.example.mbblueprintbackend.controller.room;

import com.example.mbblueprintbackend.model.Room;
import com.example.mbblueprintbackend.service.room.RoomService;
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
@WebMvcTest(controllers = RoomController.class)
@ExtendWith(MockitoExtension.class)
class RoomControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    RoomService roomService;

    @Test
    void testGetAllRooms() throws Exception {

        List<Room> roomList = new ArrayList<>();
        UUID roomId = UUID.fromString("1bfff94a-b70e-4b39-bd2a-be1c0f898589");

        Room room = Room.builder()
                .id(roomId)
                .title("anyTitle")
                .build();

        roomList.add(room);

        HashMap<String, List<Room>> map = new HashMap<>();
        map.put("rooms", roomList);

        String json = Utils.jsonStringFromObject(map);
        when(roomService.getAllRooms()).thenReturn(roomList);

        mockMvc.perform(get("/api/mb/room"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(content().json(json));
    }

    @Test
    void testGetRoom() throws Exception {

        UUID roomId = UUID.fromString("1bfff94a-b70e-4b39-bd2a-be1c0f898589");

        Room room = Room.builder()
                .id(roomId)
                .title("anyTitle")
                .build();

        String json = Utils.jsonStringFromObject(room);

        when(roomService.getRoom(any())).thenReturn(room);

        mockMvc.perform(get("/api/mb/room/{roomId}", roomId))
                .andExpect(status().is2xxSuccessful())
                .andExpect(content().json(json));
    }

    @Test
    void testCreateRoom() throws Exception {

        Room room = Room.builder()
                .title("anyTitle")
                .build();

        String json = Utils.jsonStringFromObject(room);

        ObjectMapper objectMapper = new ObjectMapper();
        Room room1 = objectMapper.readValue(json, Room.class);

        UUID roomId = UUID.fromString("1bfff94a-b70e-4b39-bd2a-be1c0f898589");
        room1.setId(roomId);
        String json1 = Utils.jsonStringFromObject(room1);

        when(roomService.createRoom(room)).thenReturn(room1);

        mockMvc.perform(post("/api/mb/room")
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful())
                .andExpect(content().json(json1));
    }

    @Test
    void testDeleteRoom() throws Exception {

        UUID roomId = UUID.fromString("1bfff94a-b70e-4b39-bd2a-be1c0f898589");

        Room room = Room.builder()
                .id(roomId)
                .title("anyRoom")
                .build();

        when(roomService.getRoom(roomId)).thenReturn(room);

        mockMvc.perform(delete("/api/mb/room/{roomId}", roomId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful());
    }

    @Test
    void testUpdateRoom() throws Exception {

        Room room = Room.builder()
                .title("anyTitle")
                .build();

        String json = Utils.jsonStringFromObject(room);

        ObjectMapper objectMapper = new ObjectMapper();
        Room room1 = objectMapper.readValue(json, Room.class);

        UUID roomId = UUID.fromString("1bfff94a-b70e-4b39-bd2a-be1c0f898589");
        room1.setId(roomId);
        String json1 = Utils.jsonStringFromObject(room1);

        when(roomService.updateRoom(roomId, room)).thenReturn(room1);

        mockMvc.perform(put("/api/mb/room/{roomId}", roomId)
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful())
                .andExpect(content().json(json1));
    }
}
