package com.example.mbblueprintbackend.entity.actor;

import com.example.mbblueprintbackend.entity.movie.MovieEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "actor")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ActorEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private UUID id;
    @Column(name = "name")
    private String name;
    @Column(name = "associatedPinyinSound")
    private String associatedPinyinSound;
    @Column(name = "family")
    private String family;
    @Column(name = "imageUrl")
    private String imageUrl;

    @OneToMany(mappedBy = "actor")
    private Set<MovieEntity> movie;
}
