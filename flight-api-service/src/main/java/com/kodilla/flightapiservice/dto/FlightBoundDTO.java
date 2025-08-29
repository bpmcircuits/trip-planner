package com.kodilla.flightapiservice.dto;

import java.util.List;

public record FlightBoundDTO(
        String durationIso,
        Integer durationMinutes,
        List<FlightSegmentDTO> segments
) {}
