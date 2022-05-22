package com.example.mbbackend.repository.movie;

import com.example.mbbackend.entity.movie.MovieEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface MovieRepository extends JpaRepository<MovieEntity, UUID> {

    String queryMoviesByActorId = "SELECT * FROM movie WHERE actor_id = :actorId ORDER BY id";
    String queryMoviesByCharacterId = "SELECT * FROM movie WHERE character_id = :characterId ORDER BY id";

    @Query(value = queryMoviesByActorId, nativeQuery = true)
    List<MovieEntity> findMoviesByActorId(UUID actorId);

    @Query(value = queryMoviesByCharacterId, nativeQuery = true)
    List<MovieEntity> findMoviesByCharacterId(UUID characterId);
}
