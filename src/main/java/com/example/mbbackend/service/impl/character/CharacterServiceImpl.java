package com.example.mbbackend.service.impl.character;

import com.example.mbbackend.entity.character.CharacterEntity;
import com.example.mbbackend.entity.movie.MovieEntity;
import com.example.mbbackend.model.Character;
import com.example.mbbackend.model.Movie;
import com.example.mbbackend.repository.character.CharacterRepository;
import com.example.mbbackend.repository.movie.MovieRepository;
import com.example.mbbackend.service.character.CharacterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class CharacterServiceImpl implements CharacterService {

    @Autowired
    CharacterRepository characterRepository;

    @Autowired
    MovieRepository movieRepository;

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
                            .prop(characterEntity.getProp())
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
                    .prop(characterEntity.getProp())
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
                .actor(null)
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
                .prop(characterEntity.getProp())
                .movie(Movie.builder()
                        .id(movieEntity.getId())
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

}
