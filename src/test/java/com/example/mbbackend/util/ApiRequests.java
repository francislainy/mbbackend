package com.example.mbbackend.util;

import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ApiRequests {

    @Test
    public void getCharacters() {

        RequestSpecification rq = Utils.getRequestSpecification();

        Response response = rq.get("http://localhost:8081/api/mb/character");
        assertEquals(200, response.statusCode());

    }

}
