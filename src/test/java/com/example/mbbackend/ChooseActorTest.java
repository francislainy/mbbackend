package com.example.mbbackend;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.HashMap;

class ChooseActorTest {

    @Test
    void chooseActor() {

        String[] gods = new String[]{"yu", "nu", "lu", "ju", "qu", "xu"};
        String[] female = new String[]{"y", "bi", "pi", "mi", "di", "ti", "ni", "li", "ji", "qi", "xi"};
        String[] fictional = new String[]{"w", "bu", "pu", "mu", "fu", "du", "tu", "nu", "lu", "gu", "ku", "hu", "zhu", "chu", "shu", "ru", "zu", "cu", "su"};
        String[] male = new String[]{"b", "p", "m", "f", "d", "t", "n", "l", "g", "k", "zh", "ch", "sh", "r", "z", "c", "s", "zhi", "chi", "shi", "ri", "zi", "ci", "si"};

        String[] setLocations = new String[]{"a", "o", "e", "ai", "ei", "ao", "ou", "an", "ang", "en", "eng", "ong", ""};
        HashMap<String, String> map = new HashMap<>();
        map.put("a", "Amie's house");
        map.put("o", "Hospital");
        map.put("e", "Escola");
        map.put("ai", "Lonnier's house");
        map.put("ei", "Adailton's house");
        map.put("ao", "Beck's house");
        map.put("ou", "Tiff's house");
        map.put("an", "Aunt's house");
        map.put("ang", "England");
        map.put("en", "Empresa");
        map.put("eng", "Engineering college");
        map.put("ong", "Gym");
        map.put("null", "Childhood home");

        String pinyin = "er";
        String pinyinToCheck1 = pinyin.substring(0, 1);
        String pinyinToCheck2 = pinyin.substring(0, 2);

        if (Arrays.asList(gods).contains(pinyinToCheck1) || Arrays.asList(gods).contains(pinyinToCheck2)) {
            System.out.println("god");
        } else if (Arrays.asList(female).contains(pinyinToCheck1) || Arrays.asList(female).contains(pinyinToCheck2)) {
            System.out.println("female");
        } else if (Arrays.asList(fictional).contains(pinyinToCheck1) || Arrays.asList(fictional).contains(pinyinToCheck2)) {
            System.out.println("fictional");
        } else if (Arrays.asList(male).contains(pinyinToCheck1) || Arrays.asList(male).contains(pinyinToCheck2)) {
            System.out.println("male actor");
        } else {
            System.out.println("no actor - Jack Chan");
        }

        if (pinyin.length() > 2) {
            String pinyinEnd1 = pinyin.substring(pinyin.length() - 1);
            String pinyinEnd2 = pinyin.substring(pinyin.length() - 2);
            String pinyinEnd3 = pinyin.substring(pinyin.length() - 3);

            if (Arrays.asList(setLocations).contains(pinyinEnd1)) {

                String setLocation = map.get(pinyinEnd1);

                System.out.println(setLocation);
            } else if (Arrays.asList(setLocations).contains(pinyinEnd2)) {

                String setLocation = map.get(pinyinEnd2);

                System.out.println(setLocation);
            } else if (Arrays.asList(setLocations).contains(pinyinEnd3)) {

                String setLocation = map.get(pinyinEnd3);

                System.out.println(setLocation);
            }
        }
        else {
            System.out.println(map.get("null"));
        }


    }
}
