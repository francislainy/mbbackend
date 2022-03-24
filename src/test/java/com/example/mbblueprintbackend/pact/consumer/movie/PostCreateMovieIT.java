package com.example.mbblueprintbackend.pact.consumer.movie;

import au.com.dius.pact.consumer.dsl.DslPart;
import au.com.dius.pact.consumer.dsl.PactDslJsonBody;
import au.com.dius.pact.consumer.dsl.PactDslWithProvider;
import au.com.dius.pact.consumer.junit5.PactConsumerTestExt;
import au.com.dius.pact.consumer.junit5.PactTestFor;
import au.com.dius.pact.core.model.PactSpecVersion;
import au.com.dius.pact.core.model.RequestResponsePact;
import au.com.dius.pact.core.model.annotations.Pact;
import com.example.mbblueprintbackend.model.Actor;
import com.example.mbblueprintbackend.model.Location;
import com.example.mbblueprintbackend.model.Movie;
import com.example.mbblueprintbackend.model.Room;
import com.example.mbblueprintbackend.util.Utils;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static com.example.mbblueprintbackend.config.Constants.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * As per https://developers.google.com/classroom/reference/rest
 * <p>
 * mvn -Dtest=com.hmhco.viaductservice.pact.consumer.*IT integration-test
 */

@ExtendWith(PactConsumerTestExt.class)
class PostCreateMovieIT {

    Map<String, String> headers = new HashMap<>();

    String path = "/api/mb/movie/";

    @Pact(provider = PACT_PROVIDER, consumer = PACT_CONSUMER)
    public RequestResponsePact createPact(PactDslWithProvider builder) {

        headers.put("Content-Type", "application/json");

        DslPart bodyGiven = new PactDslJsonBody()
                .stringType("pinyin", "xī")
                .stringType("character", "西")
                .stringType("meaning", "anyMeaning")
                .stringType("scene", "Shakira talking to Kanye West outside the front entrance")
                .stringType("imageUrl", "anyUrl")
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

        DslPart bodyReturned = new PactDslJsonBody()
                .uuid("id", "2cfff94a-b70e-4b39-bd2a-be1c0f898589")
                .stringType("pinyin", "xī")
                .stringType("character", "西")
                .stringType("meaning", "anyMeaning")
                .stringType("scene", "Shakira talking to Kanye West outside the front entrance")
                .stringType("imageUrl", "anyUrl")
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
                .given("A request to create a movie")
                .uponReceiving("A request to create a movie")
                .path(path)
                .body(bodyGiven)
                .method("POST")
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

        Movie movie = Movie.builder()
                .actor(actor)
                .character("西")
                .imageUrl("anyUrl")
                .meaning("anyMeaning")
                .pinyin("xī")
                .room(room)
                .location(location)
                .scene("Shakira talking to Kanye West outside the front entrance")
                .build();

        //Mock url
        RequestSpecification rq = Utils.getRequestSpecification().body(movie).baseUri(MOCK_PACT_URL).headers(headers);

        Response response = rq.post(path);

        assertEquals(200, response.getStatusCode());
    }

}
