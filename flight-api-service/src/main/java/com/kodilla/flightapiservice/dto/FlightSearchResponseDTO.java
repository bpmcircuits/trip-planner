package com.kodilla.flightapiservice.dto;

import java.util.List;

public record FlightSearchResponseDTO(
        boolean oneWay,
        FlightBoundDTO outbound,
        FlightBoundDTO inbound,
        PriceDTO totalPrice,
        List<TravelerPriceDTO> travelerPrices
) {}
