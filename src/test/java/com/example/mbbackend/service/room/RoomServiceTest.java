package com.example.mbbackend.service.room;

import com.example.mbbackend.entity.actor.ActorEntity;
import com.example.mbbackend.entity.character.CharacterEntity;
import com.example.mbbackend.entity.location.LocationEntity;
import com.example.mbbackend.entity.movie.MovieEntity;
import com.example.mbbackend.entity.room.RoomEntity;
import com.example.mbbackend.model.Room;
import com.example.mbbackend.repository.room.RoomRepository;
import com.example.mbbackend.service.impl.room.RoomServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

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
    void testGetAllRooms() {

        List<RoomEntity> roomEntityList = new ArrayList<>();
        UUID roomId = UUID.fromString("1bfff94a-b70e-4b39-bd2a-be1c0f898589");

        RoomEntity roomEntity = RoomEntity.builder()
                .id(roomId)
                .title("anyTitle")
                .build();

        roomEntityList.add(roomEntity);

        when(roomRepository.findAll()).thenReturn(roomEntityList);

        List<Room> roomList = roomService.getAllRooms();

        assertTrue(roomList.size() > 0);

        Room room = roomList.get(0);

        assertAll(
                () -> assertEquals(roomEntity.getId().toString(), room.getId().toString()),
                () -> assertEquals(roomEntity.getTitle(), room.getTitle()));

    }

    @Test
    void testGetRoom() {

        UUID roomId = UUID.fromString("1bfff94a-b70e-4b39-bd2a-be1c0f898589");

        Optional<RoomEntity> optionalLocationEntity = Optional.ofNullable(RoomEntity.builder()
                .id(roomId)
                .title("anyRoom")
                .build());

        when(roomRepository.findById(roomId)).thenReturn(optionalLocationEntity);

        Room room = roomService.getRoom(roomId);

        assertAll(
                () -> assertEquals(roomId.toString(), room.getId().toString()),
                () -> assertEquals("anyRoom", room.getTitle()));
    }

    @Test
    void testCreateRoom() {

        UUID roomId = UUID.fromString("1bfff94a-b70e-4b39-bd2a-be1c0f898589");

        RoomEntity roomEntity = RoomEntity.builder()
                .id(roomId)
                .title("anyTitle")
                .build();

        when(roomRepository.save(any())).thenReturn(roomEntity);

        Room room = roomService.createRoom(new Room());

        assertAll(
                () -> assertEquals(roomId.toString(), room.getId().toString()),
                () -> assertEquals(roomEntity.getTitle(), room.getTitle()));
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
    void testDeleteRoomBelongsToAMovie() {

        UUID movieId = UUID.fromString("1bfff94a-b70e-4b39-bd2a-be1c0f898589");
        UUID characterId = UUID.fromString("2afff94a-b70e-4b39-bd2a-be1c0f898589");
        UUID actorId = UUID.fromString("2afff94a-b70e-4b39-bd2a-be1c0f898589");
        UUID locationId = UUID.fromString("2afff94a-b70e-4b39-bd2a-be1c0f898589");
        UUID roomId = UUID.fromString("2afff94a-b70e-4b39-bd2a-be1c0f898589");

        RoomEntity roomEntity = RoomEntity.builder()
                .id(roomId)
                .build();

        Optional<CharacterEntity> optionalCharacterEntity = Optional.ofNullable(CharacterEntity.builder()
                .id(characterId)
                .hanzi("anyHanzi")
                .pinyin("anyPinyin")
                .meaning("anyMeaning")
                .build());

        CharacterEntity characterEntity = optionalCharacterEntity.get();

        MovieEntity movieEntity = MovieEntity.builder()
                .id(movieId)
                .scene("anyScene")
                .imageUrl("anyUrl")
                .character(CharacterEntity.builder()
                        .id(characterId)
                        .hanzi(characterEntity.getHanzi())
                        .pinyin(characterEntity.getPinyin())
                        .meaning(characterEntity.getMeaning())
                        .build())
                .actor(ActorEntity.builder()
                        .id(actorId)
                        .name("anyName")
                        .build())
                .location(LocationEntity.builder()
                        .id(locationId)
                        .title("anyTitle")
                        .associatedPinyinSound("anySound")
                        .build())
                .room(roomEntity)
                .build();

        java.util.Set<MovieEntity> movieEntityList = new HashSet<>();
        movieEntityList.add(movieEntity);

        roomEntity.setMovie(movieEntityList);

        when(roomRepository.findById(any())).thenReturn(Optional.of(roomEntity));

        assertDoesNotThrow(() -> roomService.deleteRoom(roomId));
    }


    @Test
    void testUpdateRoom() {

        UUID roomId = UUID.fromString("1bfff94a-b70e-4b39-bd2a-be1c0f898589");

        RoomEntity roomEntity = RoomEntity.builder()
                .id(roomId)
                .title("anyTitle")
                .build();

        Optional<RoomEntity> roomEntity1 = Optional.ofNullable(RoomEntity.builder()
                .id(roomId)
                .title("anyTitle")
                .build());

        Room room0 = Room.builder()
                .id(roomId)
                .title("anyTitle")
                .build();

        when(roomRepository.findById(roomId)).thenReturn(roomEntity1);
        when(roomRepository.save(any())).thenReturn(roomEntity);

        Room room = roomService.updateRoom(roomId, room0);

        assertAll(
                () -> assertEquals(roomId.toString(), room.getId().toString()),
                () -> assertEquals(roomEntity.getTitle(), room.getTitle()));
    }

}
