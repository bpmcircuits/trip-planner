package com.kodilla.tripplanner.dto;

import com.kodilla.tripplanner.domain.BaggageType;
import com.kodilla.tripplanner.domain.Gender;
import com.kodilla.tripplanner.domain.PersonType;

public record TravelerDTO(Long id,
                          String firstName,
                          String lastName,
                          Gender gender,
                          PersonType personType,
                          int age,
                          BaggageType baggage,
                          Long tripId) {
}
