package com.kodilla.tripplanner.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public record HotelDTO(Long id,
                       String name,
                       String country,
                       String city,
                       LocalDate checkInDate,
                       LocalDate checkOutDate,
                       BigDecimal price) {
}
