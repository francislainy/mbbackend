package com.example.mbbackend.entity.actor;

import com.example.mbbackend.config.ActorFamily;
import com.example.mbbackend.entity.movie.MovieEntity;
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
    @Enumerated(EnumType.ORDINAL)
    @Column(name = "family")
    private ActorFamily family;
    @Column(name = "imageUrl")
    private String imageUrl;

    @OneToMany(mappedBy = "actor", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = false)
    private Set<MovieEntity> movie;
}
