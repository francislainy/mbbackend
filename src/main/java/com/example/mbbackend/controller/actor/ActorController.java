package com.example.mbbackend.controller.actor;

import com.example.mbbackend.model.Actor;
import com.example.mbbackend.service.actor.ActorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/mb/actor")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class ActorController {

    @Autowired
    ActorService actorService;

    @GetMapping({"", "/"})
    public ResponseEntity<Object> getAllActors() {

        HashMap<String, List<Actor>> map = new HashMap<>();
        map.put("actors", actorService.getAllActors());

        return new ResponseEntity<>(map, HttpStatus.OK);
    }

    @GetMapping({"/{actorId}", "/{actorId}/"})
    public ResponseEntity<Object> getActor(@PathVariable UUID actorId) {

        return new ResponseEntity<>(actorService.getActor(actorId), HttpStatus.OK);
    }

    @PostMapping({"", "/"})
    public ResponseEntity<Actor> createActor(@RequestBody Actor actor) {

        return new ResponseEntity<>(actorService.createActor(actor), HttpStatus.CREATED);
    }

    @DeleteMapping("{actorId}")
    public ResponseEntity<Object> deleteActor(@PathVariable UUID actorId) throws Exception {

        actorService.deleteActor(actorId);
        return new ResponseEntity<>(HttpStatus.PARTIAL_CONTENT);
    }

    @PutMapping("{actorId}")
    public ResponseEntity<Object> updateActor(@PathVariable UUID actorId, @RequestBody Actor actor) {

        return new ResponseEntity<>(actorService.updateActor(actorId, actor), HttpStatus.OK);
    }

}
