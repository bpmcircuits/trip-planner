package com.kodilla.tripplanner.amadeus.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record AmadeusFlightsTravelerPricingsDTO(
        String travelerId,
        String fareOption,
        String travelerType,
        AmadeusFlightsPriceDTO price
) {
}
