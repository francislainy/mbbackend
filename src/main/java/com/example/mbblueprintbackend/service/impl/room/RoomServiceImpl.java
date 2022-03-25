package com.example.mbblueprintbackend.service.impl.room;

import com.example.mbblueprintbackend.entity.room.RoomEntity;
import com.example.mbblueprintbackend.model.Room;
import com.example.mbblueprintbackend.repository.room.RoomRepository;
import com.example.mbblueprintbackend.service.room.RoomService;
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
                    .build();
        } else {
            return null;
        }
    }

    @Override
    public Room createRoom(Room room) {

        RoomEntity roomEntity = RoomEntity.builder()
                .title(room.getTitle())
                .build();

        roomEntity = roomRepository.save(roomEntity);

        return Room.builder()
                .id(roomEntity.getId())
                .title(roomEntity.getTitle())
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
                    .title(room.getTitle())
                    .build();

            roomEntity = roomRepository.save(roomEntity);

            return Room.builder()
                    .id(roomEntity.getId())
                    .title(roomEntity.getTitle())
                    .build();
        } else {
            return null;
        }
    }

}
