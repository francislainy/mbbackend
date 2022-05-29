package com.example.mbbackend.repository.movie;

import com.example.mbbackend.entity.movie.MovieEntity;
import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public class MovieCustomRepository {

    private final EntityManager em;

    public MovieCustomRepository(EntityManager em) {
        this.em = em;
    }

    public List<MovieEntity> find(UUID id, String scene, UUID actorId) {

        String query = "select M from MovieEntity as M";
        String condition = " where";

        if (id != null) {
            query += condition + " M.id = :id";
            condition = " and ";
        }

        if (scene != null) {
            query += condition + " M.scene =:scene";
        }

        if (actorId != null) {
            query += condition + " M.actor.id =:actorId";
        }

        var q = em.createQuery(query, MovieEntity.class);

        if (id != null) {
            q.setParameter("id", id);
        }

        if (scene != null) {
            q.setParameter("scene", scene);
        }

        if (actorId != null) {
            q.setParameter("actorId", actorId);
        }


        return q.getResultList();
    }
}
