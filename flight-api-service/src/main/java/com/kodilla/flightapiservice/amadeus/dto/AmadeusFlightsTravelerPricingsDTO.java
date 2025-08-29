package com.kodilla.flightapiservice.amadeus.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record AmadeusFlightsTravelerPricingsDTO(
        String travelerId,
        String fareOption,
        String travelerType,
        AmadeusFlightsPriceDTO price
) {
}
