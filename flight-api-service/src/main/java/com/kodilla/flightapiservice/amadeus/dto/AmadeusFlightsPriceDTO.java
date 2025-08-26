package com.kodilla.tripplanner.amadeus.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record AmadeusFlightsPriceDTO(
        String total,
        String currency
) {
}
