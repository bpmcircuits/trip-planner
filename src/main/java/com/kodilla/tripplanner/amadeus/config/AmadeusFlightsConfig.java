package com.kodilla.tripplanner.amadeus.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Getter
public class AmadeusFlightsConfig {

    @Value("${amadeus.api.endpoint.prod}")
    private String amadeusApiEndpoint;

    @Value("${amadeus.api.key}")
    private String amadeusApiKey;
}
