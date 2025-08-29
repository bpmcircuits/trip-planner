package com.kodilla.hotelapiservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class GlobalHttpErrorHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(HotelNotFoundException.class)
    public ResponseEntity<Object> handleHotelNotFoundException(HotelNotFoundException exception) {
        return new ResponseEntity<>("Hotel not found: " + exception.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(DestinationNotFoundException.class)
    public ResponseEntity<Object> handleDestinationNotFoundException(DestinationNotFoundException exception) {
        return new ResponseEntity<>("Destination not found: " + exception.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(BookingApiException.class)
    public ResponseEntity<Object> handleBookingApiException(BookingApiException exception) {
        return new ResponseEntity<>("Booking API error: " + exception.getMessage(), HttpStatus.SERVICE_UNAVAILABLE);
    }

    @ExceptionHandler(DatabaseOperationException.class)
    public ResponseEntity<Object> handleDatabaseOperationException(DatabaseOperationException exception) {
        return new ResponseEntity<>("Database operation error: " + exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleGenericException(Exception exception) {
        return new ResponseEntity<>("An error occurred: " + exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}