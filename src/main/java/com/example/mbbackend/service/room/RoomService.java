package com.example.mbbackend.service.room;

import com.example.mbbackend.model.Room;

import java.util.List;
import java.util.UUID;

public interface RoomService {

    List<Room> getAllRooms();

    Room getRoom(UUID roomId);

    void deleteRoom(UUID roomId) throws Exception;

    Room createRoom(Room room);

    Room updateRoom(UUID id, Room room);
}
