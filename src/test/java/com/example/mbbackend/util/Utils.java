package com.example.mbbackend.util;

import au.com.dius.pact.core.model.RequestResponseInteraction;
import au.com.dius.pact.provider.junit5.PactVerificationContext;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.github.dzieciou.testing.curl.CurlRestAssuredConfigFactory;
import com.github.dzieciou.testing.curl.Options;
import io.restassured.config.ObjectMapperConfig;
import io.restassured.config.RestAssuredConfig;
import io.restassured.http.ContentType;
import io.restassured.path.json.mapper.factory.Jackson2ObjectMapperFactory;
import io.restassured.specification.RequestSpecification;
import lombok.extern.slf4j.Slf4j;
import org.apache.hc.core5.http.Header;
import org.apache.hc.core5.http.HttpRequest;

import java.lang.reflect.Type;
import java.util.HashMap;

import static io.restassured.RestAssured.given;

@Slf4j
public class Utils {

    public static String jsonStringFromObject(Object jsonObject) {
        if (jsonObject == null) {
            return "";
        }

        ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        String jsonStr;
        try {
            jsonStr = mapper.writeValueAsString(jsonObject);
        } catch (JsonProcessingException e) {
            log.error(e.toString());
            throw new RuntimeException(e.getMessage()); // NOSONAR
        }
        return jsonStr;
    }

    public static <T> T createClassFromMap(HashMap<T, T> map, Class<T> c) {

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(
                DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        try {
            return objectMapper
                    .convertValue(map, objectMapper.getTypeFactory().constructType(c));
        } catch (Exception e) {
            log.error("converting failed! Map: {}, class: {}", "getJsonString(map)", c.getSimpleName(), e);
        }
        return null;
    }

    public static <T> T createClassFromObject(Object o, Class<T> c) {

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(
                DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        try {
            return objectMapper
                    .convertValue(o, objectMapper.getTypeFactory().constructType(c));
        } catch (Exception e) {
            log.error("converting failed! Map: {}, class: {}", "getJsonString(map)", c.getSimpleName(), e);
        }
        return null;
    }

    public static RequestSpecification getRequestSpecification() {

        /** Enables printing request as curl under the terminal as per https://github.com/dzieciou/curl-logger */
        Options options = Options.builder()
                .printMultiliner()
                .updateCurl(curl -> curl
                        .removeHeader("Host")
                        .removeHeader("User-Agent")
                        .removeHeader("Connection"))
                .build();

        RestAssuredConfig config = CurlRestAssuredConfigFactory.createConfig(options).objectMapperConfig(new ObjectMapperConfig().jackson2ObjectMapperFactory(
                new Jackson2ObjectMapperFactory() {
                    @Override
                    public ObjectMapper create(Type type, String charset) {
                        ObjectMapper om = new ObjectMapper().findAndRegisterModules();
                        om.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
                        om.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
                        return om;
                    }

                }
        ));

        return given()
                .config(config)
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .urlEncodingEnabled(false)
                .when()
                .log()
                .everything();
    }

    public static void logCurlFromPact(PactVerificationContext context, HttpRequest request, String baseUri) {

        String bodyParam = ((RequestResponseInteraction) context.getInteraction()).getRequest().getBody().valueAsString();

        String bodyResponse = ((RequestResponseInteraction) context.getInteraction()).getResponse().getBody().valueAsString();

        String method = ((RequestResponseInteraction) context.getInteraction()).getRequest().getMethod();

        String url = baseUri + request.getPath();

        Header[] headers = request.getHeaders();

        String headersString = "";
        for (Header s : headers) {
            headersString = headersString + "--header " + "'" + s.getName() + ": " + s.getValue() + "'" + "\\" + "\n";
        }

        String curl = "" +
                "curl " +
                "--request " + method + " " +
                "'" + url + "' \\" + "\n" +
                headersString +
                "--data-binary " + "'" + bodyParam + "' \\" + "\n" +
                "--compressed \\" + "\n" +
                "--insecure \\" + "\n" +
                "--verbose" +
                "";

//        log.debug(curl + "\n\n " + bodyResponse + "\n ---- \n\n");
        System.out.println((curl + "\n\n " + bodyResponse + "\n ---- \n\n"));
    }

}
