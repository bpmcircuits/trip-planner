package com.kodilla.tripplanner.rapidapi.kiwiflights.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class OutboundDTO {
    private List<SectorSegmentsDTO> sectorSegments;
}
