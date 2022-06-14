package com.example.mbbackend.controller.room;

import com.example.mbbackend.config.BaseIntegrationTest;
import com.example.mbbackend.model.Location;
import com.example.mbbackend.model.Room;
import com.example.mbbackend.service.room.RoomService;
import com.example.mbbackend.util.Utils;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.config.RestAssuredConfig;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.*;

import static com.example.mbbackend.config.Constants.MOCK_PACT_URL;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.AFTER_TEST_METHOD;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.BEFORE_TEST_METHOD;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Slf4j
@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@AutoConfigureMockMvc
//@Sql(scripts = "classpath:init.sql", executionPhase = BEFORE_TEST_METHOD)
//@Sql(scripts = "classpath:clean-up.sql", executionPhase = AFTER_TEST_METHOD)
class RoomControllerIT extends BaseIntegrationTest {

    @LocalServerPort
    int port;

    @Autowired
    MockMvc mockMvc;

//    @MockBean
//    RoomService roomService;

    @BeforeAll
    static void test() {
        //
    }

    @Test
    void testGetAllRooms() {

        Room room = new Room();
        room.setTitle("anyRoomTitle");

        Map<String, String> headers = new HashMap<>();
        RequestSpecification rq = Utils.getRequestSpecification().baseUri("http://localhost:" + port).headers(headers);
        Response response = rq.body(room).post("/api/mb/room");

        assertEquals(201, response.getStatusCode());

        response = rq.get("/api/mb/room");

        List<Room> list = response.jsonPath().getList("rooms");
        assertEquals(200, response.getStatusCode());
        assertEquals(1, list.size());
    }

//    @Test
//    void testGetAllRooms() throws Exception {
//
//        mockMvc.perform(get("/api/mb/room"))
//                .andExpect(status().is2xxSuccessful());
//    }

}
