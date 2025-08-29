package com.kodilla.hotelapiservice.service;

import com.kodilla.hotelapiservice.domain.Hotel;
import com.kodilla.hotelapiservice.exception.DatabaseOperationException;
import com.kodilla.hotelapiservice.exception.HotelNotFoundException;
import com.kodilla.hotelapiservice.mapper.HotelMapper;
import com.kodilla.hotelapiservice.rapidapi.dto.BookingHotelsResponseDTO;
import com.kodilla.hotelapiservice.repository.HotelRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class HotelServiceTest {

    @Mock
    private HotelRepository hotelRepository;

    @Mock
    private HotelMapper hotelMapper;

    @InjectMocks
    private HotelService hotelService;

    private Hotel hotel;
    private BookingHotelsResponseDTO hotelResponseDTO;
    private LocalDateTime now;

    @BeforeEach
    void setUp() {
        // Set cleanup days
        ReflectionTestUtils.setField(hotelService, "cleanupDays", 30);

        now = LocalDateTime.now();
        LocalDate checkInDate = LocalDate.now();
        LocalDate checkOutDate = checkInDate.plusDays(5);

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
                .lastUpdated(now)
                .build();

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
    }

    @Test
    void shouldSaveHotelOffers() throws DatabaseOperationException {
        // Given
        List<BookingHotelsResponseDTO> hotelDtos = List.of(hotelResponseDTO);
        when(hotelRepository.findByNameAndCityAndCountryCode(anyString(), anyString(), anyString()))
                .thenReturn(Optional.empty());
        when(hotelMapper.mapToHotel(any(BookingHotelsResponseDTO.class))).thenReturn(hotel);
        when(hotelRepository.save(any(Hotel.class))).thenReturn(hotel);

        // When
        List<Hotel> result = hotelService.saveHotelOffers(hotelDtos);

        // Then
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Test Hotel", result.get(0).getName());
        assertEquals("PL", result.get(0).getCountryCode());
        assertEquals("Warsaw", result.get(0).getCity());

        verify(hotelRepository, times(1)).findByNameAndCityAndCountryCode(anyString(), anyString(), anyString());
        verify(hotelMapper, times(1)).mapToHotel(any(BookingHotelsResponseDTO.class));
        verify(hotelRepository, times(1)).save(any(Hotel.class));
    }

    @Test
    void shouldUpdateExistingHotel() throws DatabaseOperationException {
        // Given
        when(hotelRepository.findByNameAndCityAndCountryCode(anyString(), anyString(), anyString()))
                .thenReturn(Optional.of(hotel));
        when(hotelRepository.save(any(Hotel.class))).thenReturn(hotel);

        // When
        Hotel result = hotelService.saveOrUpdateHotel(hotelResponseDTO);

        // Then
        assertNotNull(result);
        assertEquals("Test Hotel", result.getName());
        assertEquals("PL", result.getCountryCode());
        assertEquals("Warsaw", result.getCity());

        verify(hotelRepository, times(1)).findByNameAndCityAndCountryCode(anyString(), anyString(), anyString());
        verify(hotelRepository, times(1)).save(any(Hotel.class));
        verify(hotelMapper, never()).mapToHotel(any(BookingHotelsResponseDTO.class));
    }

    @Test
    void shouldCreateNewHotel() throws DatabaseOperationException {
        // Given
        when(hotelRepository.findByNameAndCityAndCountryCode(anyString(), anyString(), anyString()))
                .thenReturn(Optional.empty());
        when(hotelMapper.mapToHotel(any(BookingHotelsResponseDTO.class))).thenReturn(hotel);
        when(hotelRepository.save(any(Hotel.class))).thenReturn(hotel);

        // When
        Hotel result = hotelService.saveOrUpdateHotel(hotelResponseDTO);

        // Then
        assertNotNull(result);
        assertEquals("Test Hotel", result.getName());
        assertEquals("PL", result.getCountryCode());
        assertEquals("Warsaw", result.getCity());

        verify(hotelRepository, times(1)).findByNameAndCityAndCountryCode(anyString(), anyString(), anyString());
        verify(hotelMapper, times(1)).mapToHotel(any(BookingHotelsResponseDTO.class));
        verify(hotelRepository, times(1)).save(any(Hotel.class));
    }

    @Test
    void shouldRemoveOutdatedOffers() throws DatabaseOperationException {
        // Given
        LocalDateTime cutoffDate = LocalDateTime.now().minusDays(30);
        List<Hotel> outdatedHotels = List.of(hotel);
        when(hotelRepository.findByLastUpdatedBefore(any(LocalDateTime.class))).thenReturn(outdatedHotels);
        doNothing().when(hotelRepository).deleteAll(anyList());

        // When
        int result = hotelService.removeOutdatedOffers();

        // Then
        assertEquals(1, result);
        verify(hotelRepository, times(1)).findByLastUpdatedBefore(any(LocalDateTime.class));
        verify(hotelRepository, times(1)).deleteAll(anyList());
    }

    @Test
    void shouldNotRemoveAnyOffersWhenNoOutdatedOffers() throws DatabaseOperationException {
        // Given
        when(hotelRepository.findByLastUpdatedBefore(any(LocalDateTime.class))).thenReturn(List.of());

        // When
        int result = hotelService.removeOutdatedOffers();

        // Then
        assertEquals(0, result);
        verify(hotelRepository, times(1)).findByLastUpdatedBefore(any(LocalDateTime.class));
        verify(hotelRepository, never()).deleteAll(anyList());
    }

    @Test
    void shouldGetAllHotels() throws DatabaseOperationException {
        // Given
        List<Hotel> hotels = List.of(hotel);
        when(hotelRepository.findAll()).thenReturn(hotels);

        // When
        List<Hotel> result = hotelService.getAllHotels();

        // Then
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Test Hotel", result.get(0).getName());
        verify(hotelRepository, times(1)).findAll();
    }

    @Test
    void shouldGetHotelById() throws DatabaseOperationException, HotelNotFoundException {
        // Given
        when(hotelRepository.findById(1L)).thenReturn(Optional.of(hotel));

        // When
        Hotel result = hotelService.getHotelById(1L);

        // Then
        assertEquals("Test Hotel", result.getName());
        verify(hotelRepository, times(1)).findById(1L);
    }

    @Test
    void shouldReturnEmptyOptionalWhenHotelNotFound() {
        // Given
        when(hotelRepository.findById(1L)).thenReturn(Optional.empty());

        // When & Then
        assertThrows(HotelNotFoundException.class, () -> hotelService.getHotelById(1L));
        verify(hotelRepository, times(1)).findById(1L);
    }

    @Test
    void shouldDeleteHotel() throws DatabaseOperationException, HotelNotFoundException {
        // Given
        when(hotelRepository.existsById(1L)).thenReturn(true);
        doNothing().when(hotelRepository).deleteById(1L);

        // When
        hotelService.deleteHotel(1L);

        // Then
        verify(hotelRepository, times(1)).existsById(1L);
        verify(hotelRepository, times(1)).deleteById(1L);
    }
}