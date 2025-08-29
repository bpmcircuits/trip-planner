package com.bpm.frontend.tripplanerback.dto.flights;

public record FlightSegmentDTO(
        String fromIata,                 // "WAW"
        String toIata,                   // "AYT"
        String carrierCode,              // "XQ"
        String departureAt,              // ISO-8601 w UTC lub z TZ, np. "2025-08-09T18:40:00Z"
        String arrivalAt                 // jw.
) {}
