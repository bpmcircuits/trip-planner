package com.kodilla.tripplanner.dto;

public record FlightDTO(Long id,
                        String flightNumber,
                        String departureAirport,
                        String departureAirportCode,
                        String arrivalAirport,
                        String arrivalAirportCode,
                        String departureDate,
                        String departureTime,
                        String arrivalDate,
                        String arrivalTime) {
}
