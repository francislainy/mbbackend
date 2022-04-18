package com.example.mbbackend.util;

import com.example.mbbackend.config.Constants;
import com.example.mbbackend.model.Character;
import com.example.mbbackend.model.*;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import java.util.List;

public class ApiRequests {

    int port;

    public ApiRequests(int port) {
        this.port = port;
    }

    List<Character> getCharacterList() {

        RequestSpecification rq = Utils.getRequestSpecification();

        Response response = rq.get(Constants.BASE_URI_LOCAL + port + "/api/mb/character");

        return response.jsonPath().get("characters");
    }

    public Character getFirstCharacterFromList() {
        return Utils.createClassFromObject(getCharacterList().get(0), Character.class);
    }

    List<Room> getRoomList() {

        RequestSpecification rq = Utils.getRequestSpecification();

        Response response = rq.get(Constants.BASE_URI_LOCAL + port + "/api/mb/room");

        return response.jsonPath().get("rooms");
    }

    public Room getFirstRoomFromList() {
        return Utils.createClassFromObject(getRoomList().get(0), Room.class);
    }

    List<Location> getLocationList() {

        RequestSpecification rq = Utils.getRequestSpecification();

        Response response = rq.get(Constants.BASE_URI_LOCAL + port + "/api/mb/location");

        return response.jsonPath().get("locations");
    }

    public Location getFirstLocationFromList() {
        return Utils.createClassFromObject(getLocationList().get(0), Location.class);
    }

    List<Actor> getActorList() {

        RequestSpecification rq = Utils.getRequestSpecification();

        Response response = rq.get(Constants.BASE_URI_LOCAL + port + "/api/mb/actor");

        return response.jsonPath().get("actors");
    }

    public Actor getFirstActorFromList() {
        return Utils.createClassFromObject(getActorList().get(0), Actor.class);
    }

    List<Movie> getMovieList() {

        RequestSpecification rq = Utils.getRequestSpecification();

        Response response = rq.get(Constants.BASE_URI_LOCAL + port + "/api/mb/movie");

        return response.jsonPath().get("movies");
    }

    public Movie getFirstMovieFromList() {
        return Utils.createClassFromObject(getMovieList().get(0), Movie.class);
    }
}
