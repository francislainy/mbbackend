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
import com.example.mbbackend.util.Utils;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.HashMap;
import java.util.Map;

import static com.example.mbbackend.config.Constants.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * mvn -Dtest=com.example.mbbackend.pact.consumer.*IT integration-test
 */

@ExtendWith(PactConsumerTestExt.class)
class PutUpdateCharacterIT {

    Map<String, String> headers = new HashMap<>();

    String path = "/api/mb/character/";

    @Pact(provider = PACT_PROVIDER, consumer = PACT_CONSUMER)
    public RequestResponsePact createPact(PactDslWithProvider builder) {

        headers.put("Content-Type", "application/json");

        DslPart bodyGiven = new PactDslJsonBody()
                .stringType("hanzi", "西")
                .stringType("pinyin", "xi")
                .stringType("meaning", "West")
                .stringType("tone", "FIRST")
                .close();

        DslPart bodyReturned = new PactDslJsonBody()
                .uuid("id", "2cfff94a-b70e-4b39-bd2a-be1c0f898589")
                .stringType("hanzi", "西")
                .stringType("pinyin", "xi")
                .stringType("meaning", "West")
                .stringType("tone", "FIRST")
                .close();

        return builder
                .given("A request to update a character")
                .uponReceiving("A request to update a character")
                .pathFromProviderState(path + "${characterId}", path + "1bfff94a-b70e-4b39-bd2a-be1c0f898589")
                .body(bodyGiven)
                .method("PUT")
                .headers(headers)
                .willRespondWith()
                .status(200)
                .body(bodyReturned)
                .toPact();
    }

    @Test
    @PactTestFor(providerName = PACT_PROVIDER, port = PACT_PORT, pactVersion = PactSpecVersion.V3)
    void runTest() {

        Character character = Character.builder()
                .hanzi("西")
                .pinyin("xi")
                .meaning("West")
                .tone(CharacterTone.FIFTY)
                .build();

        String json = Utils.jsonStringFromObject(character);

        //Mock url
        RequestSpecification rq = Utils.getRequestSpecification().body(json).baseUri(MOCK_PACT_URL).headers(headers);

        Response response = rq.put(path + "1bfff94a-b70e-4b39-bd2a-be1c0f898589");

        assertEquals(200, response.getStatusCode());
    }

}
