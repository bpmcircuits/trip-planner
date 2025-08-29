package com.kodilla.hotelapiservice.mapper;

import com.kodilla.hotelapiservice.domain.Hotel;
import com.kodilla.hotelapiservice.rapidapi.dto.BookingHotelsResponseDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class HotelMapperTest {

    private HotelMapper hotelMapper;
    
    @BeforeEach
    void setUp() {
        hotelMapper = new HotelMapper();
    }

    @Test
    void shouldMapToHotel() {
        // Given
        LocalDate checkInDate = LocalDate.now();
        LocalDate checkOutDate = checkInDate.plusDays(5);
        
        BookingHotelsResponseDTO dto = new BookingHotelsResponseDTO(
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

        // When
        Hotel result = hotelMapper.mapToHotel(dto);

        // Then
        assertNotNull(result);
        assertNull(result.getId()); // ID should be null as it's not set by the mapper
        assertEquals("Test Hotel", result.getName());
        assertEquals("PL", result.getCountryCode());
        assertEquals("Warsaw", result.getCity());
        assertEquals(new BigDecimal("100.00"), result.getPrice());
        assertEquals("PLN", result.getCurrency());
        assertEquals(checkInDate, result.getCheckInDate());
        assertEquals(checkOutDate, result.getCheckOutDate());
        assertEquals(8.5, result.getReviewScore());
        assertEquals("Very Good", result.getReviewScoreWord());
        assertEquals(100, result.getReviewCount());
        assertNotNull(result.getLastUpdated()); // lastUpdated should be set to current time
    }

    @Test
    void shouldMapToHotelList() {
        // Given
        LocalDate checkInDate = LocalDate.now();
        LocalDate checkOutDate = checkInDate.plusDays(5);
        
        BookingHotelsResponseDTO dto1 = new BookingHotelsResponseDTO(
                "Test Hotel 1",
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
        
        BookingHotelsResponseDTO dto2 = new BookingHotelsResponseDTO(
                "Test Hotel 2",
                "DE",
                "Berlin",
                new BigDecimal("150.00"),
                "EUR",
                checkInDate,
                checkOutDate,
                9.0,
                "Excellent",
                200
        );
        
        List<BookingHotelsResponseDTO> dtos = List.of(dto1, dto2);

        // When
        List<Hotel> result = hotelMapper.mapToHotelList(dtos);

        // Then
        assertNotNull(result);
        assertEquals(2, result.size());
        
        assertEquals("Test Hotel 1", result.get(0).getName());
        assertEquals("PL", result.get(0).getCountryCode());
        assertEquals("Warsaw", result.get(0).getCity());
        
        assertEquals("Test Hotel 2", result.get(1).getName());
        assertEquals("DE", result.get(1).getCountryCode());
        assertEquals("Berlin", result.get(1).getCity());
    }

    @Test
    void shouldMapToDto() {
        // Given
        LocalDate checkInDate = LocalDate.now();
        LocalDate checkOutDate = checkInDate.plusDays(5);
        
        Hotel hotel = Hotel.builder()
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

        // When
        BookingHotelsResponseDTO result = hotelMapper.mapToDto(hotel);

        // Then
        assertNotNull(result);
        assertEquals("Test Hotel", result.name());
        assertEquals("PL", result.countryCode());
        assertEquals("Warsaw", result.city());
        assertEquals(new BigDecimal("100.00"), result.price());
        assertEquals("PLN", result.currency());
        assertEquals(checkInDate, result.checkInDate());
        assertEquals(checkOutDate, result.checkOutDate());
        assertEquals(8.5, result.reviewScore());
        assertEquals("Very Good", result.reviewScoreWord());
        assertEquals(100, result.reviewCount());
    }

    @Test
    void shouldMapToDtoList() {
        // Given
        LocalDate checkInDate = LocalDate.now();
        LocalDate checkOutDate = checkInDate.plusDays(5);
        
        Hotel hotel1 = Hotel.builder()
                .id(1L)
                .name("Test Hotel 1")
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
        
        Hotel hotel2 = Hotel.builder()
                .id(2L)
                .name("Test Hotel 2")
                .countryCode("DE")
                .city("Berlin")
                .price(new BigDecimal("150.00"))
                .currency("EUR")
                .checkInDate(checkInDate)
                .checkOutDate(checkOutDate)
                .reviewScore(9.0)
                .reviewScoreWord("Excellent")
                .reviewCount(200)
                .lastUpdated(LocalDateTime.now())
                .build();
        
        List<Hotel> hotels = List.of(hotel1, hotel2);

        // When
        List<BookingHotelsResponseDTO> result = hotelMapper.mapToDtoList(hotels);

        // Then
        assertNotNull(result);
        assertEquals(2, result.size());
        
        assertEquals("Test Hotel 1", result.get(0).name());
        assertEquals("PL", result.get(0).countryCode());
        assertEquals("Warsaw", result.get(0).city());
        
        assertEquals("Test Hotel 2", result.get(1).name());
        assertEquals("DE", result.get(1).countryCode());
        assertEquals("Berlin", result.get(1).city());
    }
}