package com.kodilla.hotelapiservice.exception;

public class HotelNotFoundException extends Exception {
    public HotelNotFoundException(String message) {
        super(message);
    }
}