package com.example.mbbackend.service.room;

import com.example.mbbackend.config.CharacterTone;
import com.example.mbbackend.entity.room.RoomEntity;
import com.example.mbbackend.model.Room;
import com.example.mbbackend.repository.room.RoomRepository;
import com.example.mbbackend.service.impl.room.RoomServiceImpl;
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
class RoomServiceTest {

    @InjectMocks
    RoomServiceImpl roomService;

    @Mock
    RoomRepository roomRepository;

    @Test
    void testGetRooms() {

        List<RoomEntity> roomEntityList = new ArrayList<>();
        UUID roomId = UUID.fromString("1bfff94a-b70e-4b39-bd2a-be1c0f898589");

        RoomEntity roomEntity = RoomEntity.builder()
                .id(roomId)
                .title("anyTitle")
                .tone(CharacterTone.FIRST)
                .build();

        roomEntityList.add(roomEntity);

        when(roomRepository.findAll()).thenReturn(roomEntityList);

        List<Room> roomList = roomService.getAllRooms();

        assertTrue(roomList.size() > 0);

        Room room = roomList.get(0);

        assertAll(
                () -> assertEquals(roomEntity.getId().toString(), room.getId().toString()),
                () -> assertEquals(roomEntity.getTitle(), room.getTitle()),
                () -> assertEquals(roomEntity.getTone(), room.getTone()));

    }

    @Test
    void testGetRoom() {

        UUID roomId = UUID.fromString("1bfff94a-b70e-4b39-bd2a-be1c0f898589");

        Optional<RoomEntity> optionalLocationEntity = Optional.ofNullable(RoomEntity.builder()
                .id(roomId)
                .title("anyRoom")
                .tone(CharacterTone.FIRST)
                .build());

        when(roomRepository.findById(roomId)).thenReturn(optionalLocationEntity);

        Room room = roomService.getRoom(roomId);

        assertAll(
                () -> assertEquals(roomId.toString(), room.getId().toString()),
                () -> assertEquals("anyRoom", room.getTitle()),
                () -> assertEquals("FIRST", room.getTone().toString()));
    }

    @Test
    void testCreateRoom() {

        UUID roomId = UUID.fromString("1bfff94a-b70e-4b39-bd2a-be1c0f898589");

        RoomEntity roomEntity = RoomEntity.builder()
                .id(roomId)
                .title("anyTitle")
                .tone(CharacterTone.FIRST)
                .build();

        when(roomRepository.save(any())).thenReturn(roomEntity);

        Room room = roomService.createRoom(new Room());

        assertAll(
                () -> assertEquals(roomId.toString(), room.getId().toString()),
                () -> assertEquals(roomEntity.getTitle(), room.getTitle()),
                () -> assertEquals(roomEntity.getTone(), room.getTone()));
    }

    @Test
    void testDeleteRoom() {

        UUID roomId = UUID.fromString("1bfff94a-b70e-4b39-bd2a-be1c0f898589");

        RoomEntity roomEntity = RoomEntity.builder()
                .id(roomId)
                .build();

        when(roomRepository.findById(any())).thenReturn(Optional.ofNullable(roomEntity));

        assertDoesNotThrow(() -> roomService.deleteRoom(roomId));
    }

    @Test
    void testDeleteRoom_ItemNotFound_ThrowsException() {

        UUID roomId = UUID.fromString("1bfff94a-b70e-4b39-bd2a-be1c0f898589");

        assertThrows(Exception.class, () -> roomService.deleteRoom(roomId));
    }

    @Test
    void testUpdateRoom() {

        UUID roomId = UUID.fromString("1bfff94a-b70e-4b39-bd2a-be1c0f898589");

        RoomEntity roomEntity = RoomEntity.builder()
                .id(roomId)
                .title("anyTitle")
                .tone(CharacterTone.FIRST)
                .build();

        Room roomUpdated = Room.builder()
                .id(roomId)
                .title("anyUpdatedTitle")
                .tone(CharacterTone.FIFTH)
                .build();

        when(roomRepository.findById(roomId)).thenReturn(Optional.ofNullable(roomEntity));
        when(roomRepository.save(any())).thenReturn(roomEntity);

        Room room = roomService.updateRoom(roomId, roomUpdated);

        assertAll(
                () -> assertEquals(roomId.toString(), room.getId().toString()),
                () -> assertEquals(roomEntity.getTitle(), room.getTitle()),
                () -> assertEquals(roomEntity.getTone(), room.getTone()));
    }

}
