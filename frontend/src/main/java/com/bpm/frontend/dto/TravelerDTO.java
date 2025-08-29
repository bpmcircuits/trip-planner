package com.bpm.frontend.dto;

public record TravelerDTO (
        Long id,
        String firstName,
        String lastName,
        String gender,
        String personType,
        String baggageType
) {}
