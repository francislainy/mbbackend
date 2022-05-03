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
 * mvn -Dtest=com.example.mbbackend.pact.consumer.*IT integration-test
 */

@ExtendWith(PactConsumerTestExt.class)
class PostCreateActorIT {

    Map<String, String> headers = new HashMap<>();

    String path = "/api/mb/actor/";

    @Pact(provider = PACT_PROVIDER, consumer = PACT_CONSUMER)
    public RequestResponsePact createPact(PactDslWithProvider builder) {

        headers.put("Content-Type", "application/json");

        DslPart bodyGiven = new PactDslJsonBody()
                .stringType("name", "Jennie")
                .stringType("associatedPinyinSound", "ji")
                .stringType("family", "FEMALE")
                .stringType("imageUrl", "http://anyimage.com")
                .close();

        DslPart bodyReturned = new PactDslJsonBody()
                .uuid("id", "2cfff94a-b70e-4b39-bd2a-be1c0f898589")
                .stringType("name", "Jennie")
                .stringType("associatedPinyinSound", "ji")
                .stringType("family", "FEMALE")
                .stringType("imageUrl", "http://anyimage.com")
                .close();

        return builder
                .given("A request to create an actor")
                .uponReceiving("A request to create an actor")
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

        Actor actor = Actor.builder()
                .name("South London")
                .associatedPinyinSound("OU")
                .family(ActorFamily.FEMALE)
                .imageUrl("http://anyimage.com")
                .build();

        //Mock url
        RequestSpecification rq = Utils.getRequestSpecification().body(actor).baseUri(MOCK_PACT_URL).headers(headers);

        Response response = rq.post(path);

        assertEquals(201, response.getStatusCode());
    }

}
