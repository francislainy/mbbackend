package com.example.mbbackend.controller.character;

import com.example.mbbackend.controller.BaseController;
import com.example.mbbackend.exception.CharacterAlreadyExistsException;
import com.example.mbbackend.model.Character;
import com.example.mbbackend.repository.character.CharacterRepository;
import com.example.mbbackend.service.character.CharacterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/mb/character")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class CharacterController extends BaseController {

    @Autowired
    CharacterService characterService;

    @Autowired
    CharacterRepository characterRepository;

    @GetMapping({"", "/"})
    public ResponseEntity<Object> getCharacterList() {

        HashMap<String, List<Character>> map = new HashMap<>();
        map.put("characters", characterService.getAllCharacters());

        return new ResponseEntity<>(map, HttpStatus.OK);
    }

    @GetMapping({"/{characterId}", "/{characterId}/"})
    public ResponseEntity<Object> getCharacter(@PathVariable UUID characterId) {

        return new ResponseEntity<>(characterService.getCharacter(characterId), HttpStatus.OK);
    }

    @PostMapping(value = {"", "/"}, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Character> createCharacter(@RequestBody Character character) {

        if (characterRepository.findCharacterEntityByHanzi(character.getHanzi()).isPresent()) {
            throw new CharacterAlreadyExistsException("Character Already Exists");
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
