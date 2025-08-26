package com.kodilla.tripplanner.amadeus.client;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.kodilla.tripplanner.amadeus.dto.AmadeusFlightsFlightOfferDTO;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record AmadeusFlightsFlightOffersDataDTO(
        List<AmadeusFlightsFlightOfferDTO> data
) {
}
