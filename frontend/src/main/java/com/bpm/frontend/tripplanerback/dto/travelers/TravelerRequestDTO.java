package com.bpm.frontend.tripplanerback.dto.travelers;

public record TravelerRequestDTO(String firstName,
                                 String lastName,
                                 String gender,
                                 String personType,
                                 String baggage) {
}
