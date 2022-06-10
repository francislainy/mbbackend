package com.example.mbbackend.pact.provider;

import au.com.dius.pact.provider.junit5.HttpTestTarget;
import au.com.dius.pact.provider.junit5.PactVerificationContext;
import au.com.dius.pact.provider.junit5.PactVerificationInvocationContextProvider;
import au.com.dius.pact.provider.junitsupport.Consumer;
import au.com.dius.pact.provider.junitsupport.Provider;
import au.com.dius.pact.provider.junitsupport.State;
import au.com.dius.pact.provider.junitsupport.VerificationReports;
import au.com.dius.pact.provider.junitsupport.loader.PactFolder;
import com.example.mbbackend.config.BaseIntegrationTest;
import com.example.mbbackend.util.ApiRequests;
import jakarta.transaction.Transactional;
import org.apache.hc.core5.http.HttpRequest;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestTemplate;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.jdbc.Sql;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static com.example.mbbackend.config.Constants.*;
import static com.example.mbbackend.util.Utils.logCurlFromPact;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.AFTER_TEST_METHOD;

/**
 * mvn -Dtest=com.example.mbbackend.pact.provider.*IT integration-test -DtestEnvt=int -Dpactbroker.auth.token=myToken
 */

/* ✨👇 Uncomment this and comment @PactBroker to test locally by pasting a .json file for the contract under the target/pacts folder (use import au.com.dius.pact.provider.junit.loader.PactFolder;) ✨*/
@PactFolder("target/pacts")

@Provider(PACT_PROVIDER)
@Consumer(PACT_CONSUMER)
//@PactBroker(url = BROKER_PACT_URL, authentication = @PactBrokerAuth(token = "${pactbroker.auth.token}"))
@VerificationReports(value = {"markdown"}, reportDir = "target/pacts")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Sql(scripts = "classpath:init.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = "classpath:clean-up.sql", executionPhase = AFTER_TEST_METHOD)
class ProviderIT extends BaseIntegrationTest {

    @LocalServerPort
    int port;

    ApiRequests apiRequests;

    @TestTemplate
    @ExtendWith(PactVerificationInvocationContextProvider.class)
    void pactTestTemplate(PactVerificationContext context, HttpRequest request) {
//        request.addHeader("Authorization", getAuthorizationToken());

        logCurlFromPact(context, request, BASE_URI_LOCAL + port);

        context.verifyInteraction();
    }


    @BeforeAll
    void setUp() {
        apiRequests = new ApiRequests(port);
    }

    @BeforeEach
    void before(PactVerificationContext context) {

        context.setTarget(new HttpTestTarget(BASE_PACT_URL_LOCAL, port, ""));
    }

    @State("A request to retrieve a list of movies")
    void getMovies() {

    }

    @State("A request to retrieve a list of movies for an actor")
    Map<String, Object> getMoviesForActor() {
        Map<String, Object> map = new HashMap<>();
        map.put("actorId", "549f26ac-151f-48df-abf2-ec36617be78e");
        return map;
    }

    @State("A request to retrieve a movie")
    Map<String, Object> getMovie() {
        Map<String, Object> map = new HashMap<>();
        map.put("movieId", apiRequests.getFirstMovieFromList().getId());
        return map;
    }

    @State("A request to create a movie")
    void createMovie() {

    }

    @State("A request to update a movie")
    Map<String, Object> updateMovie() {
        Map<String, Object> map = new HashMap<>();
        map.put("movieId", apiRequests.getFirstMovieFromList().getId());
        return map;
    }

    @State("A request to delete a movie")
    Map<String, Object> deleteMovie() {
        Map<String, Object> map = new HashMap<>();
        map.put("movieId", apiRequests.getFirstMovieFromList().getId());
        return map;
    }

    @State("A request to retrieve a list of locations")
    void getLocations() {

    }

    @State("A request to retrieve a location")
    Map<String, Object> getLocation() {
        Map<String, Object> map = new HashMap<>();
        map.put("locationId", apiRequests.getFirstLocationFromList().getId());
        return map;
    }

    @State("A request to create a location")
    void createLocation() {

    }

    @State("A request to delete a location")
    Map<String, Object> deleteLocation() {
        Map<String, Object> map = new HashMap<>();
        map.put("locationId", apiRequests.getFirstLocationFromList().getId());
        return map;
    }

    @State("A request to update a location")
    Map<String, Object> updateLocation() {
        Map<String, Object> map = new HashMap<>();
        map.put("locationId", apiRequests.getFirstLocationFromList().getId());
        return map;
    }

    @State("A request to retrieve a list of actors")
    void getActors() {

    }

    @State("A request to retrieve an actor")
    Map<String, Object> getActor() {
        Map<String, Object> map = new HashMap<>();
        map.put("actorId", apiRequests.getFirstActorFromList().getId());
        return map;
    }

    @State("A request to create an actor")
    void createActor() {

    }

    @State("A request to delete an actor")
    Map<String, Object> deleteActor() {
        Map<String, Object> map = new HashMap<>();
        map.put("actorId", apiRequests.getFirstActorFromList().getId());
        return map;
    }

    @State("A request to update an actor")
    Map<String, Object> updateActor() {
        Map<String, Object> map = new HashMap<>();
        map.put("actorId", apiRequests.getFirstActorFromList().getId());
        return map;
    }

    @State("A request to retrieve a list of rooms")
    void getRooms() {

    }

    @State("A request to retrieve a room")
    Map<String, Object> getRoom() {
        Map<String, Object> map = new HashMap<>();
        map.put("roomId", apiRequests.getFirstRoomFromList().getId());
        return map;
    }

    @State("A request to create a room")
    void createRoom() {

    }

    @State("A request to update a room")
    Map<String, Object> updateRoom() {
        Map<String, Object> map = new HashMap<>();
        map.put("roomId", apiRequests.getFirstRoomFromList().getId());
        return map;
    }

    @State("A request to delete a room")
    Map<String, Object> deleteRoom() {
        Map<String, Object> map = new HashMap<>();
        map.put("roomId", apiRequests.getFirstRoomFromList().getId());
        return map;
    }

    @State("A request to retrieve a list of characters")
    void getCharacters() {

    }

    @State("A request to retrieve a character")
    Map<String, Object> getCharacter() {
        Map<String, Object> map = new HashMap<>();
        map.put("characterId", apiRequests.getFirstCharacterFromList().getId());
        return map;
    }

    @State("A request to create a character")
    void createCharacter() {

    }

    @State("A request to update a character")
    Map<String, Object> updateCharacter() {
        Map<String, Object> map = new HashMap<>();
        map.put("characterId", apiRequests.getFirstCharacterFromList().getId());
        return map;
    }

    @State("A request to delete a character")
    Map<String, Object> deleteCharacter() {
        Map<String, Object> map = new HashMap<>();
        map.put("characterId", apiRequests.getFirstCharacterFromList().getId());
        return map;
    }

}
