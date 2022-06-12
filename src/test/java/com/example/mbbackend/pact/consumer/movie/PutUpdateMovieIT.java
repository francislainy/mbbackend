package com.example.mbbackend.pact.consumer.movie;

import au.com.dius.pact.consumer.dsl.DslPart;
import au.com.dius.pact.consumer.dsl.PactDslJsonBody;
import au.com.dius.pact.consumer.dsl.PactDslWithProvider;
import au.com.dius.pact.consumer.junit5.PactConsumerTestExt;
import au.com.dius.pact.consumer.junit5.PactTestFor;
import au.com.dius.pact.core.model.PactSpecVersion;
import au.com.dius.pact.core.model.RequestResponsePact;
import au.com.dius.pact.core.model.annotations.Pact;
import com.example.mbbackend.model.Character;
import com.example.mbbackend.model.*;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static com.example.mbbackend.config.Constants.*;
import static com.example.mbbackend.util.Utils.getRequestSpecification;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * mvn -Dtest=com.example.mbbackend.pact.consumer.*.*IT integration-test
 */

@ExtendWith(PactConsumerTestExt.class)
class PutUpdateMovieIT {

    Map<String, String> headers = new HashMap<>();

    String path = "/api/mb/movie/";

    @Pact(provider = PACT_PROVIDER, consumer = PACT_CONSUMER)
    public RequestResponsePact createPact(PactDslWithProvider builder) {

        headers.put("Content-Type", "application/json");

        DslPart bodyGiven = new PactDslJsonBody()
                .stringType("scene", "Shakira talking to Kanye West outside the front entrance")
                .stringType("imageUrl", "anyUrl")
                .object("character")
                .uuid("id", "1bfff94a-b70e-4b39-bd2a-be1c0f898589")
                .closeObject()
                .object("location")
                .uuid("id", "1bfff94a-b70e-4b39-bd2a-be1c0f898589")
                .closeObject()
                .object("actor")
                .uuid("id", "1bfff94a-b70e-4b39-bd2a-be1c0f898589")
                .closeObject()
                .object("room")
                .uuid("id", "1bfff94a-b70e-4b39-bd2a-be1c0f898589")
                .closeObject()
                .close();

        DslPart bodyReturned = new PactDslJsonBody()
                .uuid("id", "2cfff94a-b70e-4b39-bd2a-be1c0f898589")
                .stringType("scene", "Kanye West talking to Shakira outside the front entrancehdjbfdjhjfgbshjfgfjhgbdj hjsgfhdfghfgdsh")
                .stringType("imageUrl", "anyUrl")
                .object("character")
                .uuid("id", "2dfff94a-b70e-4b39-bd2a-be1c0f898589")
                .closeObject()
                .object("location")
                .uuid("id", "1bfff94a-b70e-4b39-bd2a-be1c0f898589")
                .closeObject()
                .object("actor")
                .uuid("id", "4efff94a-b70e-4b39-bd2a-be1c0f898589")
                .closeObject()
                .object("room")
                .uuid("id", "3afff94a-b70e-4b39-bd2a-be1c0f898589")
                .closeObject()
                .close();

        return builder
                .given("A request to update a movie")
                .uponReceiving("A request to update a movie")
                .pathFromProviderState(path + "${movieId}", path + "1bfff94a-b70e-4b39-bd2a-be1c0f898589")
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

        Actor actor = Actor.builder().id(UUID.fromString("4efff94a-b70e-4b39-bd2a-be1c0f898589")).build();
        Room room = Room.builder().id(UUID.fromString("3afff94a-b70e-4b39-bd2a-be1c0f898589")).build();
        Location location = Location.builder().id(UUID.fromString("1bfff94a-b70e-4b39-bd2a-be1c0f898589")).build();
        Character character = Character.builder().id(UUID.fromString("2dfff94a-b70e-4b39-bd2a-be1c0f898589")).build();

        Movie movie = Movie.builder()
                .actor(actor)
                .character(character)
                .room(room)
                .location(location)
                .scene("Shakira talking to Kanye West outside the front entrance")
                .imageUrl("anyUrl")
                .build();

        //Mock url
        RequestSpecification rq = getRequestSpecification().body(movie).baseUri(MOCK_PACT_URL).headers(headers);

        Response response = rq.put(path + "1bfff94a-b70e-4b39-bd2a-be1c0f898589");

        assertEquals(200, response.getStatusCode());
    }

}
