package com.bpm.frontend.tripplanerback.dto.travelers;

public record TravelerResponseDTO(Long id,
                                  String firstName,
                                  String lastName,
                                  String gender,
                                  String personType,
                                  int age,
                                  String baggage,
                                  Long tripId) {
}
