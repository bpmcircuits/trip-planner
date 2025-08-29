package com.kodilla.hotelapiservice.rapidapi.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record BookingHotelsApiDTO(
    String name,
    String currency,
    String wishlistName,
    String countryCode,
    String checkinDate,
    String checkoutDate,
    double reviewScore,
    String reviewScoreWord,
    int reviewCount,
    BookingHotelsCheckTimeApiDTO checkin,
    BookingHotelsCheckTimeApiDTO checkout,
    BookingHotelsPriceBreakdownApiDTO priceBreakdown
) {}
