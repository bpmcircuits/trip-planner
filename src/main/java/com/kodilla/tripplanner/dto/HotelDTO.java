package com.kodilla.tripplanner.dto;

import java.math.BigDecimal;

public record HotelDTO(Long id,
                       String name,
                       String address,
                       String country,
                       String city,
                       String checkInDate,
                       String checkOutDate,
                       BigDecimal price) {
}
