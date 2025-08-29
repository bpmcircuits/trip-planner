package com.kodilla.hotelapiservice.rapidapi.dto;


public record BookingHotelsRequestDTO(
        String query,
        String checkinDate,
        String checkoutDate,
        int adultsNumber,
        String currency,
        String locale
) {}

