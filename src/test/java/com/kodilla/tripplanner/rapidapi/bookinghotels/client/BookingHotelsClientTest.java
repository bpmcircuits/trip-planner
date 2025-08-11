package com.kodilla.tripplanner.rapidapi.bookinghotels.client;

import com.kodilla.tripplanner.dto.HotelSearchRequestDTO;
import com.kodilla.tripplanner.rapidapi.bookinghotels.config.BookingHotelsConfig;
import com.kodilla.tripplanner.rapidapi.bookinghotels.dto.BookingHotelsDestinationApiDTO;
import com.kodilla.tripplanner.rapidapi.bookinghotels.dto.BookingHotelsDestinationResponseApiDTO;
import com.kodilla.tripplanner.rapidapi.bookinghotels.dto.BookingHotelsSearchResponseApiDTO;
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
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BookingHotelsClientTest {

    @Mock
    private BookingHotelsConfig config;

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private BookingHotelsClient bookingHotelsClient;

    private final String API_ENDPOINT = "https://booking-com.p.rapidapi.com/v1/hotels";
    private final String API_KEY = "test-api-key";

    @BeforeEach
    void setUp() {
        when(config.getBookingHotelsApiEndpoint()).thenReturn(API_ENDPOINT);
        when(config.getBookingApiKey()).thenReturn(API_KEY);
    }

    @Test
    void testGetAutoComplete() {
        // Given
        String query = "Warsaw";
        BookingHotelsDestinationResponseApiDTO mockResponse = new BookingHotelsDestinationResponseApiDTO(true, "Success", List.of());
        
        when(restTemplate.exchange(
                any(),
                eq(HttpMethod.GET),
                any(HttpEntity.class),
                eq(BookingHotelsDestinationResponseApiDTO.class)))
                .thenReturn(new ResponseEntity<>(mockResponse, HttpStatus.OK));

        // When
        BookingHotelsDestinationResponseApiDTO result = bookingHotelsClient.getAutoComplete(query);

        // Then
        assertNotNull(result);
        assertEquals(mockResponse, result);
    }

    @Test
    void testGetHotelResults() {
        // Given
        BookingHotelsDestinationApiDTO destination = new BookingHotelsDestinationApiDTO("city", "123");
        HotelSearchRequestDTO request = new HotelSearchRequestDTO(
                "Warsaw",
                "2025-08-15",
                "2025-08-20",
                2,
                "USD",
                "en-us"
        );
        
        BookingHotelsSearchResponseApiDTO mockResponse = new BookingHotelsSearchResponseApiDTO(true, "Success", List.of());
        
        when(restTemplate.exchange(
                any(),
                eq(HttpMethod.GET),
                any(HttpEntity.class),
                eq(BookingHotelsSearchResponseApiDTO.class)))
                .thenReturn(new ResponseEntity<>(mockResponse, HttpStatus.OK));

        // When
        BookingHotelsSearchResponseApiDTO result = bookingHotelsClient.getHotelResults(destination, request);

        // Then
        assertNotNull(result);
        assertEquals(mockResponse, result);
    }
}