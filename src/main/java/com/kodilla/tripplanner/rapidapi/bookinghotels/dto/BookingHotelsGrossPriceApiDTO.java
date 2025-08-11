package com.kodilla.tripplanner.rapidapi.bookinghotels.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record BookingHotelsGrossPriceApiDTO(
    double value,
    String currency
) {}
