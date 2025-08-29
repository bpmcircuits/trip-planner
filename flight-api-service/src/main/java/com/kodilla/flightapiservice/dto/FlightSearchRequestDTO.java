package com.kodilla.flightapiservice.dto;

public record FlightSearchRequestDTO(
        String departureCity,
        String arrivalCity,
        String departureDate,
        String returnDate,
        int adults,
        int children,
        int infants,
        String currencyCode
) {
}
