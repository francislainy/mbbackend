package com.example.mbbackend.repository.room;

import com.example.mbbackend.entity.room.RoomEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface RoomRepository extends JpaRepository<RoomEntity, UUID> {

    String QUERY_ROOM_BY_TONE_INTEGER = "SELECT * FROM room WHERE tone = :tone LIMIT 1";

    @Query(value = QUERY_ROOM_BY_TONE_INTEGER, nativeQuery = true)
    RoomEntity findRoomByTone(String tone);

}
