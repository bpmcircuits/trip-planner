package com.kodilla.tripplanner.amadeus.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record AmadeusFlightsFlightOfferDTO(
        boolean oneWay,
        List<AmadeusFlightsItinerariesDTO> itineraries,
        AmadeusFlightsPriceDTO price,
        List<AmadeusFlightsTravelerPricingsDTO> travelerPricings
) {
}
