package com.example.mbblueprintbackend.entity.character;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
    @Column(name = "character")
    private String character;
    @Column(name = "pinyin")
    private String pinyin;
    @Column(name = "meaning")
    private String meaning;

}