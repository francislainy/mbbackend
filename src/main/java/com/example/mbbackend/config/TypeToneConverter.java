package com.example.mbbackend.config;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.util.List;

@Converter(autoApply = true)
public class TypeToneConverter implements AttributeConverter<CharacterTone, Integer> {

    @Override
    public Integer convertToDatabaseColumn(CharacterTone tone) {
        if (tone == null) {
            return null;
        }
        return tone.getTone();
    }

    @Override
    public CharacterTone convertToEntityAttribute(Integer tone) {
        if (tone == null) {
            return null;
        }

        int i = 0;
        List<CharacterTone> list = List.of(CharacterTone.values());
        for (CharacterTone characterTone : list) {
//            if (characterTone.getTone().equals(tone+1)) {
            if (characterTone.getTone().equals(tone)) {
                return list.get(i);
            }
            i++;
        }

        return null;
    }
}
        
//        CharacterTone characterTone = Stream
//                .of(CharacterTone.values())
//                .filter(c -> c.getTone().equals(tone))
//                .findFirst()
//                .orElseThrow(IllegalArgumentException::new);

//        return characterTone;
//    }
//}
