package com.example.mbbackend.entity.character;

import com.example.mbbackend.config.CharacterTone;
import com.example.mbbackend.entity.movie.MovieEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "character")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CharacterEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private UUID id;
    @Column(name = "hanzi")
    private String hanzi;
    @Column(name = "pinyin")
    private String pinyin;
    @Column(name = "meaning")
    private String meaning;
    @Enumerated(EnumType.ORDINAL)
    @Column(name = "tone")
    private CharacterTone tone;
    @Column(name = "prop")
    private Boolean prop;

    @OneToMany(mappedBy = "character", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<MovieEntity> movie;
}
