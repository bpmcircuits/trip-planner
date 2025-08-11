package com.kodilla.tripplanner.mapper;

import com.kodilla.tripplanner.amadeus.dto.AmadeusFlightsAirportSearchResponseDTO;
import com.kodilla.tripplanner.dto.flights.FlightIataCodeDTO;
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
