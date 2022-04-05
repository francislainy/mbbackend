package com.example.mbbackend.pact.provider;

import au.com.dius.pact.provider.junit5.HttpTestTarget;
import au.com.dius.pact.provider.junit5.PactVerificationContext;
import au.com.dius.pact.provider.junit5.PactVerificationInvocationContextProvider;
import au.com.dius.pact.provider.junitsupport.Consumer;
import au.com.dius.pact.provider.junitsupport.Provider;
import au.com.dius.pact.provider.junitsupport.State;
import au.com.dius.pact.provider.junitsupport.VerificationReports;
import au.com.dius.pact.provider.junitsupport.loader.PactFolder;
import org.apache.hc.core5.http.HttpRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestTemplate;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static com.example.mbbackend.config.Constants.*;
import static com.example.mbbackend.util.Utils.logCurlFromPact;

/**
 * As per https://developers.google.com/classroom/reference/rest
 * And
 * https://hapi.int.hmhco.com/edcore/viaduct-service/swagger-ui/index.html?url=/edcore/viaduct-service/v3/api-docs
 * <p>
 * mvn -Dtest=com.hmhco.viaductservice.pact.provider.*EdIT integration-test -DtestEnvt=int -Dpactbroker.auth.token=Ziqryvt7vncQKSo8dHOkDg
 */

/* ✨👇 Uncomment this and comment @PactBroker to test locally by pasting a .json file for the contract under the target/pacts folder (use import au.com.dius.pact.provider.junit.loader.PactFolder;) ✨*/
@PactFolder("target/pacts")

@Provider(PACT_PROVIDER)
@Consumer(PACT_CONSUMER)
//@PactBroker(url = BROKER_PACT_URL, authentication = @PactBrokerAuth(token = "${pactbroker.auth.token}"))
@VerificationReports(value = {"markdown"}, reportDir = "target/pacts")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ProviderIT {

    @LocalServerPort
    int port;

    @TestTemplate
    @ExtendWith(PactVerificationInvocationContextProvider.class)
    void pactTestTemplate(PactVerificationContext context, HttpRequest request) {
//        request.addHeader("Authorization", getAuthorizationToken());

        logCurlFromPact(context, request, BASE_URI_LOCAL);

        context.verifyInteraction();
    }

    @BeforeEach
    void before(PactVerificationContext context) {

        context.setTarget(new HttpTestTarget(BASE_PACT_URL_LOCAL, port, ""));
    }

    @State("A request to retrieve a list of movies")
    void getMovies() {

    }

    @State("A request to retrieve a movie")
    Map<String, Object> getMovie() {
        Map<String, Object> map = new HashMap<>();
        map.put("movieId", UUID.fromString("6f6d4899-297f-4a02-b646-105a4208fe94"));
        return map;
    }

    @State("A request to create a movie")
    void createMovie() {

    }

    @State("A request to update a movie")
    Map<String, Object> updateMovie() {
        Map<String, Object> map = new HashMap<>();
        map.put("movieId", UUID.fromString("6f6d4899-297f-4a02-b646-105a4208fe94"));
        return map;
    }

    @State("A request to delete a movie")
    Map<String, Object> deleteMovie() {
        Map<String, Object> map = new HashMap<>();
        map.put("movieId", UUID.fromString("bdad926e-2db7-43b9-8b32-3650c7af5ced"));
        return map;
    }

    @State("A request to retrieve a list of locations")
    void getLocations() {

    }

    @State("A request to retrieve a location")
    Map<String, Object> getLocation() {
        Map<String, Object> map = new HashMap<>();
        map.put("locationId", UUID.fromString("1bfff94a-b70e-4b39-bd2a-be1c0f898589"));
        return map;
    }

    @State("A request to create a location")
    void createLocation() {

    }

    @State("A request to delete a location")
    Map<String, Object> deleteLocation() {
        Map<String, Object> map = new HashMap<>();
        map.put("locationId", UUID.fromString("121dba90-338d-4d41-991d-6f43fcd9336e"));
        return map;
    }

    @State("A request to update a location")
    Map<String, Object> updateLocation() {
        Map<String, Object> map = new HashMap<>();
        map.put("locationId", UUID.fromString("1bfff94a-b70e-4b39-bd2a-be1c0f898589"));
        return map;
    }

    @State("A request to retrieve a list of actors")
    void getActors() {

    }

    @State("A request to retrieve an actor")
    Map<String, Object> getActor() {
        Map<String, Object> map = new HashMap<>();
        map.put("actorId", UUID.fromString("1bfff94a-b70e-4b39-bd2a-be1c0f898589"));
        return map;
    }

    @State("A request to create an actor")
    void createActor() {

    }

    @State("A request to delete an actor")
    Map<String, Object> deleteActor() {
        Map<String, Object> map = new HashMap<>();
        map.put("actorId", UUID.fromString("347e8d6c-34af-45a6-97e8-0b5545759b75"));
        return map;
    }

    @State("A request to update an actor")
    Map<String, Object> updateActor() {
        Map<String, Object> map = new HashMap<>();
        map.put("actorId", UUID.fromString("1bfff94a-b70e-4b39-bd2a-be1c0f898589"));
        return map;
    }

    @State("A request to retrieve a list of rooms")
    void getRooms() {

    }

    @State("A request to retrieve a room")
    Map<String, Object> getRoom() {
        Map<String, Object> map = new HashMap<>();
        map.put("roomId", UUID.fromString("1bfff94a-b70e-4b39-bd2a-be1c0f898589"));
        return map;
    }

    @State("A request to create a room")
    void createRoom() {

    }

    @State("A request to update a room")
    Map<String, Object> updateRoom() {
        Map<String, Object> map = new HashMap<>();
        map.put("roomId", UUID.fromString("1bfff94a-b70e-4b39-bd2a-be1c0f898589"));
        return map;
    }

    @State("A request to delete a room")
    Map<String, Object> deleteRoom() {
        Map<String, Object> map = new HashMap<>();
        map.put("roomId", UUID.fromString("55615bd0-bf1e-4826-8689-e9d2fb928451"));
        return map;
    }

    @State("A request to retrieve a list of characters")
    void getCharacters() {

    }

    @State("A request to retrieve a character")
    Map<String, Object> getCharacter() {
        Map<String, Object> map = new HashMap<>();
        map.put("characterId", UUID.fromString("1bfff94a-b70e-4b39-bd2a-be1c0f898589"));
        return map;
    }

    @State("A request to create a character")
    void createCharacter() {

    }

    @State("A request to update a character")
    Map<String, Object> updateCharacter() {
        Map<String, Object> map = new HashMap<>();
        map.put("characterId", UUID.fromString("1bfff94a-b70e-4b39-bd2a-be1c0f898589"));
        return map;
    }

    @State("A request to delete a character")
    Map<String, Object> deleteCharacter() {
        Map<String, Object> map = new HashMap<>();
        map.put("characterId", UUID.fromString("674b1aa7-a3b7-4b1f-9c15-a3083b99c40d"));
        return map;
    }

}