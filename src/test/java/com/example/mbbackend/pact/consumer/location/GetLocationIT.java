package com.example.mbbackend.pact.consumer.location;

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

import static com.example.mbbackend.config.Constants.*;
import static com.example.mbbackend.util.Utils.getRequestSpecification;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * mvn -Dtest=com.example.mbbackend.pact.consumer.*.*IT integration-test
 */

@ExtendWith(PactConsumerTestExt.class)
class GetLocationIT {

    Map<String, String> headers = new HashMap<>();

    String path = "/api/mb/location/";

    @Pact(provider = PACT_PROVIDER, consumer = PACT_CONSUMER)
    public RequestResponsePact createPact(PactDslWithProvider builder) {

        headers.put("Content-Type", "application/json");

        DslPart bodyReturned = new PactDslJsonBody()
                .uuid("id", "1bfff94a-b70e-4b39-bd2a-be1c0f898589")
                .stringType("title", "anyTitle")
                .stringType("associatedPinyinSound", "OU")
                .close();

        return builder
                .given("A request to retrieve a location")
                .uponReceiving("A request to retrieve a location")
                .pathFromProviderState(path + "${locationId}", path + "1bfff94a-b70e-4b39-bd2a-be1c0f898589")
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

        Response response = rq.get(path + "1bfff94a-b70e-4b39-bd2a-be1c0f898589");

        assertEquals(200, response.getStatusCode());
    }

}
