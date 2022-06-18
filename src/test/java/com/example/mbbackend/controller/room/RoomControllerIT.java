package com.example.mbbackend.controller.room;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.example.mbbackend.config.BaseIntegrationTest;
import com.example.mbbackend.model.Room;
import com.example.mbbackend.util.Utils;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

@Slf4j
@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class RoomControllerIT extends BaseIntegrationTest {

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

    @AfterEach
    void cleanUp() {
        deleteAllRooms();
    }

    @Test
    void testGetAllRooms() {

        createRoom();
        List<Room> list = getRoomList();
        assertEquals(1, list.size());
    }

    @Test
    void testGetRoom() {

        createRoom();
        List<Room> list = getRoomList();

        Room room = objectMapper.convertValue(list.get(0), Room.class);
        UUID roomId = room.getId();

        Response response = rq.get("/api/mb/room/" + roomId);
        assertEquals(200, response.getStatusCode());

        Room room1 = objectMapper.convertValue(response.jsonPath().get(), Room.class);
        assertEquals("anyRoomTitle", room1.getTitle());
    }

    @Test
    void testCreateRoom() {
        createRoom();
    }

    @Test
    void testUpdateRoom() {

        createRoom();

        List<Room> list = getRoomList();

        Room room = getFirstRoomFromList(list);
        room.setTitle("updatedTitle");
        UUID roomId = room.getId();
        
        Response response = rq.body(room).put("/api/mb/room/" + roomId);
        Room updatedRoom = objectMapper.convertValue(response.jsonPath().get(), Room.class);
        assertEquals(200, response.getStatusCode());
        assertEquals("updatedTitle", updatedRoom.getTitle());

        response = rq.get("/api/mb/room/" + roomId);
        assertEquals(200, response.getStatusCode());
        Room room1 = objectMapper.convertValue(response.jsonPath().get(), Room.class);
        assertEquals("updatedTitle", room1.getTitle());
    }
    
    // Helpers
    private Room getFirstRoomFromList(List<Room> list) {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.convertValue(list.get(0), Room.class);
    }

    private List<Room> getRoomList() {
        Response response = rq.get("/api/mb/room");
        List<Room> list = response.jsonPath().getList("rooms");
        assertEquals(200, response.getStatusCode());
        return list;
    }

    private void createRoom() {

        Room room = new Room();
        room.setTitle("anyRoomTitle");
        Response response = rq.body(room).post("/api/mb/room");
        assertEquals(201, response.getStatusCode());
    }

    private void deleteAllRooms() {

        Response response = rq.get("/api/mb/room");
        List<Room> list = response.jsonPath().getList("rooms");

        ObjectMapper objectMapper = new ObjectMapper();
        List<Room> roomList = objectMapper.convertValue(
                list,
                new TypeReference<>() {
                });

        for (Room r : roomList) {
            deleteRoom(r);
        }
    }

    private void deleteRoom(Room room) {
        Response response = rq.body(room).delete("/api/mb/room/" + room.getId());
        assertEquals(206, response.getStatusCode());
    }

}
