package com.example.mbbackend.pact.consumer.character;

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

import static com.example.mbbackend.config.Constants.*;
import static com.example.mbbackend.util.Utils.getRequestSpecification;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * As per https://developers.google.com/classroom/reference/rest
 * <p>
 * mvn -Dtest=com.hmhco.viaductservice.pact.consumer.*IT integration-test
 */

@ExtendWith(PactConsumerTestExt.class)
class DeleteCharacterIT {

    Map<String, String> headers = new HashMap<>();

    String path = "/api/mb/character/";

    @Pact(provider = PACT_PROVIDER, consumer = PACT_CONSUMER)
    public RequestResponsePact createPact(PactDslWithProvider builder) {

        headers.put("Content-Type", "application/json");

        return builder
                .given("A request to delete a character")
                .uponReceiving("A request to delete a character")
                .pathFromProviderState(path + "${characterId}", path + "1bfff94a-b70e-4b39-bd2a-be1c0f898589")
                .method("DELETE")
                .headers(headers)
                .willRespondWith()
                .status(206)
                .toPact();
    }

    @Test
    @PactTestFor(providerName = PACT_PROVIDER, port = PACT_PORT, pactVersion = PactSpecVersion.V3)
    void runTest() {

        //Mock url
        RequestSpecification rq = getRequestSpecification().baseUri(MOCK_PACT_URL).headers(headers);

        Response response = rq.delete(path + "1bfff94a-b70e-4b39-bd2a-be1c0f898589");

        assertEquals(206, response.getStatusCode());
    }

}
