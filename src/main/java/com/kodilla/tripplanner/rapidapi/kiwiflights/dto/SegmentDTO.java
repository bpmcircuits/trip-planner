package com.kodilla.tripplanner.rapidapi.kiwiflights.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class SegmentDTO {
    private SourceDTO source;
    private DestinationDTO destination;
    private int duration;
    private int code;
    private CarrierDTO carrier;
    private String cabinClass;
}
