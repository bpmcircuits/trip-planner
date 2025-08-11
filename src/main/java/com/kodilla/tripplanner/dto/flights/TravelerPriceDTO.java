package com.kodilla.tripplanner.dto.flights;

public record TravelerPriceDTO(
        String travelerType,             // "ADULT" | "CHILD" | "INFANT"
        PriceDTO price
) {}
