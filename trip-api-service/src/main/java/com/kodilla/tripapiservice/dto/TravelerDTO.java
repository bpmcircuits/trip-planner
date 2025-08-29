package com.kodilla.tripapiservice.dto;


import com.kodilla.tripapiservice.domain.BaggageType;
import com.kodilla.tripapiservice.domain.Gender;
import com.kodilla.tripapiservice.domain.PersonType;

public record TravelerDTO(Long id,
                          String firstName,
                          String lastName,
                          Gender gender,
                          PersonType personType,
                          int age,
                          BaggageType baggage,
                          Long tripId) {
}
