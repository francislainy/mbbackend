package com.example.mbblueprintbackend.util;

import au.com.dius.pact.core.model.RequestResponseInteraction;
import au.com.dius.pact.provider.junit5.PactVerificationContext;
import com.example.mbblueprintbackend.entity.location.LocationEntity;
import com.example.mbblueprintbackend.model.Actor;
import com.example.mbblueprintbackend.model.Location;
import com.example.mbblueprintbackend.model.Movie;
import com.example.mbblueprintbackend.model.Room;
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
import java.util.*;

import static io.restassured.RestAssured.given;

@Slf4j
public class Utils {

    public static String jsonStringFromObject(Object jsonObject) {
        if (jsonObject == null) {
            return "";
        }

        ObjectMapper mapper = new ObjectMapper();
        String jsonStr;
        try {
            jsonStr = mapper.writeValueAsString(jsonObject);
        } catch (JsonProcessingException e) {
            log.error(e.toString());
            throw new RuntimeException(e.getMessage()); // NOSONAR
        }
        return jsonStr;
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

    public static Map<String, Object> getAllMovies() {
        List<Object> movieList = new ArrayList<>();
        Actor actor = Actor.builder().name("Shakira").build();
        Room room = Room.builder().title("Bedroom").build();
        Location location = Location.builder().title("Childhood home").build();

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
        movieList.add(movie);
        Map<String, Object> map = new HashMap<>();
        map.put("movies", movieList);
        return map;
    }

    public static Movie getSingleMovie(UUID uuid) {

        Actor actor = Actor.builder().id(UUID.fromString("6b00f4e7-c499-4fd6-907d-ec0e8b9934b2")).build();
        Room room = Room.builder().id(UUID.fromString("40325c6e-047f-452d-9a9a-f93111510764")).build();
        Location location = Location.builder().id(UUID.fromString("c7b6f5b7-747b-490f-99d2-f6e3ebdbc060")).build();

        return Movie.builder()
                .id(uuid)
                .actor(actor)
                .character("西")
                .imageUrl("anyUrl")
                .meaning("anyMeaning")
                .pinyin("xī")
                .room(room)
                .location(location)
                .scene("Shakira talking to Kanye West outside the front entrance")
                .build();
    }

    public static List<LocationEntity> getAllLocations() {
        List<LocationEntity> locationList = new ArrayList<>();
        UUID locationId = UUID.fromString("1bfff94a-b70e-4b39-bd2a-be1c0f898589");

        LocationEntity location = LocationEntity.builder()
                .id(locationId)
                .title("South London")
                .associatedPinyinSound("Ou")
                .build();

        locationList.add(location);
        return locationList;
    }

    public static Optional<LocationEntity> getSingleLocation(UUID locationId) {

        return Optional.ofNullable(LocationEntity.builder()
                .id(locationId)
                .title("South London")
                .associatedPinyinSound("Ou")
                .build());
    }
}
