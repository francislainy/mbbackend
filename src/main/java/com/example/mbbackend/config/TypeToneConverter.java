package com.example.mbbackend.config;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.util.stream.Stream;

@Converter(autoApply = true)
public class TypeToneConverter implements AttributeConverter<CharacterTone, String> {

    @Override
    public String convertToDatabaseColumn(CharacterTone tone) {
        if (tone == null) {
            return null;
        }
        return tone.getTone();
    }

    @Override
    public CharacterTone convertToEntityAttribute(String tone) {
        if (tone == null) {
            return null;
        }

        return Stream
                .of(CharacterTone.values())
                .filter(c -> c.getTone().equals(tone))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }
}
