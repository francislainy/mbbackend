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

        Optional<RoomEntity> optionalRoom = null;
        try {
            optionalRoom = roomRepository.findById(roomId);
        } catch (StackOverflowError e) {
            System.err.println("blabla!");
            e.printStackTrace();
        }

        if (optionalRoom.isPresent()) {

            RoomEntity roomEntity = null;
            try {
                roomEntity = optionalRoom.get();
            } catch (StackOverflowError e) {
//                throw new Exception(e.getMessage());
                System.out.println("here");
            }

            try {
                roomRepository.delete(roomEntity);
            } catch (Exception e) {
                throw new Exception(e.getMessage());
            }

        }

//        else {
//            throw new Exception("Item not present");
//        }
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
