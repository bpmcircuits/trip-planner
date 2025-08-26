package com.kodilla.tripplanner.nbp.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class NBPRateDTO {

    @JsonProperty("currency")
    private String currencyName;

    @JsonProperty("code")
    private String currencyCode;

    @JsonProperty("mid")
    private BigDecimal value;

}
