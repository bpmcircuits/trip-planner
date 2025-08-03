package com.kodilla.tripplanner.rapidapi.kiwiflights.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.math.BigDecimal;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class TierPriceDTO {
    private BigDecimal amount;
}
