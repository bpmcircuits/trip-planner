package com.kodilla.tripapiservice.exception;

public class TripNotFoundException extends Exception {
    public TripNotFoundException(String message) {
        super(message);
    }
}