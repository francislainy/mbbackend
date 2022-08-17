package com.example.mbbackend.exception;

public class CharacterAlreadyExistsException extends RuntimeException {
    
    public CharacterAlreadyExistsException(String message) {
        super(message);
    }
}