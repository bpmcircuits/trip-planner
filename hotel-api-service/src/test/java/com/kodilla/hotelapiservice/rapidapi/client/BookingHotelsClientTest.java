package com.kodilla.hotelapiservice.rapidapi.client;

import com.kodilla.hotelapiservice.rapidapi.config.BookingHotelsConfig;
import com.kodilla.hotelapiservice.rapidapi.dto.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.http.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class BookingHotelsClientTest {

    @Mock
    private BookingHotelsConfig config;

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private BookingHotelsClient bookingHotelsClient;

    private BookingHotelsDestinationApiDTO destinationApiDTO;
    private BookingHotelsDestinationResponseApiDTO destinationResponseApiDTO;
    private BookingHotelsSearchResponseApiDTO searchResponseApiDTO;
    private BookingHotelsRequestDTO requestDTO;

    @BeforeEach
    void setUp() {
        // Konfiguracja mocków
        when(config.getBookingHotelsApiEndpoint()).thenReturn("https://booking-com.p.rapidapi.com/v1/hotels");
        when(config.getBookingApiKey()).thenReturn("test-api-key");

        // Tworzenie danych testowych
        destinationApiDTO = new BookingHotelsDestinationApiDTO(
                "city",
                "123456"
        );

        destinationResponseApiDTO = new BookingHotelsDestinationResponseApiDTO(
                true,
                "Successful",
                List.of(destinationApiDTO)
        );

        // Tworzenie minimalnego BookingHotelsApiDTO do testów
        BookingHotelsGrossPriceApiDTO grossPrice = new BookingHotelsGrossPriceApiDTO(100.00, "PLN");
        BookingHotelsPriceBreakdownApiDTO priceBreakdown = new BookingHotelsPriceBreakdownApiDTO(grossPrice);
        BookingHotelsCheckTimeApiDTO checkIn = new BookingHotelsCheckTimeApiDTO("2025-08-20", "12:00");
        BookingHotelsCheckTimeApiDTO checkOut = new BookingHotelsCheckTimeApiDTO("2025-08-25", "12:00");

        BookingHotelsApiDTO hotelApiDTO = new BookingHotelsApiDTO(
                "Test Hotel",
                "PLN",
                "Warsaw",
                "PL",
                "2025-08-20",
                "2025-08-25",
                8.5,
                "Very Good",
                100,
                checkIn,
                checkOut,
                priceBreakdown
        );

        searchResponseApiDTO = new BookingHotelsSearchResponseApiDTO(
                true,
                "Successful",
                List.of(hotelApiDTO)
        );

        requestDTO = new BookingHotelsRequestDTO(
                "Warsaw",
                "2025-08-20",
                "2025-08-25",
                2,
                "PLN",
                "en-us"
        );
    }

    @Test
    void shouldGetAutoComplete() {
        // Given
        ResponseEntity<BookingHotelsDestinationResponseApiDTO> responseEntity =
                ResponseEntity.ok(destinationResponseApiDTO);

        doReturn(responseEntity).when(restTemplate).exchange(
                argThat(uri -> uri.toString().contains("/auto-complete")),
                eq(HttpMethod.GET),
                any(HttpEntity.class),
                eq(BookingHotelsDestinationResponseApiDTO.class)
        );

        // When
        BookingHotelsDestinationResponseApiDTO result = bookingHotelsClient.getAutoComplete("Warsaw");

        // Then
        assertNotNull(result);
        assertEquals("Successful", result.message());
        assertEquals(1, result.data().size());
        assertEquals("123456", result.data().get(0).destId());

        verify(restTemplate).exchange(
                argThat(uri -> uri.toString().contains("query=Warsaw")),
                eq(HttpMethod.GET),
                any(HttpEntity.class),
                eq(BookingHotelsDestinationResponseApiDTO.class)
        );
    }

    @Test
    void shouldGetHotelResults() {
        // Given
        ResponseEntity<BookingHotelsSearchResponseApiDTO> responseEntity =
                ResponseEntity.ok(searchResponseApiDTO);

        doReturn(responseEntity).when(restTemplate).exchange(
                argThat(uri -> uri.toString().contains("/search")),
                eq(HttpMethod.GET),
                any(HttpEntity.class),
                eq(BookingHotelsSearchResponseApiDTO.class)
        );

        // When
        BookingHotelsSearchResponseApiDTO result = bookingHotelsClient.getHotelResults(destinationApiDTO, requestDTO);

        // Then
        assertNotNull(result);
        assertEquals("Successful", result.message());
        assertEquals(1, result.data().size());
        assertEquals("Test Hotel", result.data().get(0).name());

        verify(restTemplate).exchange(
                argThat(uri ->
                                uri.toString().contains("locationId=123456") &&
                                uri.toString().contains("checkinDate=2025-08-20") &&
                                uri.toString().contains("checkoutDate=2025-08-25") &&
                                uri.toString().contains("adults=2")
                ),
                eq(HttpMethod.GET),
                any(HttpEntity.class),
                eq(BookingHotelsSearchResponseApiDTO.class)
        );
    }

    @Test
    void shouldHandleEmptyResponseForAutoComplete() {
        // Given
        BookingHotelsDestinationResponseApiDTO emptyResponse = new BookingHotelsDestinationResponseApiDTO(
                true,
                "No results found",
                new ArrayList<>()
        );

        ResponseEntity<BookingHotelsDestinationResponseApiDTO> responseEntity =
                ResponseEntity.ok(emptyResponse);

        doReturn(responseEntity).when(restTemplate).exchange(
                argThat(uri -> uri.toString().contains("/auto-complete")),
                eq(HttpMethod.GET),
                any(HttpEntity.class),
                eq(BookingHotelsDestinationResponseApiDTO.class)
        );

        // When
        BookingHotelsDestinationResponseApiDTO result = bookingHotelsClient.getAutoComplete("NonExistentLocation");

        // Then
        assertNotNull(result);
        assertTrue(result.data().isEmpty());

        verify(restTemplate).exchange(
                argThat(uri -> uri.toString().contains("query=NonExistentLocation")),
                eq(HttpMethod.GET),
                any(HttpEntity.class),
                eq(BookingHotelsDestinationResponseApiDTO.class)
        );
    }

    @Test
    void shouldHandleEmptyResponseForHotelResults() {
        // Given
        BookingHotelsSearchResponseApiDTO emptyResponse = new BookingHotelsSearchResponseApiDTO(
                true,
                "No hotels found",
                new ArrayList<>()
        );

        ResponseEntity<BookingHotelsSearchResponseApiDTO> responseEntity =
                ResponseEntity.ok(emptyResponse);

        doReturn(responseEntity).when(restTemplate).exchange(
                argThat(uri -> uri.toString().contains("/search")),
                eq(HttpMethod.GET),
                any(HttpEntity.class),
                eq(BookingHotelsSearchResponseApiDTO.class)
        );

        // When
        BookingHotelsSearchResponseApiDTO result = bookingHotelsClient.getHotelResults(destinationApiDTO, requestDTO);

        // Then
        assertNotNull(result);
        assertTrue(result.data().isEmpty());
    }

    @Test
    void shouldHandleClientErrorForAutoComplete() {
        // Given
        doThrow(new HttpClientErrorException(HttpStatus.BAD_REQUEST))
                .when(restTemplate).exchange(
                        argThat(uri -> uri.toString().contains("/auto-complete")),
                        eq(HttpMethod.GET),
                        any(HttpEntity.class),
                        eq(BookingHotelsDestinationResponseApiDTO.class)
                );

        // When & Then
        assertThrows(HttpClientErrorException.class, () ->
                bookingHotelsClient.getAutoComplete("Invalid?Query")
        );
    }

    @Test
    void shouldHandleServerErrorForHotelResults() {
        // Given
        doThrow(new HttpServerErrorException(HttpStatus.INTERNAL_SERVER_ERROR))
                .when(restTemplate).exchange(
                        argThat(uri -> uri.toString().contains("/search")),
                        eq(HttpMethod.GET),
                        any(HttpEntity.class),
                        eq(BookingHotelsSearchResponseApiDTO.class)
                );

        // When & Then
        assertThrows(HttpServerErrorException.class, () ->
                bookingHotelsClient.getHotelResults(destinationApiDTO, requestDTO)
        );
    }

    @Test
    void shouldAddCorrectHeadersToRequest() {
        // Given
        ResponseEntity<BookingHotelsDestinationResponseApiDTO> responseEntity =
                ResponseEntity.ok(destinationResponseApiDTO);

        // When
        doReturn(responseEntity).when(restTemplate).exchange(
                argThat(uri -> uri.toString().contains("/auto-complete")),
                eq(HttpMethod.GET),
                argThat(entity -> {
                    HttpHeaders headers = entity.getHeaders();
                    return headers.get("x-rapidapi-key").contains("test-api-key") &&
                            headers.getAccept().contains(MediaType.APPLICATION_JSON);
                }),
                eq(BookingHotelsDestinationResponseApiDTO.class)
        );

        // Then
        BookingHotelsDestinationResponseApiDTO result = bookingHotelsClient.getAutoComplete("Warsaw");
        assertNotNull(result);
    }
}