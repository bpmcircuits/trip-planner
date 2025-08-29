package com.kodilla.flightapiservice.amadeus.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record AmadeusTokenResponse(@JsonProperty("access_token") String accessToken,
                                   @JsonProperty("token_type") String tokenType,
                                   @JsonProperty("expires_in") long expiresInSeconds) {
}
