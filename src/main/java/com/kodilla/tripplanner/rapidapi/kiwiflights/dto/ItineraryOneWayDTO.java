package com.kodilla.tripplanner.rapidapi.kiwiflights.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ItineraryOneWayDTO {
    private PriceDTO priceEur;
    private BagsInfoDTO bagsInfo;
    private SectorDTO sector;
}
