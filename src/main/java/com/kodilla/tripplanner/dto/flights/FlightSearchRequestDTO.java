package com.kodilla.tripplanner.dto.flights;

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
