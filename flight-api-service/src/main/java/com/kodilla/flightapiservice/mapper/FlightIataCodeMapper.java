package com.kodilla.flightapiservice.mapper;

import com.kodilla.flightapiservice.amadeus.dto.AmadeusFlightsAirportSearchResponseDTO;
import com.kodilla.flightapiservice.dto.FlightIataCodeDTO;
import org.springframework.stereotype.Service;

@Service
public class FlightIataCodeMapper {

    public FlightIataCodeDTO toDto(AmadeusFlightsAirportSearchResponseDTO response) {
        if (response == null || response.data() == null || response.data().isEmpty()) {
            return new FlightIataCodeDTO(null);
        }
        String iataCode = response.data().getFirst().iataCode();
        return new FlightIataCodeDTO(iataCode);
    }
}