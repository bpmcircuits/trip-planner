package com.kodilla.tripplanner.rapidapi.kiwiflights.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ItineraryRoundTripDTO {
    private PriceDTO priceEur;
    private BagsInfoDTO bagsInfo;
    private OutboundDTO outbound;
    private InboundDTO inbound;
}
