package com.bpm.frontend.tripplanerback.dto.flights;

import java.util.List;

public record FlightSearchResponseDTO(
        boolean oneWay,
        FlightBoundDTO outbound,
        FlightBoundDTO inbound,
        PriceDTO totalPrice,
        List<TravelerPriceDTO> travelerPrices
) {}
