package com.kodilla.nbpapiservice.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class NBPTableDTO {

    @JsonProperty("table")
    private String tableName;

    @JsonProperty("no")
    private String number;

    @JsonProperty("effectiveDate")
    private LocalDate effectiveDate;

    @JsonProperty("rates")
    private List<NBPRateDTO> currencies;
}
