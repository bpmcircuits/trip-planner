package com.kodilla.hotelapiservice.rapidapi.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record BookingHotelsDestinationResponseApiDTO(
    boolean status,
    String message,
    List<BookingHotelsDestinationApiDTO> data
) {}
