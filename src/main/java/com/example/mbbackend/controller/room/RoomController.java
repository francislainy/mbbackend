package com.example.mbbackend.controller.room;

import com.example.mbbackend.model.Room;
import com.example.mbbackend.service.room.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/mb")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class RoomController {

    @Autowired
    RoomService roomService;

    @GetMapping("/room")
    public ResponseEntity<Object> getAllRoom() {

        HashMap<String, List<Room>> map = new HashMap<>();
        map.put("rooms", roomService.getAllRooms());

        return new ResponseEntity<>(map, HttpStatus.OK);
    }

    @GetMapping("/room/{roomId}")
    public ResponseEntity<Object> getSingleRoom(@PathVariable UUID roomId) {

        return new ResponseEntity<>(roomService.getRoom(roomId), HttpStatus.OK);
    }

    @PostMapping("/room")
    public ResponseEntity<Room> createRoom(@RequestBody Room room) {

        return new ResponseEntity<>(roomService.createRoom(room), HttpStatus.CREATED);
    }

    @DeleteMapping("/room/{roomId}")
    public ResponseEntity<Object> deleteRoom(@PathVariable UUID roomId) throws Exception {

        roomService.deleteRoom(roomId);
        return new ResponseEntity<>(HttpStatus.PARTIAL_CONTENT);
    }

    @PutMapping("/room/{roomId}")
    public ResponseEntity<Object> updateRoom(@PathVariable UUID roomId, @RequestBody Room room) {

        return new ResponseEntity<>(roomService.updateRoom(roomId, room), HttpStatus.OK);
    }

}
