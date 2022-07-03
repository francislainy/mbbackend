package com.example.mbbackend.service.impl.room;

import com.example.mbbackend.entity.room.RoomEntity;
import com.example.mbbackend.model.Room;
import com.example.mbbackend.repository.room.RoomRepository;
import com.example.mbbackend.service.room.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class RoomServiceImpl implements RoomService {

    @Autowired
    RoomRepository roomRepository;

    @Override
    public List<Room> getAllRooms() {

        List<Room> roomList = new ArrayList<>();

        roomRepository.findAll().forEach(roomEntity -> roomList.add(
                Room.builder()
                        .id(roomEntity.getId())
                        .title(roomEntity.getTitle())
                        .tone(roomEntity.getTone())
                        .build()));

        return roomList;
    }

    @Override
    public Room getRoom(UUID roomId) {

        Optional<RoomEntity> roomEntityOptional = roomRepository.findById(roomId);

        if (roomEntityOptional.isPresent()) {

            RoomEntity roomEntity = roomEntityOptional.get();

            return Room.builder()
                    .id(roomEntity.getId())
                    .title(roomEntity.getTitle())
                    .tone(roomEntity.getTone())
                    .build();
        } else {
            return null;
        }
    }

    @Override
    public Room createRoom(Room room) {

        RoomEntity roomEntity = RoomEntity.builder()
                .title(room.getTitle())
                .tone(room.getTone())
                .build();

        roomEntity = roomRepository.save(roomEntity);

        return Room.builder()
                .id(roomEntity.getId())
                .title(roomEntity.getTitle())
                .tone(roomEntity.getTone())
                .build();
    }


    @Override
    public void deleteRoom(UUID roomId) throws Exception {

        Optional<RoomEntity> optionalRoom = roomRepository.findById(roomId);

        if (optionalRoom.isPresent()) {

            RoomEntity roomEntity = optionalRoom.get();

            roomRepository.delete(roomEntity);
        } else {
            throw new Exception();
        }
    }

    @Override
    public Room updateRoom(UUID uuid, Room room) {

        Optional<RoomEntity> roomEntityOptional = roomRepository.findById(uuid);

        if (roomEntityOptional.isPresent()) {

            RoomEntity roomEntity = RoomEntity.builder()
                    .id(room.getId())
                    .title(room.getTitle())
                    .tone(room.getTone())
                    .build();

            roomEntity = roomRepository.save(roomEntity);

            return Room.builder()
                    .id(roomEntity.getId())
                    .title(roomEntity.getTitle())
                    .tone(roomEntity.getTone())
                    .build();
        } else {
            return null;
        }
    }

}
