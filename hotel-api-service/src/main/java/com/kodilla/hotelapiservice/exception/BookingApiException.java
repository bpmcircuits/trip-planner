package com.kodilla.hotelapiservice.exception;

public class BookingApiException extends Exception {
    public BookingApiException(String message) {
        super(message);
    }
    
    public BookingApiException(String message, Throwable cause) {
        super(message, cause);
    }
}