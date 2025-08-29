package com.bpm.frontend.tripplanerback.dto.flights;

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
