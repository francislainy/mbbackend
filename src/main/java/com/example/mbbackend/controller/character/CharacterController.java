package com.example.mbbackend.controller.character;

import com.example.mbbackend.model.Character;
import com.example.mbbackend.repository.character.CharacterRepository;
import com.example.mbbackend.service.character.CharacterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/mb/character")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class CharacterController {

    @Autowired
    CharacterService characterService;

    @Autowired
    CharacterRepository characterRepository;

    @GetMapping({"", "/"})
    public ResponseEntity<Object> getAllCharacter() {

        HashMap<String, List<Character>> map = new HashMap<>();
        map.put("characters", characterService.getAllCharacters());

        return new ResponseEntity<>(map, HttpStatus.OK);
    }

    @GetMapping({"/{characterId}", "/{characterId}/"})
    public ResponseEntity<Object> getSingleCharacter(@PathVariable UUID characterId) {

        return new ResponseEntity<>(characterService.getCharacter(characterId), HttpStatus.OK);
    }

    @PostMapping({"", "/"})
    public ResponseEntity<Character> createCharacter(@RequestBody Character character) {

        if (characterRepository.findCharacterEntityByHanzi(character.getHanzi()).isPresent()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(characterService.createCharacter(character), HttpStatus.CREATED);
    }

    @DeleteMapping({"/{characterId}", "/{characterId}/"})
    public ResponseEntity<Object> deleteCharacter(@PathVariable UUID characterId) throws Exception {

        characterService.deleteCharacter(characterId);
        return new ResponseEntity<>(HttpStatus.PARTIAL_CONTENT);
    }

    @PutMapping({"/{characterId}", "/{characterId}"})
    public ResponseEntity<Object> updateCharacter(@PathVariable UUID characterId, @RequestBody Character character) {

        return new ResponseEntity<>(characterService.updateCharacter(characterId, character), HttpStatus.OK);
    }

}
