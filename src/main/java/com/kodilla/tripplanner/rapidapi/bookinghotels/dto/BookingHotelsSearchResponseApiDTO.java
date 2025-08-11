package com.kodilla.tripplanner.rapidapi.bookinghotels.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record BookingHotelsSearchResponseApiDTO(
    boolean status,
    String message,
    List<BookingHotelsApiDTO> data
) {}
