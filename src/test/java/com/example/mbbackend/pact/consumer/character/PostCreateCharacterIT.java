package com.example.mbbackend.pact.consumer.character;

import au.com.dius.pact.consumer.dsl.DslPart;
import au.com.dius.pact.consumer.dsl.PactDslJsonBody;
import au.com.dius.pact.consumer.dsl.PactDslWithProvider;
import au.com.dius.pact.consumer.junit5.PactConsumerTestExt;
import au.com.dius.pact.consumer.junit5.PactTestFor;
import au.com.dius.pact.core.model.PactSpecVersion;
import au.com.dius.pact.core.model.RequestResponsePact;
import au.com.dius.pact.core.model.annotations.Pact;
import com.example.mbbackend.config.CharacterTone;
import com.example.mbbackend.model.Character;
import com.example.mbbackend.model.Movie;
import com.example.mbbackend.util.Util;
import com.example.mbbackend.util.Utils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.HashMap;
import java.util.Map;

import static com.example.mbbackend.config.Constants.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * mvn -Dtest=com.example.mbbackend.pact.consumer.*.*IT integration-test
 */

@ExtendWith(PactConsumerTestExt.class)
class PostCreateCharacterIT {

    Map<String, String> headers = new HashMap<>();

    String path = "/api/mb/character/";

    @Pact(provider = PACT_PROVIDER, consumer = PACT_CONSUMER)
    public RequestResponsePact createPact(PactDslWithProvider builder) {

        headers.put("Content-Type", "application/json");

        // @formatter:off
        DslPart bodyGiven = new PactDslJsonBody()
                .stringType("hanzi", "uniqueFromPact")
                .stringType("pinyin", "xi")
                .stringType("meaning", "West")
                .stringType("tone", "FIRST")
                .booleanType("prop", true)
                .close();
        
        DslPart bodyReturned = new PactDslJsonBody()
                .stringType("hanzi", "uniqueFromPact")
                .stringType("pinyin", "xi")
                .stringType("meaning", "West")
                .stringType("tone", "FIRST")
                .booleanType("prop", true)
                .object("movie")
                .uuid("id", "1bfff94a-b70e-4b39-bd2a-be1c0f898589")
                .booleanType("isSuggestedMovie", true)
                    .object("location")
                    .uuid("id", "1bfff94a-b70e-4b39-bd2a-be1c0f898589")
                    .stringType("title", "Childhood home")
                    .closeObject()
                    .object("actor")
                    .uuid("id", "1bfff94a-b70e-4b39-bd2a-be1c0f898589")
                    .stringType("title", "Shakira")
                    .closeObject()
                    .object("room")
                    .uuid("id", "1bfff94a-b70e-4b39-bd2a-be1c0f898589")
                    .stringType("title", "bedroom")
                    .closeObject()
                .closeObject()
                .close();
        // @formatter:on

        return builder
                .given("A request to create a character")
                .uponReceiving("A request to create a character")
                .path(path)
                .body(bodyGiven)
                .method("POST")
                .headers(headers)
                .willRespondWith()
                .status(201)
                .body(bodyReturned)
                .toPact();
    }

    @Test
    @PactTestFor(providerName = PACT_PROVIDER, port = PACT_PORT, pactVersion = PactSpecVersion.V3)
    void runTest() {

        Character character = Character.builder()
                .hanzi("uniqueFromPact")
                .pinyin("xi")
                .meaning("West")
                .tone(CharacterTone.FIRST)
                .prop(true)
                .build();

        String json = Utils.jsonStringFromObject(character);

        //Mock url
        RequestSpecification rq = Utils.getRequestSpecification().body(json).baseUri(MOCK_PACT_URL).headers(headers);

        Response response = rq.post(path);

        assertEquals(201, response.getStatusCode());
    }

}
