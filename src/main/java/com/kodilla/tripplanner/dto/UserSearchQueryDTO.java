package com.kodilla.tripplanner.dto;

import java.time.LocalDate;

public record UserSearchQueryDTO(String departure,
                                 String arrival,
                                 LocalDate departureDate,
                                 LocalDate returnDate,
                                 int adults,
                                 int children,
                                 int infants,
                                 int checkedBaggage,
                                 int holdBaggage) {

}
