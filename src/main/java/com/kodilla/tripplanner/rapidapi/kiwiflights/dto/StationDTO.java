package com.kodilla.tripplanner.rapidapi.kiwiflights.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class StationDTO {
    private String id;
    private String name;
    private String code;
    private CityDTO city;
    private CountryDTO country;
}
