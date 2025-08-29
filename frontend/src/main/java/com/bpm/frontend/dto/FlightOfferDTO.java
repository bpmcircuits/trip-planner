package com.bpm.frontend.dto;

import java.time.LocalDateTime;

public record FlightOfferDTO(String airline,
                             LocalDateTime departureTime,
                             String from,
                             LocalDateTime arrivalTime,
                             String to,
                             String cost) {
}