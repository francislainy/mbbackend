package com.example.mbbackend.pact.consumer.movie;

import au.com.dius.pact.consumer.dsl.DslPart;
import au.com.dius.pact.consumer.dsl.PactDslJsonBody;
import au.com.dius.pact.consumer.dsl.PactDslWithProvider;
import au.com.dius.pact.consumer.junit5.PactConsumerTestExt;
import au.com.dius.pact.consumer.junit5.PactTestFor;
import au.com.dius.pact.core.model.PactSpecVersion;
import au.com.dius.pact.core.model.RequestResponsePact;
import au.com.dius.pact.core.model.annotations.Pact;
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
class GetMoviesIT {

    Map<String, String> headers = new HashMap<>();

    String path = "/api/mb/movie";

    @Pact(provider = PACT_PROVIDER, consumer = PACT_CONSUMER)
    public RequestResponsePact createPact(PactDslWithProvider builder) {

        headers.put("Content-Type", "application/json");

        DslPart bodyReturned = new PactDslJsonBody()
                .eachLike("movies", 100)
                    .uuid("id", "1bfff94a-b70e-4b39-bd2a-be1c0f898589")
                    .stringType("scene", "Kanye West talking to Shakira outside the front entrance")
                    .stringType("imageUrl", "anyUrl")
                .object("character")
                    .uuid("id", "1bfff94a-b70e-4b39-bd2a-be1c0f898589")
                    .stringType("hanzi", "西")
                    .stringType("pinyin", "xi")
                    .stringType("meaning", "West")
                    .stringType("tone", "FIRST")
                .closeObject()
                .object("location")
                    .uuid("id", "1bfff94a-b70e-4b39-bd2a-be1c0f898589")
                    .stringType("title", "Childhood home")
                    .stringType("associatedPinyinSound", "null")
                .closeObject()
                .object("actor")
                    .uuid("id", "1bfff94a-b70e-4b39-bd2a-be1c0f898589")
                    .stringType("name", "Shakira")
                .closeObject()
                .object("room")
                    .uuid("id", "1bfff94a-b70e-4b39-bd2a-be1c0f898589")
                    .stringType("title", "Bedroom")
                .closeObject()
                .closeArray()
                .close();

        return builder
                .given("A request to retrieve a list of movies")
                .uponReceiving("A request to retrieve a list of movies")
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
        RequestSpecification rq = Utils.getRequestSpecification().baseUri(MOCK_PACT_URL).headers(headers);

        Response response = rq.get(path);

        assertEquals(200, response.getStatusCode());
    }

}
