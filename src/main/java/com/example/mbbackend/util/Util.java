package com.example.mbbackend.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Util {

    public static String jsonStringFromObject(Object jsonObject) {
        if (jsonObject == null) {
            return "";
        }

        ObjectMapper mapper = new ObjectMapper();
        String jsonStr;
        try {
            jsonStr = mapper.writeValueAsString(jsonObject);
        } catch (JsonProcessingException e) {
            log.error(e.toString());
            throw new RuntimeException(e.getMessage()); // NOSONAR
        }
        return jsonStr;
    }

}
