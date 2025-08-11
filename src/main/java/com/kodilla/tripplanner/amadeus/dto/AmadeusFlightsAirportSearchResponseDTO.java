package com.kodilla.tripplanner.amadeus.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record AmadeusFlightsAirportSearchResponseDTO(
        List<AmadeusFlightsAirportSearchDataDTO> data
) {
}
