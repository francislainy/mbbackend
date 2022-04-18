package com.example.mbbackend.pact.consumer.actor;

import au.com.dius.pact.consumer.dsl.DslPart;
import au.com.dius.pact.consumer.dsl.PactDslJsonBody;
import au.com.dius.pact.consumer.dsl.PactDslWithProvider;
import au.com.dius.pact.consumer.junit5.PactConsumerTestExt;
import au.com.dius.pact.consumer.junit5.PactTestFor;
import au.com.dius.pact.core.model.PactSpecVersion;
import au.com.dius.pact.core.model.RequestResponsePact;
import au.com.dius.pact.core.model.annotations.Pact;
import com.example.mbbackend.config.ActorFamily;
import com.example.mbbackend.model.Actor;
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
class PutUpdateActorIT {

    Map<String, String> headers = new HashMap<>();

    String path = "/api/mb/actor/";

    @Pact(provider = PACT_PROVIDER, consumer = PACT_CONSUMER)
    public RequestResponsePact createPact(PactDslWithProvider builder) {

        headers.put("Content-Type", "application/json");

        DslPart bodyGiven = new PactDslJsonBody()
                .stringType("name", "Jennie")
                .stringType("associatedPinyinSound", "ji")
                .stringType("family", "FEMALE")
                .stringType("imageUrl", "anyUrl")
                .close();

        DslPart bodyReturned = new PactDslJsonBody()
                .uuid("id", "2cfff94a-b70e-4b39-bd2a-be1c0f898589")
                .stringType("name", "Jennie")
                .stringType("associatedPinyinSound", "ji")
                .stringType("family", "FEMALE")
                .stringType("imageUrl", "anyUrl")
                .close();

        return builder
                .given("A request to update an actor")
                .uponReceiving("A request to update an actor")
                .pathFromProviderState(path + "${actorId}", path + "1bfff94a-b70e-4b39-bd2a-be1c0f898589")
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

        Actor actor = Actor.builder()
                .name("Jennie")
                .associatedPinyinSound("ji")
                .family(ActorFamily.FEMALE)
                .imageUrl("anyUrl")
                .build();

        //Mock url
        RequestSpecification rq = Utils.getRequestSpecification().body(actor).baseUri(MOCK_PACT_URL).headers(headers);

        Response response = rq.put(path + "1bfff94a-b70e-4b39-bd2a-be1c0f898589");

        assertEquals(200, response.getStatusCode());
    }

}
