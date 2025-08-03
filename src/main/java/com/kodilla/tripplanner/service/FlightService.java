package com.kodilla.tripplanner.service;

import com.kodilla.tripplanner.dto.FlightRequestDTO;
import com.kodilla.tripplanner.rapidapi.kiwiflights.client.KiwiClient;
import com.kodilla.tripplanner.rapidapi.kiwiflights.dto.KiwiSearchResponseOneWayDTO;
import com.kodilla.tripplanner.rapidapi.kiwiflights.dto.KiwiSearchResponseRoundTripDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FlightService {

    private final KiwiClient kiwiClient;

    public KiwiSearchResponseOneWayDTO getOneWayFlights(FlightRequestDTO flightRequest) {
        return kiwiClient.getOneWayFlights(flightRequest);
    }

    public KiwiSearchResponseRoundTripDTO getRoundTripFlights(FlightRequestDTO flightRequest) {
        return kiwiClient.getRoundTripFlights(flightRequest);
    }
}
