package com.kodilla.tripplanner.amadeus.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record AmadeusFlightsFlightEndPointDTO(
        String iataCode,
        String terminal,
        String at
) {
}
