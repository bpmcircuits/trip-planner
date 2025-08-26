package com.kodilla.tripplanner.dto.flights;

import java.util.List;

public record FlightBoundDTO(
        String durationIso,
        Integer durationMinutes,
        List<FlightSegmentDTO> segments
) {}
