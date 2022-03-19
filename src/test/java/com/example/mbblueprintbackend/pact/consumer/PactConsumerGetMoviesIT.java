package com.example.mbblueprintbackend.pact.consumer;

import au.com.dius.pact.consumer.dsl.DslPart;
import au.com.dius.pact.consumer.dsl.PactDslJsonBody;
import au.com.dius.pact.consumer.dsl.PactDslWithProvider;
import au.com.dius.pact.consumer.junit5.PactConsumerTestExt;
import au.com.dius.pact.consumer.junit5.PactTestFor;
import au.com.dius.pact.core.model.PactSpecVersion;
import au.com.dius.pact.core.model.RequestResponsePact;
import au.com.dius.pact.core.model.annotations.Pact;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.HashMap;
import java.util.Map;

import static com.example.mbblueprintbackend.config.Constants.*;
import static com.example.mbblueprintbackend.util.Util.getRequestSpecification;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * As per https://developers.google.com/classroom/reference/rest
 * <p>
 * mvn -Dtest=com.hmhco.viaductservice.pact.consumer.*IT integration-test
 */

@ExtendWith(PactConsumerTestExt.class)
class PactConsumerGetMoviesIT {

    Map<String, String> headers = new HashMap<>();

    String path = "/api/mb/movie";

    @Pact(provider = PACT_PROVIDER, consumer = PACT_CONSUMER)
    public RequestResponsePact createPact(PactDslWithProvider builder) {

        headers.put("Content-Type", "application/json");

        DslPart bodyReturned = new PactDslJsonBody()
                .eachLike("movies", 5)
                    .stringType("pinyin", "xī")
                    .stringType("character", "西")
                    .stringType("meaning", "West")
                    .stringType("scene", "Kanye West talking to Shakira outside the front entrance")
                    .stringType("imageUrl", "anyUrl")
                .object("setLocation")
                    .uuid("id", "1bfff94a-b70e-4b39-bd2a-be1c0f898589")
                    .stringType("title", "Childhood home")
                .closeObject()
                .object("actor")
                    .stringType("id", "1bfff94a-b70e-4b39-bd2a-be1c0f898589")
                    .stringType("name", "Shakira")
                .closeObject()
                .object("room")
                    .stringType("id", "1bfff94a-b70e-4b39-bd2a-be1c0f898589")
                    .stringType("title", "Bedroom")
                .closeObject()
                .closeArray()
                .close();

        return builder
                .given("A request to retrieve a list of courses")
                .uponReceiving("A request to retrieve a list of courses")
                .path(path)
                .method("GET")
                .headers(headers)
                .willRespondWith()
                .status(200)
                .body(bodyReturned)
                .toPact();
    }


    @Test
    @PactTestFor(providerName = PACT_PROVIDER, port = PACT_PORT, pactVersion = PactSpecVersion.V3)
    void runTest() {

        //Mock url
        RequestSpecification rq = getRequestSpecification().baseUri(MOCK_PACT_URL).headers(headers);

        Response response = rq.get(path);

        assertEquals(200, response.getStatusCode());
    }

}
