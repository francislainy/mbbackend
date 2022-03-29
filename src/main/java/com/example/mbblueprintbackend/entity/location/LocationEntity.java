package com.example.mbblueprintbackend.entity.location;

import com.example.mbblueprintbackend.entity.movie.MovieEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "location")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LocationEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private UUID id;
    @Column(name = "title")
    private String title;
    @Column(name = "associatedPinyinSound")
    private String associatedPinyinSound;

    @OneToMany(mappedBy = "location")
    private Set<MovieEntity> movie;
}
