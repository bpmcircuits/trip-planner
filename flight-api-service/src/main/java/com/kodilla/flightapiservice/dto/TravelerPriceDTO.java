package com.kodilla.flightapiservice.dto;

public record TravelerPriceDTO(
        String travelerType,             // "ADULT" | "CHILD" | "INFANT"
        PriceDTO price
) {}
