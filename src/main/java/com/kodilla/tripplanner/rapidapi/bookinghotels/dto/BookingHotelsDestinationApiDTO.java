package com.kodilla.tripplanner.rapidapi.bookinghotels.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public record BookingHotelsDestinationApiDTO(
    @JsonProperty("dest_type")
    String destType,

    @JsonProperty("id")
    String destId
) {}
