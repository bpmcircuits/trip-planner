package com.kodilla.flightapiservice.amadeus.dto;

public record AmadeusFlightsAirportSearchRequestDTO(
        String originLocationCode,
        String destinationLocationCode,
        String departureDate,
        String returnDate,
        int adults,
        int children,
        int infants,
        String currencyCode
) {}
