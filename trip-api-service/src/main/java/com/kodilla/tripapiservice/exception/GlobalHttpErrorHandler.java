package com.kodilla.tripapiservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class GlobalHttpErrorHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(TripNotFoundException.class)
    public ResponseEntity<Object> handleTripNotFoundException(TripNotFoundException exception) {
        return new ResponseEntity<>("Trip with given id not found", HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(TravelerNotFoundException.class)
    public ResponseEntity<Object> handleTravelerNotFoundException(TravelerNotFoundException exception) {
        return new ResponseEntity<>("Traveler with given id not found", HttpStatus.NOT_FOUND);
    }
    
    @ExceptionHandler(FlightNotFoundException.class)
    public ResponseEntity<Object> handleFlightNotFoundException(FlightNotFoundException exception) {
        return new ResponseEntity<>("Flight with given id not found", HttpStatus.NOT_FOUND);
    }
    
    @ExceptionHandler(HotelNotFoundException.class)
    public ResponseEntity<Object> handleHotelNotFoundException(HotelNotFoundException exception) {
        return new ResponseEntity<>("Hotel with given id not found", HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleGenericException(Exception exception) {
        return new ResponseEntity<>("An error occurred: " + exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}