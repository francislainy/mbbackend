package com.example.mbbackend.pact.consumer.room;

import au.com.dius.pact.consumer.dsl.DslPart;
import au.com.dius.pact.consumer.dsl.PactDslJsonBody;
import au.com.dius.pact.consumer.dsl.PactDslWithProvider;
import au.com.dius.pact.consumer.junit5.PactConsumerTestExt;
import au.com.dius.pact.consumer.junit5.PactTestFor;
import au.com.dius.pact.core.model.PactSpecVersion;
import au.com.dius.pact.core.model.RequestResponsePact;
import au.com.dius.pact.core.model.annotations.Pact;
import com.example.mbbackend.model.Room;
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
 * As per https://developers.google.com/classroom/reference/rest
 * <p>
 * mvn -Dtest=com.hmhco.viaductservice.pact.consumer.*IT integration-test
 */

@ExtendWith(PactConsumerTestExt.class)
class PostCreateRoomIT {

    Map<String, String> headers = new HashMap<>();

    String path = "/api/mb/room/";

    @Pact(provider = PACT_PROVIDER, consumer = PACT_CONSUMER)
    public RequestResponsePact createPact(PactDslWithProvider builder) {

        headers.put("Content-Type", "application/json");

        DslPart bodyGiven = new PactDslJsonBody()
                .stringType("title", "Bedroom")
                .close();

        DslPart bodyReturned = new PactDslJsonBody()
                .uuid("id", "2cfff94a-b70e-4b39-bd2a-be1c0f898589")
                .stringType("title", "Bedroom")
                .close();

        return builder
                .given("A request to create a room")
                .uponReceiving("A request to create a room")
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

        Room room = Room.builder()
                .title("Bedroom")
                .build();

        //Mock url
        RequestSpecification rq = Utils.getRequestSpecification().body(room).baseUri(MOCK_PACT_URL).headers(headers);

        Response response = rq.post(path);

        assertEquals(201, response.getStatusCode());
    }

}
