package com.bpm.frontend.tripplanerback.dto.flights;

public record TravelerPriceDTO(
        String travelerType,             // "ADULT" | "CHILD" | "INFANT"
        PriceDTO price
) {}
