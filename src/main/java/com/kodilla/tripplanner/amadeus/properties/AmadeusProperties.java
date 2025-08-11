package com.kodilla.tripplanner.amadeus.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
@Getter
@Setter
public class AmadeusProperties {
    @Value("${amadeus.api.endpoint.prod")
    private String baseUrl;
    @Value("${amadeus.token.url}")
    private String tokenUrl;
    @Value("${amadeus.client.id}")
    private String clientId;
    @Value("${amadeus.client.secret}")
    private String clientSecret;
    @Value("${amadeus.refresh.token.seconds}")
    private int refreshSkewSeconds;
}
