package com.example.mbblueprintbackend.pact.provider;

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

import static com.example.mbblueprintbackend.config.Constants.*;
import static com.example.mbblueprintbackend.util.Utils.logCurlFromPact;

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
class PactProviderEdIT {

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

    @State("A request to retrieve a movie")
    Map<String, Object> sampleState1() {
        Map<String, Object> map = new HashMap<>();
        map.put("movieId", UUID.randomUUID());
        return map;
    }

    @State("A request to retrieve a list of movies")
    void sampleState2() {

    }

    @State("A request to create a movie")
    void sampleState3() {

    }

}

