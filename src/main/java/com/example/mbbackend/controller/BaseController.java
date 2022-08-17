package com.example.mbbackend.controller;

import com.example.mbbackend.exception.CharacterAlreadyExistsException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

public class BaseController {

    @ExceptionHandler(CharacterAlreadyExistsException.class)
    @ResponseBody
    public ResponseEntity<Object> handleAuthenticationChainException(CharacterAlreadyExistsException ex) {

        StringResponse stringResponse = new StringResponse("CharacterAlreadyExistsException: " + ex.getMessage());

        return new ResponseEntity<>(stringResponse, HttpStatus.BAD_REQUEST);
    }
    
    record StringResponse(String response) {

        public String getResponse() {
            return response;
        }
    }
}
