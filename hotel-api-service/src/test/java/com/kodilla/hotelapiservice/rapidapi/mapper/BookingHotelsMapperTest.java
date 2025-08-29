package com.kodilla.hotelapiservice.rapidapi.mapper;

import com.kodilla.hotelapiservice.rapidapi.dto.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class BookingHotelsMapperTest {

    private BookingHotelsMapper bookingHotelsMapper;

    private BookingHotelsApiDTO hotelApiDTO1;
    private BookingHotelsApiDTO hotelApiDTO2;
    private BookingHotelsSearchResponseApiDTO searchResponseApiDTO;

    @BeforeEach
    void setUp() {
        // Initialize mapper
        bookingHotelsMapper = new BookingHotelsMapper();
        
        // Create test data
        BookingHotelsGrossPriceApiDTO grossPrice1 = new BookingHotelsGrossPriceApiDTO(100.00, "PLN");
        BookingHotelsGrossPriceApiDTO grossPrice2 = new BookingHotelsGrossPriceApiDTO(150.00, "EUR");
        
        BookingHotelsPriceBreakdownApiDTO priceBreakdown1 = new BookingHotelsPriceBreakdownApiDTO(grossPrice1);
        BookingHotelsPriceBreakdownApiDTO priceBreakdown2 = new BookingHotelsPriceBreakdownApiDTO(grossPrice2);
        
        BookingHotelsCheckTimeApiDTO checkIn1 = new BookingHotelsCheckTimeApiDTO(
                LocalDate.now().toString(),
                LocalDate.now().plusDays(5).toString()
        );
        
        BookingHotelsCheckTimeApiDTO checkOut1 = new BookingHotelsCheckTimeApiDTO(
                LocalDate.now().toString(),
                LocalDate.now().plusDays(5).toString()
        );
        
        BookingHotelsCheckTimeApiDTO checkIn2 = new BookingHotelsCheckTimeApiDTO(
                LocalDate.now().toString(),
                LocalDate.now().plusDays(7).toString()
        );
        
        BookingHotelsCheckTimeApiDTO checkOut2 = new BookingHotelsCheckTimeApiDTO(
                LocalDate.now().toString(),
                LocalDate.now().plusDays(7).toString()
        );
        
        String checkInDate1 = LocalDate.now().toString();
        String checkOutDate1 = LocalDate.now().plusDays(5).toString();
        String checkInDate2 = LocalDate.now().toString();
        String checkOutDate2 = LocalDate.now().plusDays(7).toString();
        
        hotelApiDTO1 = new BookingHotelsApiDTO(
                "Test Hotel 1",
                "PLN",
                "Warsaw",
                "PL",
                checkInDate1,
                checkOutDate1,
                8.5,
                "Very Good",
                100,
                checkIn1,
                checkOut1,
                priceBreakdown1
        );
        
        hotelApiDTO2 = new BookingHotelsApiDTO(
                "Test Hotel 2",
                "EUR",
                "Berlin",
                "DE",
                checkInDate2,
                checkOutDate2,
                9.0,
                "Excellent",
                200,
                checkIn2,
                checkOut2,
                priceBreakdown2
        );
        
        searchResponseApiDTO = new BookingHotelsSearchResponseApiDTO(
                true,
                "Successful",
                List.of(hotelApiDTO1, hotelApiDTO2)
        );
    }

    @Test
    void shouldMapToHotelInfoList() {
        // When
        List<BookingHotelsResponseDTO> result = bookingHotelsMapper.mapToHotelInfoList(searchResponseApiDTO);

        // Then
        assertNotNull(result);
        assertEquals(2, result.size());
        
        // Verify first hotel
        assertEquals("Test Hotel 1", result.get(0).name());
        assertEquals("PL", result.get(0).countryCode());
        assertEquals("Warsaw", result.get(0).city());
        assertEquals(0, new BigDecimal("100.00").compareTo(result.get(0).price()), "Price should be equal regardless of scale");
        assertEquals("PLN", result.get(0).currency());
        assertEquals(LocalDate.now(), result.get(0).checkInDate());
        assertEquals(LocalDate.now().plusDays(5), result.get(0).checkOutDate());
        assertEquals(8.5, result.get(0).reviewScore());
        assertEquals("Very Good", result.get(0).reviewScoreWord());
        assertEquals(100, result.get(0).reviewCount());
        
        // Verify second hotel
        assertEquals("Test Hotel 2", result.get(1).name());
        assertEquals("DE", result.get(1).countryCode());
        assertEquals("Berlin", result.get(1).city());
        assertEquals(0, new BigDecimal("150.00").compareTo(result.get(1).price()), "Price should be equal regardless of scale");
        assertEquals("EUR", result.get(1).currency());
        assertEquals(LocalDate.now(), result.get(1).checkInDate());
        assertEquals(LocalDate.now().plusDays(7), result.get(1).checkOutDate());
        assertEquals(9.0, result.get(1).reviewScore());
        assertEquals("Excellent", result.get(1).reviewScoreWord());
        assertEquals(200, result.get(1).reviewCount());
    }

    @Test
    void shouldMapToHotelInfo() {
        // When
        BookingHotelsResponseDTO result = bookingHotelsMapper.mapToHotelInfo(hotelApiDTO1);

        // Then
        assertNotNull(result);
        assertEquals("Test Hotel 1", result.name());
        assertEquals("PL", result.countryCode());
        assertEquals("Warsaw", result.city());
        assertEquals(0, new BigDecimal("100.00").compareTo(result.price()), "Price should be equal regardless of scale");
        assertEquals("PLN", result.currency());
        assertEquals(LocalDate.now(), result.checkInDate());
        assertEquals(LocalDate.now().plusDays(5), result.checkOutDate());
        assertEquals(8.5, result.reviewScore());
        assertEquals("Very Good", result.reviewScoreWord());
        assertEquals(100, result.reviewCount());
    }

    @Test
    void shouldHandleNullDates() {
        // Given
        BookingHotelsGrossPriceApiDTO grossPrice = new BookingHotelsGrossPriceApiDTO(100.00, "PLN");
        BookingHotelsPriceBreakdownApiDTO priceBreakdown = new BookingHotelsPriceBreakdownApiDTO(grossPrice);
        
        BookingHotelsCheckTimeApiDTO checkIn = new BookingHotelsCheckTimeApiDTO(null, null);
        BookingHotelsCheckTimeApiDTO checkOut = new BookingHotelsCheckTimeApiDTO(null, null);
        
        BookingHotelsApiDTO hotelApiDTOWithNullDates = new BookingHotelsApiDTO(
                "Test Hotel",
                "PLN",
                "Warsaw",
                "PL",
                null,
                null,
                8.5,
                "Very Good",
                100,
                checkIn,
                checkOut,
                priceBreakdown
        );

        // When
        BookingHotelsResponseDTO result = bookingHotelsMapper.mapToHotelInfo(hotelApiDTOWithNullDates);

        // Then
        assertNotNull(result);
        assertNull(result.checkInDate());
        assertNull(result.checkOutDate());
    }
}