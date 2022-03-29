package com.example.mbblueprintbackend.entity.room;

import com.example.mbblueprintbackend.entity.movie.MovieEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "room")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RoomEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private UUID id;
    @Column(name = "title")
    private String title;

    @OneToMany(mappedBy = "room")
    private Set<MovieEntity> movie;
}
