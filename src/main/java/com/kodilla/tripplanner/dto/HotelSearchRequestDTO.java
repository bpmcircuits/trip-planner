package com.kodilla.tripplanner.dto;


public record HotelSearchRequestDTO(
        String query,
        String checkinDate,
        String checkoutDate,
        int adultsNumber,
        String currency,
        String locale
) {}

