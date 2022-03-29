package com.example.mbblueprintbackend.entity.movie;

import com.example.mbblueprintbackend.entity.actor.ActorEntity;
import com.example.mbblueprintbackend.entity.character.CharacterEntity;
import com.example.mbblueprintbackend.entity.location.LocationEntity;
import com.example.mbblueprintbackend.entity.room.RoomEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Table(name = "movie")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MovieEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private UUID id;
    @Column(name = "scene")
    private String scene;
    @Column(name = "imageUrl")
    private String imageUrl;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    @JoinColumn(name = "character_id", referencedColumnName = "id")
    private CharacterEntity character;
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    @JoinColumn(name = "location_id", referencedColumnName = "id")
    private LocationEntity location;
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    @JoinColumn(name = "actor_id", referencedColumnName = "id")
    private ActorEntity actor;
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    @JoinColumn(name = "room_id", referencedColumnName = "id")
    private RoomEntity room;
}
