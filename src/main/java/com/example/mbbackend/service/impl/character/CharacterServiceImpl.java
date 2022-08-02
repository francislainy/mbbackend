package com.example.mbbackend.service.impl.character;

import com.example.mbbackend.entity.actor.ActorEntity;
import com.example.mbbackend.entity.character.CharacterEntity;
import com.example.mbbackend.entity.location.LocationEntity;
import com.example.mbbackend.entity.movie.MovieEntity;
import com.example.mbbackend.entity.room.RoomEntity;
import com.example.mbbackend.model.Actor;
import com.example.mbbackend.model.Character;
import com.example.mbbackend.model.Movie;
import com.example.mbbackend.repository.actor.ActorRepository;
import com.example.mbbackend.repository.character.CharacterRepository;
import com.example.mbbackend.repository.location.LocationRepository;
import com.example.mbbackend.repository.movie.MovieRepository;
import com.example.mbbackend.repository.room.RoomRepository;
import com.example.mbbackend.service.character.CharacterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class CharacterServiceImpl implements CharacterService {

    @Autowired
    CharacterRepository characterRepository;

    @Autowired
    MovieRepository movieRepository;

    @Autowired
    ActorRepository actorRepository;

    @Autowired
    LocationRepository locationRepository;

    @Autowired
    RoomRepository roomRepository;

    @Override
    public List<Character> getAllCharacters() {

        List<Character> characterList = new ArrayList<>();

        characterRepository.findAll().forEach(characterEntity -> {

            List<MovieEntity> movieEntityList = movieRepository.findMoviesByCharacterId(characterEntity.getId());

            Movie movie = null;
            if (!movieEntityList.isEmpty()) {
                movie = Movie.builder()
                        .id(movieEntityList.get(0).getId())
                        .build();
            }

            characterList.add(
                    Character.builder()
                            .id(characterEntity.getId())
                            .hanzi(characterEntity.getHanzi())
                            .pinyin(characterEntity.getPinyin())
                            .meaning(characterEntity.getMeaning())
                            .tone(characterEntity.getTone())
                            .prop(characterEntity.isProp())
                            .movie(movie)
                            .build());
        });

        return characterList;
    }

    @Override
    public Character getCharacter(UUID characterId) {

        Optional<CharacterEntity> characterEntityOptional = characterRepository.findById(characterId);

        List<MovieEntity> movieEntityList = movieRepository.findMoviesByCharacterId(characterId);

        Movie movie = null;
        if (!movieEntityList.isEmpty()) {
            movie = Movie.builder()
                    .id(movieEntityList.get(0).getId())
                    .build();
        }

        if (characterEntityOptional.isPresent()) {
            CharacterEntity characterEntity = characterEntityOptional.get();
            return Character.builder()
                    .id(characterEntity.getId())
                    .hanzi(characterEntity.getHanzi())
                    .pinyin(characterEntity.getPinyin())
                    .meaning(characterEntity.getMeaning())
                    .tone(characterEntity.getTone())
                    .prop(characterEntity.isProp())
                    .movie(movie)
                    .build();
        } else {
            return null;
        }
    }

    @Override
    public Character createCharacter(Character character) {

        Optional<CharacterEntity> characterHanzi = characterRepository.findCharacterEntityByHanzi(character.getHanzi());

        if (characterHanzi.isPresent()) {
            return null;
        }

        CharacterEntity characterEntity = CharacterEntity.builder()
                .hanzi(character.getHanzi())
                .pinyin(character.getPinyin())
                .meaning(character.getMeaning())
                .tone(character.getTone())
                .prop(character.isProp())
                .build();

        MovieEntity movieEntity = MovieEntity.builder()
                .scene("")
                .imageUrl("")
                .character(characterEntity)
                .actor(getActorFromCharacter(characterEntity))
                .location(null)
                .room(null)
                .build();

        characterEntity = characterRepository.save(characterEntity);
        movieEntity = movieRepository.save(movieEntity);

        return Character.builder()
                .id(characterEntity.getId())
                .hanzi(characterEntity.getHanzi())
                .pinyin(characterEntity.getPinyin())
                .meaning(characterEntity.getMeaning())
                .tone(characterEntity.getTone())
                .prop(characterEntity.isProp())
                .movie(Movie.builder()
                        .id(movieEntity.getId())
                        .actor(Actor.builder()
                                .id(movieEntity.getActor().getId())
                                .name(movieEntity.getActor().getName())
                                .associatedPinyinSound(movieEntity.getActor().getAssociatedPinyinSound())
                                .build())
                        .build())
                .build();
    }

    @Override
    public void deleteCharacter(UUID characterId) throws Exception {

        Optional<CharacterEntity> optionalCharacter = characterRepository.findById(characterId);

        if (optionalCharacter.isPresent()) {

            CharacterEntity characterEntity = optionalCharacter.get();

            characterRepository.delete(characterEntity);
        } else {
            throw new Exception();
        }
    }

    @Override
    public Character updateCharacter(UUID uuid, Character character) {

        Optional<CharacterEntity> characterEntityOptional = characterRepository.findById(uuid);

        if (characterEntityOptional.isPresent()) {

            CharacterEntity characterEntity = CharacterEntity.builder()
                    .id(uuid)
                    .hanzi(character.getHanzi())
                    .pinyin(character.getPinyin())
                    .meaning(character.getMeaning())
                    .tone(character.getTone())
                    .prop(character.isProp())
                    .build();

            characterEntity = characterRepository.save(characterEntity);

            return Character.builder()
                    .id(characterEntity.getId())
                    .hanzi(character.getHanzi())
                    .pinyin(character.getPinyin())
                    .meaning(character.getMeaning())
                    .tone(character.getTone())
                    .prop(character.isProp())
                    .build();
        } else {
            return null;
        }
    }

    public ActorEntity getActorFromCharacter(CharacterEntity characterEntity) {

        if (characterEntity.getPinyin().length() == 1) {
            return actorRepository.findActorEntityByAssociatedPinyinSound("void").orElse(null);
        }

        List<ActorEntity> actorEntityList = actorRepository.findAll();
        List<String> actorSounds = actorRepository.findAll().stream().map(ActorEntity::getAssociatedPinyinSound)
                .toList();

        String pinyin = characterEntity.getPinyin();
        String pinyinSubstring = "";

        if (pinyin.length() >= 3 && actorSounds.contains(pinyin.substring(0, 3))) {
            pinyinSubstring = pinyin.substring(0, 3);
        } else if (pinyin.length() >= 2 && actorSounds.contains(pinyin.substring(0, 2))) {
            pinyinSubstring = pinyin.substring(0, 2);
        } else if (actorSounds.contains(pinyin.substring(0, 1))) {
            pinyinSubstring = pinyin.substring(0, 1);
        }

        String finalPinyinSubstring = pinyinSubstring;
        return actorEntityList.stream()
                .filter(actorEntity -> actorEntity.getAssociatedPinyinSound().equals(finalPinyinSubstring))
                .findFirst().orElse(null);
    }

    public int getActorLengthFromCharacter(CharacterEntity characterEntity) {

        List<String> actorSounds = actorRepository.findAll().stream().map(ActorEntity::getAssociatedPinyinSound).toList();
        String pinyin = characterEntity.getPinyin();

        if (pinyin.length() >= 3 && actorSounds.contains(pinyin.substring(0, 3))) {
            return 3;
        } else if (pinyin.length() >= 2 && actorSounds.contains(pinyin.substring(0, 2))) {
            return 2;
        } else if (actorSounds.contains(pinyin.substring(0, 1)) || pinyin.length() == 1) {
            return 1;
        }

        return -1;
    }

    public LocationEntity getLocationFromCharacter(CharacterEntity characterEntity) {

//        -a, -o, -e, -ai, -ei, -ao, -ou, -an, -ang, -(e)n, -(e)ng, -ong & Ø Null

        int lengthActor = getActorLengthFromCharacter(characterEntity);

        if (characterEntity.getPinyin().length() == 1) {
            return locationRepository.findLocationEntityByAssociatedPinyinSound("void").orElse(null);
        }

        String locationSubstring = characterEntity.getPinyin().substring(lengthActor);
        List<LocationEntity> locationEntityList = locationRepository.findAll();
        return locationEntityList.stream()
                .filter(locationEntity -> locationEntity.getAssociatedPinyinSound().equals(locationSubstring))
                .findFirst().orElse(null);
    }
    
    public RoomEntity getRoomFromCharacter(CharacterEntity characterEntity) {

        List<RoomEntity> roomEntityList = roomRepository.findAll();
        return roomEntityList.stream()
                .filter(roomEntity -> roomEntity.getTone().equals(characterEntity.getTone()))
                .findFirst().orElse(null);
    }
}
