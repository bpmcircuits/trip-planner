package com.kodilla.tripplanner.service;

import com.kodilla.tripplanner.amadeus.client.AmadeusFlightsClient;
import com.kodilla.tripplanner.amadeus.client.AmadeusFlightsFlightOffersDataDTO;
import com.kodilla.tripplanner.amadeus.dto.AmadeusFlightsAirportSearchDataDTO;
import com.kodilla.tripplanner.amadeus.dto.AmadeusFlightsAirportSearchRequestDTO;
import com.kodilla.tripplanner.amadeus.dto.AmadeusFlightsAirportSearchResponseDTO;
import com.kodilla.tripplanner.dto.flights.FlightIataCodeDTO;
import com.kodilla.tripplanner.dto.flights.FlightSearchRequestDTO;
import com.kodilla.tripplanner.dto.flights.FlightSearchResponseDTO;
import com.kodilla.tripplanner.mapper.FlightIataCodeMapper;
import com.kodilla.tripplanner.mapper.FlightOfferMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FlightServiceTest {

    @Mock
    private AmadeusFlightsClient amadeusFlightsClient;

    @Mock
    private FlightIataCodeMapper flightIataCodeMapper;

    @Mock
    private FlightOfferMapper flightOfferMapper;

    @InjectMocks
    private FlightService flightService;

    private FlightSearchRequestDTO flightSearchRequest;
    private FlightIataCodeDTO departureCityIataCode;
    private FlightIataCodeDTO arrivalCityIataCode;
    private AmadeusFlightsAirportSearchRequestDTO amadeusRequest;
    private AmadeusFlightsFlightOffersDataDTO amadeusResponse;
    private List<FlightSearchResponseDTO> expectedFlightOffers;
    private DateTimeFormatter formatter;
    private AmadeusFlightsAirportSearchResponseDTO warsawResponse;
    private AmadeusFlightsAirportSearchResponseDTO londonResponse;

    @BeforeEach
    void setUp() {
        formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate departureDate = LocalDate.now().plusDays(10);
        LocalDate returnDate = LocalDate.now().plusDays(17);
        
        // Setup test data
        flightSearchRequest = new FlightSearchRequestDTO(
                "Warsaw",
                "London",
                departureDate.format(formatter),
                returnDate.format(formatter),
                2,
                1,
                0,
                "PLN"
        );

        departureCityIataCode = new FlightIataCodeDTO("WAW");
        arrivalCityIataCode = new FlightIataCodeDTO("LHR");

        // Create mock responses for airport code searches
        AmadeusFlightsAirportSearchDataDTO warsawData = new AmadeusFlightsAirportSearchDataDTO("WAW");
        warsawResponse = new AmadeusFlightsAirportSearchResponseDTO(Collections.singletonList(warsawData));
        
        AmadeusFlightsAirportSearchDataDTO londonData = new AmadeusFlightsAirportSearchDataDTO("LHR");
        londonResponse = new AmadeusFlightsAirportSearchResponseDTO(Collections.singletonList(londonData));

        amadeusRequest = new AmadeusFlightsAirportSearchRequestDTO(
                "WAW",
                "LHR",
                departureDate.format(formatter),
                returnDate.format(formatter),
                2,
                1,
                0,
                "PLN"
        );

        amadeusResponse = mock(AmadeusFlightsFlightOffersDataDTO.class);

        FlightSearchResponseDTO flightOffer1 = mock(FlightSearchResponseDTO.class);
        FlightSearchResponseDTO flightOffer2 = mock(FlightSearchResponseDTO.class);
        expectedFlightOffers = Arrays.asList(flightOffer1, flightOffer2);
    }

    @Test
    void testGetFlightOffers() {
        // Given
        when(amadeusFlightsClient.getAirportCode("Warsaw")).thenReturn(warsawResponse);
        when(amadeusFlightsClient.getAirportCode("London")).thenReturn(londonResponse);
        when(flightIataCodeMapper.toDto(warsawResponse)).thenReturn(departureCityIataCode);
        when(flightIataCodeMapper.toDto(londonResponse)).thenReturn(arrivalCityIataCode);
        when(flightOfferMapper.mapToAmadeusRequest(any(FlightSearchRequestDTO.class))).thenReturn(amadeusRequest);
        when(amadeusFlightsClient.getFlightOffer(amadeusRequest)).thenReturn(amadeusResponse);
        when(flightOfferMapper.mapAllFromAmadeus(amadeusResponse)).thenReturn(expectedFlightOffers);

        // When
        List<FlightSearchResponseDTO> result = flightService.getFlightOffers(flightSearchRequest);

        // Then
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(expectedFlightOffers, result);

        // Verify interactions
        verify(amadeusFlightsClient, times(1)).getAirportCode("Warsaw");
        verify(amadeusFlightsClient, times(1)).getAirportCode("London");
        verify(flightIataCodeMapper, times(2)).toDto(any(AmadeusFlightsAirportSearchResponseDTO.class));
        verify(flightOfferMapper, times(1)).mapToAmadeusRequest(any(FlightSearchRequestDTO.class));
        verify(amadeusFlightsClient, times(1)).getFlightOffer(amadeusRequest);
        verify(flightOfferMapper, times(1)).mapAllFromAmadeus(amadeusResponse);
    }
}