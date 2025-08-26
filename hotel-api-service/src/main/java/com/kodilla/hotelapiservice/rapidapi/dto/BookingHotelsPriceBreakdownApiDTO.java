package com.kodilla.tripplannerhotelapi.rapidapi.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record BookingHotelsPriceBreakdownApiDTO(
    BookingHotelsGrossPriceApiDTO grossPrice
) {}
