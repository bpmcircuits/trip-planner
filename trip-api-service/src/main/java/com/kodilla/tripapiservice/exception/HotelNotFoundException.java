package com.kodilla.tripapiservice.exception;

public class HotelNotFoundException extends Exception {
    public HotelNotFoundException(String message) {
        super(message);
    }
}