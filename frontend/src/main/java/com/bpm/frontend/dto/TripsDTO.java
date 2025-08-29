package com.bpm.frontend.dto;

import java.time.LocalDate;

public record TripsDTO(
        String id,
        String country,
        String city,
        LocalDate date,
        int numberOfTravelers
){
}
