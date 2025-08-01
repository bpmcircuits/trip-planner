package com.kodilla.tripplanner.dto;

import java.util.List;

public record TripDTO(Long id,
                      String name,
                      String description,
                      String createdAt,
                      Long flightId,
                      Long hotelId,
                      List<Long> travelerIds) {}
