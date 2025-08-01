package com.kodilla.tripplanner.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

public record FlightDTO(Long id,
                        String flightNumber,
                        String departureAirport,
                        String departureAirportCode,
                        String arrivalAirport,
                        String arrivalAirportCode,
                        LocalDateTime departureDateTime,
                        LocalDateTime arrivalDateTime,
                        BigDecimal price,
                        String currency) {
}
