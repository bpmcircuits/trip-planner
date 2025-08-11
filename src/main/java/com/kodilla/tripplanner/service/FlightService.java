package com.kodilla.tripplanner.service;

import com.kodilla.tripplanner.amadeus.client.AmadeusFlightsClient;
import com.kodilla.tripplanner.amadeus.client.AmadeusFlightsFlightOffersDataDTO;
import com.kodilla.tripplanner.amadeus.dto.AmadeusFlightsAirportSearchRequestDTO;
import com.kodilla.tripplanner.dto.flights.FlightIataCodeDTO;
import com.kodilla.tripplanner.dto.flights.FlightSearchRequestDTO;
import com.kodilla.tripplanner.dto.flights.FlightSearchResponseDTO;
import com.kodilla.tripplanner.mapper.FlightIataCodeMapper;
import com.kodilla.tripplanner.mapper.FlightOfferMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FlightService {

    private final AmadeusFlightsClient amadeusFlightsClient;
    private final FlightIataCodeMapper flightIataCodeMapper;
    private final FlightOfferMapper flightOfferMapper;

    public List<FlightSearchResponseDTO> getFlightOffers(FlightSearchRequestDTO flightSearchRequest) {

        FlightSearchRequestDTO requestWithIataCodes = new FlightSearchRequestDTO(
                getIataCode(flightSearchRequest.departureCity()).iataCode(),
                getIataCode(flightSearchRequest.arrivalCity()).iataCode(),
                flightSearchRequest.departureDate(),
                flightSearchRequest.returnDate(),
                flightSearchRequest.adults(),
                flightSearchRequest.children(),
                flightSearchRequest.infants(),
                flightSearchRequest.currencyCode()
        );

        AmadeusFlightsAirportSearchRequestDTO requestDTO = flightOfferMapper.mapToAmadeusRequest(requestWithIataCodes);
        AmadeusFlightsFlightOffersDataDTO flightsOfferData = amadeusFlightsClient.getFlightOffer(requestDTO);
        return flightOfferMapper.mapAllFromAmadeus(flightsOfferData);
    }

    private FlightIataCodeDTO getIataCode(String city) {
        return flightIataCodeMapper.toDto(amadeusFlightsClient.getAirportCode(city));
    }
}