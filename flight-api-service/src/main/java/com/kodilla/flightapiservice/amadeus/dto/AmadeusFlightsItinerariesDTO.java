package com.kodilla.flightapiservice.amadeus.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record AmadeusFlightsItinerariesDTO(
        String duration,
        List<AmadeusFlightsSegmentsDTO> segments
) {
}
