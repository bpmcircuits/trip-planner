package com.kodilla.tripplanner.dto;

import com.kodilla.tripplanner.domain.Gender;
import com.kodilla.tripplanner.domain.PersonType;

import java.util.List;

public record TravelerDTO(Long id,
                          String firstName,
                          String lastName,
                          Gender gender,
                          PersonType personType,
                          int age,
                          List<Long> baggageIds,
                          Long tripId) {
}
