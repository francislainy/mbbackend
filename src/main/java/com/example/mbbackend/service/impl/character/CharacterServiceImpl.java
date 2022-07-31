package com.example.mbbackend.service.impl.character;

import com.example.mbbackend.entity.actor.ActorEntity;
import com.example.mbbackend.entity.character.CharacterEntity;
import com.example.mbbackend.entity.movie.MovieEntity;
import com.example.mbbackend.model.Actor;
import com.example.mbbackend.model.Character;
import com.example.mbbackend.model.Movie;
import com.example.mbbackend.repository.actor.ActorRepository;
import com.example.mbbackend.repository.character.CharacterRepository;
import com.example.mbbackend.repository.movie.MovieRepository;
import com.example.mbbackend.service.character.CharacterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class CharacterServiceImpl implements CharacterService {

    @Autowired
    CharacterRepository characterRepository;

    @Autowired
    MovieRepository movieRepository;

    @Autowired
    ActorRepository actorRepository;

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

        List<String> threeCharacters = new ArrayList<>();
        List<String> twoCharacters = new ArrayList<>();
        List<String> oneCharacter = new ArrayList<>();

        if (characterEntity.getPinyin().length() == 1) {
            return actorRepository.findActorEntityByAssociatedPinyinSound("void").orElse(null);
        }

        List<ActorEntity> actorEntityList = actorRepository.findAll();

        for (ActorEntity actorEntity : actorEntityList) {

            if (actorEntity.getAssociatedPinyinSound().length() >= 3) {
                threeCharacters.add(actorEntity.getAssociatedPinyinSound());
            } else if (actorEntity.getAssociatedPinyinSound().length() >= 2) {
                twoCharacters.add(actorEntity.getAssociatedPinyinSound());
            } else if (actorEntity.getAssociatedPinyinSound().length() >= 1) {
                oneCharacter.add(actorEntity.getAssociatedPinyinSound());
            }
        }
        
        String pinyin = characterEntity.getPinyin();
        String pinyinSubstring = "";

        if (pinyin.length() >= 3 && threeCharacters.contains(pinyin.substring(0, 3))) {
            pinyinSubstring = pinyin.substring(0, 3);
        } else if (pinyin.length() >= 2 && twoCharacters.contains(pinyin.substring(0, 2))) {
            pinyinSubstring = pinyin.substring(0, 2);
        } else if (oneCharacter.contains(pinyin.substring(0, 1))) {
            pinyinSubstring = pinyin.substring(0, 1);
        }

        for (ActorEntity actor : actorEntityList) {
            if (actor.getAssociatedPinyinSound().equals(pinyinSubstring)) {
                return actor;
            }
        }

        return null;
    }

}
