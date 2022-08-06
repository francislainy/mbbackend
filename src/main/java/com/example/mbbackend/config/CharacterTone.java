package com.example.mbbackend.config;

import lombok.Getter;

@Getter
public enum CharacterTone {

    FIRST("1"), SECOND("2"), THIRD("3"), FOURTH("4"), FIFTH("5");

    private final String tone;

    CharacterTone(String tone) {
        this.tone = tone;
    }
}
