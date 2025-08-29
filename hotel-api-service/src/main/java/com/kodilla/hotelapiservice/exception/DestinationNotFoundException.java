package com.kodilla.hotelapiservice.exception;

public class DestinationNotFoundException extends Exception {
    public DestinationNotFoundException(String message) {
        super(message);
    }
}