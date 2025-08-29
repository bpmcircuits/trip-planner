package com.kodilla.tripapiservice.dto;

import java.time.LocalDateTime;
import java.util.List;

public record TripDTO(Long id,
                      String name,
                      String description,
                      LocalDateTime createdAt,
                      Long flightId,
                      Long hotelId,
                      List<Long> travelerIds) {}
