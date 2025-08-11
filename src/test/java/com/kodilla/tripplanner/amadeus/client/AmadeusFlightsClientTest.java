package com.kodilla.tripplanner.amadeus.client;

import com.kodilla.tripplanner.amadeus.config.AmadeusFlightsConfig;
import com.kodilla.tripplanner.amadeus.dto.AmadeusFlightsAirportSearchDataDTO;
import com.kodilla.tripplanner.amadeus.dto.AmadeusFlightsAirportSearchRequestDTO;
import com.kodilla.tripplanner.amadeus.dto.AmadeusFlightsAirportSearchResponseDTO;
import com.kodilla.tripplanner.amadeus.dto.AmadeusFlightsFlightOfferDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AmadeusFlightsClientTest {

    @Mock
    private RestTemplate restTemplate;

    @Mock
    private AmadeusFlightsConfig config;

    @InjectMocks
    private AmadeusFlightsClient amadeusFlightsClient;

    private final String API_ENDPOINT = "https://test.api.amadeus.com";
    private final String API_KEY = "Bearer test-api-key";

    @BeforeEach
    void setUp() {
        when(config.getAmadeusApiEndpoint()).thenReturn(API_ENDPOINT);
        when(config.getAmadeusApiKey()).thenReturn(API_KEY);
    }

    @Test
    void testGetAirportCode() {
        // Given
        String query = "Warsaw";
        AmadeusFlightsAirportSearchResponseDTO mockResponse = new AmadeusFlightsAirportSearchResponseDTO(List.of());
        
        when(restTemplate.exchange(
                any(),
                eq(HttpMethod.GET),
                any(HttpEntity.class),
                eq(AmadeusFlightsAirportSearchResponseDTO.class)))
                .thenReturn(new ResponseEntity<>(mockResponse, HttpStatus.OK));

        // When
        AmadeusFlightsAirportSearchResponseDTO result = amadeusFlightsClient.getAirportCode(query);

        // Then
        assertNotNull(result);
        assertEquals(mockResponse, result);
    }

    @Test
    void testGetFlightOffer() {
        // Given
        AmadeusFlightsAirportSearchRequestDTO requestDTO = new AmadeusFlightsAirportSearchRequestDTO(
                "WAW",
                "LHR",
                "2025-09-01",
                "2025-09-10",
                2,
                0,
                0,
                "USD"
        );
        
        AmadeusFlightsFlightOffersDataDTO mockResponse = new AmadeusFlightsFlightOffersDataDTO(List.of());
        
        when(restTemplate.exchange(
                any(),
                eq(HttpMethod.GET),
                any(HttpEntity.class),
                eq(AmadeusFlightsFlightOffersDataDTO.class)))
                .thenReturn(new ResponseEntity<>(mockResponse, HttpStatus.OK));

        // When
        AmadeusFlightsFlightOffersDataDTO result = amadeusFlightsClient.getFlightOffer(requestDTO);

        // Then
        assertNotNull(result);
        assertEquals(mockResponse, result);
    }
}