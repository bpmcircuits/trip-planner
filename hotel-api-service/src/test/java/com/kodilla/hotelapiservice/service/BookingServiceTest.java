package com.kodilla.hotelapiservice.service;

import com.kodilla.hotelapiservice.domain.Hotel;
import com.kodilla.hotelapiservice.exception.BookingApiException;
import com.kodilla.hotelapiservice.exception.DatabaseOperationException;
import com.kodilla.hotelapiservice.exception.DestinationNotFoundException;
import com.kodilla.hotelapiservice.rapidapi.client.BookingHotelsClient;
import com.kodilla.hotelapiservice.rapidapi.dto.*;
import com.kodilla.hotelapiservice.rapidapi.mapper.BookingHotelsMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BookingServiceTest {

    @Mock
    private BookingHotelsClient bookingHotelsClient;

    @Mock
    private BookingHotelsMapper bookingHotelsMapper;

    @Mock
    private HotelService hotelService;

    @InjectMocks
    private BookingService bookingService;

    private BookingHotelsRequestDTO requestDTO;
    private BookingHotelsDestinationResponseApiDTO destinationResponseApiDTO;
    private BookingHotelsDestinationApiDTO destinationApiDTO;
    private BookingHotelsSearchResponseApiDTO searchResponseApiDTO;
    private BookingHotelsResponseDTO hotelResponseDTO;
    private Hotel hotel;

    @BeforeEach
    void setUp() {
        LocalDate checkInDate = LocalDate.now();
        LocalDate checkOutDate = checkInDate.plusDays(5);

        requestDTO = new BookingHotelsRequestDTO(
                "Warsaw",
                checkInDate.toString(),
                checkOutDate.toString(),
                2,
                "PLN",
                "en-us"
        );

        destinationApiDTO = new BookingHotelsDestinationApiDTO(
                "city",
                "123456"
        );

        destinationResponseApiDTO = new BookingHotelsDestinationResponseApiDTO(
                true,
                "Successful",
                List.of(destinationApiDTO)
        );

        // Mock searchResponseApiDTO will be initialized in specific tests as needed

        hotelResponseDTO = new BookingHotelsResponseDTO(
                "Test Hotel",
                "PL",
                "Warsaw",
                new BigDecimal("100.00"),
                "PLN",
                checkInDate,
                checkOutDate,
                8.5,
                "Very Good",
                100
        );

        hotel = Hotel.builder()
                .id(1L)
                .name("Test Hotel")
                .countryCode("PL")
                .city("Warsaw")
                .price(new BigDecimal("100.00"))
                .currency("PLN")
                .checkInDate(checkInDate)
                .checkOutDate(checkOutDate)
                .reviewScore(8.5)
                .reviewScoreWord("Very Good")
                .reviewCount(100)
                .lastUpdated(LocalDateTime.now())
                .build();
    }

    @Test
    void shouldSearchHotels() throws DestinationNotFoundException, BookingApiException {
        // Given
        // Initialize searchResponseApiDTO
        BookingHotelsApiDTO hotelApiDTO = new BookingHotelsApiDTO(
                "Test Hotel",
                "PLN",
                "Warsaw",
                "PL",
                LocalDate.now().toString(),
                LocalDate.now().plusDays(5).toString(),
                8.5,
                "Very Good",
                100,
                null,
                null,
                null
        );
        
        searchResponseApiDTO = new BookingHotelsSearchResponseApiDTO(
                true,
                "Successful",
                List.of(hotelApiDTO)
        );
        
        when(bookingHotelsClient.getAutoComplete(anyString())).thenReturn(destinationResponseApiDTO);
        when(bookingHotelsClient.getHotelResults(any(BookingHotelsDestinationApiDTO.class), any(BookingHotelsRequestDTO.class)))
                .thenReturn(searchResponseApiDTO);

        // When
        BookingHotelsSearchResponseApiDTO result = bookingService.searchHotels(requestDTO);

        // Then
        assertNotNull(result);
        assertEquals(searchResponseApiDTO, result);
        verify(bookingHotelsClient, times(1)).getAutoComplete(anyString());
        verify(bookingHotelsClient, times(1)).getHotelResults(any(BookingHotelsDestinationApiDTO.class), any(BookingHotelsRequestDTO.class));
    }

    @Test
    void shouldThrowExceptionWhenNoDestinationFound() {
        // Given
        BookingHotelsDestinationResponseApiDTO emptyResponse = new BookingHotelsDestinationResponseApiDTO(
                true,
                "Successful",
                List.of()
        );
        when(bookingHotelsClient.getAutoComplete(anyString())).thenReturn(emptyResponse);

        // When & Then
        Exception exception = assertThrows(DestinationNotFoundException.class, () -> {
            bookingService.searchHotels(requestDTO);
        });

        assertEquals("No destination found for query: Warsaw", exception.getMessage());
        verify(bookingHotelsClient, times(1)).getAutoComplete(anyString());
        verify(bookingHotelsClient, never()).getHotelResults(any(BookingHotelsDestinationApiDTO.class), any(BookingHotelsRequestDTO.class));
    }

    @Test
    void shouldThrowExceptionWhenDestinationResponseNotSuccessful() {
        // Given
        BookingHotelsDestinationResponseApiDTO failedResponse = new BookingHotelsDestinationResponseApiDTO(
                false,
                "Failed",
                List.of(destinationApiDTO)
        );
        when(bookingHotelsClient.getAutoComplete(anyString())).thenReturn(failedResponse);

        // When & Then
        Exception exception = assertThrows(DestinationNotFoundException.class, () -> {
            bookingService.searchHotels(requestDTO);
        });

        assertEquals("No destination found for query: Warsaw", exception.getMessage());
        verify(bookingHotelsClient, times(1)).getAutoComplete(anyString());
        verify(bookingHotelsClient, never()).getHotelResults(any(BookingHotelsDestinationApiDTO.class), any(BookingHotelsRequestDTO.class));
    }

    @Test
    void shouldSearchAndSaveHotels() throws DestinationNotFoundException, BookingApiException, DatabaseOperationException {
        // Given
        // Initialize searchResponseApiDTO
        BookingHotelsApiDTO hotelApiDTO = new BookingHotelsApiDTO(
                "Test Hotel",
                "PLN",
                "Warsaw",
                "PL",
                LocalDate.now().toString(),
                LocalDate.now().plusDays(5).toString(),
                8.5,
                "Very Good",
                100,
                null,
                null,
                null
        );
        
        searchResponseApiDTO = new BookingHotelsSearchResponseApiDTO(
                true,
                "Successful",
                List.of(hotelApiDTO)
        );
        
        when(bookingHotelsClient.getAutoComplete(anyString())).thenReturn(destinationResponseApiDTO);
        when(bookingHotelsClient.getHotelResults(any(BookingHotelsDestinationApiDTO.class), any(BookingHotelsRequestDTO.class)))
                .thenReturn(searchResponseApiDTO);
        when(bookingHotelsMapper.mapToHotelInfoList(any(BookingHotelsSearchResponseApiDTO.class)))
                .thenReturn(List.of(hotelResponseDTO));
        when(hotelService.saveHotelOffers(anyList())).thenReturn(List.of(hotel));

        // When
        List<Hotel> result = bookingService.searchAndSaveHotels(requestDTO);

        // Then
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Test Hotel", result.get(0).getName());
        verify(bookingHotelsClient, times(1)).getAutoComplete(anyString());
        verify(bookingHotelsClient, times(1)).getHotelResults(any(BookingHotelsDestinationApiDTO.class), any(BookingHotelsRequestDTO.class));
        verify(bookingHotelsMapper, times(1)).mapToHotelInfoList(any(BookingHotelsSearchResponseApiDTO.class));
        verify(hotelService, times(1)).saveHotelOffers(anyList());
    }
}