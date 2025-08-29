package com.kodilla.hotelapiservice.rapidapi.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record BookingHotelsPriceBreakdownApiDTO(
    BookingHotelsGrossPriceApiDTO grossPrice
) {}
