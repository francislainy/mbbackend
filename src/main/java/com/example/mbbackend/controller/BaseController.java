package com.example.mbbackend.controller;

import com.example.mbbackend.exception.CharacterAlreadyExistsException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.Collections;
import java.util.Map;

public class BaseController {

    @ExceptionHandler(CharacterAlreadyExistsException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public Map<String, String> handleAuthenticationChainException(CharacterAlreadyExistsException ex) {
        return Collections.singletonMap("CharacterAlreadyExistsException", ex.getMessage());
    }
}
