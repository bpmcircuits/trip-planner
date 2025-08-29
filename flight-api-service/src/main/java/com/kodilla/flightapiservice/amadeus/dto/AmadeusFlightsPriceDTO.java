package com.kodilla.flightapiservice.amadeus.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record AmadeusFlightsPriceDTO(
        String total,
        String currency
) {
}
