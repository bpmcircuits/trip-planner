package com.kodilla.tripplanner.rapidapi.kiwiflights.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Getter
public class KiwiConfig {

    @Value("${kiwi.api.endpoint.prod}")
    private String kiwiApiEndpoint;

    @Value("${kiwi.api.key}")
    private String kiwiApiKey;

}
