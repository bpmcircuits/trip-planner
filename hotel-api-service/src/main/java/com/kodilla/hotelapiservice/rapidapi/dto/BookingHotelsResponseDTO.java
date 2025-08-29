package com.kodilla.hotelapiservice.rapidapi.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public record BookingHotelsResponseDTO(
        String name,
        String countryCode,
        String city,
        BigDecimal price,
        String currency,
        LocalDate checkInDate,
        LocalDate checkOutDate,
        double reviewScore,
        String reviewScoreWord,
        int reviewCount
) {}
