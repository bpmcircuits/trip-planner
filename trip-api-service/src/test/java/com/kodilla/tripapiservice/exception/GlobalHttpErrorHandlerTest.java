package com.kodilla.tripapiservice.exception;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;

class GlobalHttpErrorHandlerTest {

    private GlobalHttpErrorHandler globalHttpErrorHandler;

    @BeforeEach
    void setUp() {
        globalHttpErrorHandler = new GlobalHttpErrorHandler();
    }

    @Test
    void shouldHandleTripNotFoundException() {
        // Given
        TripNotFoundException exception = new TripNotFoundException("Trip with ID 1 not found");

        // When
        ResponseEntity<Object> response = globalHttpErrorHandler.handleTripNotFoundException(exception);

        // Then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(response.getBody()).isEqualTo("Trip with given id not found");
    }

    @Test
    void shouldHandleTravelerNotFoundException() {
        // Given
        TravelerNotFoundException exception = new TravelerNotFoundException("Traveler with ID 1 not found");

        // When
        ResponseEntity<Object> response = globalHttpErrorHandler.handleTravelerNotFoundException(exception);

        // Then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(response.getBody()).isEqualTo("Traveler with given id not found");
    }

    @Test
    void shouldHandleFlightNotFoundException() {
        // Given
        FlightNotFoundException exception = new FlightNotFoundException("Flight with ID 1 not found");

        // When
        ResponseEntity<Object> response = globalHttpErrorHandler.handleFlightNotFoundException(exception);

        // Then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(response.getBody()).isEqualTo("Flight with given id not found");
    }

    @Test
    void shouldHandleHotelNotFoundException() {
        // Given
        HotelNotFoundException exception = new HotelNotFoundException("Hotel with ID 1 not found");

        // When
        ResponseEntity<Object> response = globalHttpErrorHandler.handleHotelNotFoundException(exception);

        // Then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(response.getBody()).isEqualTo("Hotel with given id not found");
    }

    @Test
    void shouldHandleGenericException() {
        // Given
        Exception exception = new RuntimeException("Something went wrong");

        // When
        ResponseEntity<Object> response = globalHttpErrorHandler.handleGenericException(exception);

        // Then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
        assertThat(response.getBody()).isEqualTo("An error occurred: Something went wrong");
    }
}