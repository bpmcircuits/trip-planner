package com.kodilla.tripplanner.rapidapi.bookinghotels.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record BookingHotelsCheckTimeApiDTO(
    String fromTime,
    String untilTime
) {}
