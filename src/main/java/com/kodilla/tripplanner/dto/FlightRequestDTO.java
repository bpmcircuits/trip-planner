package com.kodilla.tripplanner.dto;

import java.time.LocalDateTime;

public record FlightRequestDTO(String source,
                               String destination,
                               LocalDateTime inboundDepartureDateStart,
                               LocalDateTime inboundDepartureDateEnd,
                               LocalDateTime outboundDepartmentDateStart,
                               LocalDateTime outboundDepartmentDateEnd,
                               Integer adults,
                               Integer children,
                               Integer infants,
                               Integer handBags,
                               Integer holdBags) {}
