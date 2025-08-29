package com.kodilla.flightapiservice.amadeus.client;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.kodilla.flightapiservice.amadeus.dto.AmadeusFlightsFlightOfferDTO;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record AmadeusFlightsFlightOffersDataDTO(
        List<AmadeusFlightsFlightOfferDTO> data
) {
}
