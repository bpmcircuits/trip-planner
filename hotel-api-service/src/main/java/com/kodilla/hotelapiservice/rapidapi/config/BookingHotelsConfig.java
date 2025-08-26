package com.kodilla.tripplannerhotelapi.rapidapi.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Getter
public class BookingHotelsConfig {
    @Value("${booking.hotels.api.endpoint.prod}")
    private String bookingHotelsApiEndpoint;

    @Value("${booking.api.key}")
    private String bookingApiKey;
}
