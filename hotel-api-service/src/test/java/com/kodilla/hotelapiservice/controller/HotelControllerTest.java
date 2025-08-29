package com.kodilla.hotelapiservice.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.kodilla.hotelapiservice.domain.Hotel;
import com.kodilla.hotelapiservice.exception.*;
import com.kodilla.hotelapiservice.mapper.HotelMapper;
import com.kodilla.hotelapiservice.rapidapi.dto.*;
import com.kodilla.hotelapiservice.rapidapi.mapper.BookingHotelsMapper;
import com.kodilla.hotelapiservice.service.BookingService;
import com.kodilla.hotelapiservice.service.HotelService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class HotelControllerTest {

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ISO_LOCAL_DATE;

    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    @Mock
    private BookingService bookingService;

    @Mock
    private HotelService hotelService;

    @Mock
    private BookingHotelsMapper bookingHotelsMapper;

    @Mock
    private HotelMapper hotelMapper;

    @InjectMocks
    private HotelController hotelController;

    private Hotel hotel;
    private BookingHotelsResponseDTO hotelResponseDTO;
    private BookingHotelsRequestDTO hotelRequestDTO;
    private BookingHotelsSearchResponseApiDTO searchResponseApiDTO;
    private LocalDate checkInDate;
    private LocalDate checkOutDate;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(hotelController)
                .setControllerAdvice(new GlobalHttpErrorHandler())
                .build();

        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        // Initialize test data
        checkInDate = LocalDate.now();
        checkOutDate = checkInDate.plusDays(5);

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

        hotelRequestDTO = new BookingHotelsRequestDTO(
                "Warsaw",
                checkInDate.format(DATE_FORMATTER),
                checkOutDate.format(DATE_FORMATTER),
                2,
                "PLN",
                "en-us"
        );

        // Initialize searchResponseApiDTO here for all tests
        searchResponseApiDTO = createMockSearchResponseApiDTO();
    }

    private BookingHotelsSearchResponseApiDTO createMockSearchResponseApiDTO() {
        BookingHotelsGrossPriceApiDTO grossPrice = new BookingHotelsGrossPriceApiDTO(100.00, "PLN");
        BookingHotelsPriceBreakdownApiDTO priceBreakdown = new BookingHotelsPriceBreakdownApiDTO(grossPrice);

        BookingHotelsCheckTimeApiDTO checkIn = new BookingHotelsCheckTimeApiDTO(
                checkInDate.format(DATE_FORMATTER),
                checkOutDate.format(DATE_FORMATTER)
        );

        BookingHotelsCheckTimeApiDTO checkOut = new BookingHotelsCheckTimeApiDTO(
                checkInDate.format(DATE_FORMATTER),
                checkOutDate.format(DATE_FORMATTER)
        );

        BookingHotelsApiDTO hotelApiDTO = new BookingHotelsApiDTO(
                "Test Hotel",
                "PLN",
                "Warsaw",
                "PL",
                checkInDate.format(DATE_FORMATTER),
                checkOutDate.format(DATE_FORMATTER),
                8.5,
                "Very Good",
                100,
                checkIn,
                checkOut,
                priceBreakdown
        );

        return new BookingHotelsSearchResponseApiDTO(
                true,
                "Successful",
                List.of(hotelApiDTO)
        );
    }

    @Test
    void shouldSearchHotels() throws Exception {
        // Given
        when(bookingService.searchHotels(any(BookingHotelsRequestDTO.class))).thenReturn(searchResponseApiDTO);
        when(bookingHotelsMapper.mapToHotelInfoList(any(BookingHotelsSearchResponseApiDTO.class)))
                .thenReturn(List.of(hotelResponseDTO));

        // When & Then
        mockMvc.perform(post("/api/v1/hotels/hotel-offer")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(hotelRequestDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].name", is("Test Hotel")))
                .andExpect(jsonPath("$[0].countryCode", is("PL")))
                .andExpect(jsonPath("$[0].city", is("Warsaw")));

        verify(bookingService, times(1)).searchHotels(any(BookingHotelsRequestDTO.class));
        verify(bookingHotelsMapper, times(1)).mapToHotelInfoList(any(BookingHotelsSearchResponseApiDTO.class));
    }

    @Test
    void shouldHandleExceptionWhenSearchingHotels() throws Exception {
        // Given
        when(bookingService.searchHotels(any(BookingHotelsRequestDTO.class)))
                .thenThrow(new BookingApiException("API connection error"));

        // When & Then
        mockMvc.perform(post("/api/v1/hotels/hotel-offer")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(hotelRequestDTO)))
                .andExpect(status().isServiceUnavailable());

        verify(bookingService, times(1)).searchHotels(any(BookingHotelsRequestDTO.class));
        verify(bookingHotelsMapper, never()).mapToHotelInfoList(any());
    }

    @Test
    void shouldHandleDestinationNotFoundException() throws Exception {
        // Given
        when(bookingService.searchHotels(any(BookingHotelsRequestDTO.class)))
                .thenThrow(new DestinationNotFoundException("No destination found"));

        // When & Then
        mockMvc.perform(post("/api/v1/hotels/hotel-offer")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(hotelRequestDTO)))
                .andExpect(status().isNotFound());

        verify(bookingService, times(1)).searchHotels(any(BookingHotelsRequestDTO.class));
        verify(bookingHotelsMapper, never()).mapToHotelInfoList(any());
    }

    @Test
    void shouldSearchAndSaveHotels() throws Exception {
        // Given
        when(bookingService.searchAndSaveHotels(any(BookingHotelsRequestDTO.class))).thenReturn(List.of(hotel));
        when(hotelMapper.mapToDtoList(anyList())).thenReturn(List.of(hotelResponseDTO));

        // When & Then
        mockMvc.perform(post("/api/v1/hotels/hotel-offer/save")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(hotelRequestDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].name", is("Test Hotel")))
                .andExpect(jsonPath("$[0].countryCode", is("PL")))
                .andExpect(jsonPath("$[0].city", is("Warsaw")));

        verify(bookingService, times(1)).searchAndSaveHotels(any(BookingHotelsRequestDTO.class));
        verify(hotelMapper, times(1)).mapToDtoList(anyList());
    }

    @Test
    void shouldHandleExceptionWhenSearchingAndSavingHotels() throws Exception {
        // Given
        when(bookingService.searchAndSaveHotels(any(BookingHotelsRequestDTO.class)))
                .thenThrow(new DatabaseOperationException("Database error"));

        // When & Then
        mockMvc.perform(post("/api/v1/hotels/hotel-offer/save")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(hotelRequestDTO)))
                .andExpect(status().isInternalServerError());

        verify(bookingService, times(1)).searchAndSaveHotels(any(BookingHotelsRequestDTO.class));
        verify(hotelMapper, never()).mapToDtoList(anyList());
    }

    @Test
    void shouldGetAllHotels() throws Exception {
        // Given
        when(hotelService.getAllHotels()).thenReturn(List.of(hotel));
        when(hotelMapper.mapToDtoList(anyList())).thenReturn(List.of(hotelResponseDTO));

        // When & Then
        mockMvc.perform(get("/api/v1/hotels")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].name", is("Test Hotel")))
                .andExpect(jsonPath("$[0].countryCode", is("PL")))
                .andExpect(jsonPath("$[0].city", is("Warsaw")));

        verify(hotelService, times(1)).getAllHotels();
        verify(hotelMapper, times(1)).mapToDtoList(anyList());
    }

    @Test
    void shouldReturnEmptyListWhenNoHotelsExist() throws Exception {
        // Given
        when(hotelService.getAllHotels()).thenReturn(Collections.emptyList());
        when(hotelMapper.mapToDtoList(Collections.emptyList())).thenReturn(Collections.emptyList());

        // When & Then
        mockMvc.perform(get("/api/v1/hotels")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));

        verify(hotelService, times(1)).getAllHotels();
        verify(hotelMapper, times(1)).mapToDtoList(Collections.emptyList());
    }

    @Test
    void shouldGetHotelDetails() throws Exception {
        // Given
        when(hotelService.getHotelById(1L)).thenReturn(hotel);
        when(hotelMapper.mapToDto(any(Hotel.class))).thenReturn(hotelResponseDTO);

        // When & Then
        mockMvc.perform(get("/api/v1/hotels/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is("Test Hotel")))
                .andExpect(jsonPath("$.countryCode", is("PL")))
                .andExpect(jsonPath("$.city", is("Warsaw")));

        verify(hotelService, times(1)).getHotelById(1L);
        verify(hotelMapper, times(1)).mapToDto(any(Hotel.class));
    }

    @Test
    void shouldReturnNotFoundWhenHotelDoesNotExist() throws Exception {
        // Given
        when(hotelService.getHotelById(1L)).thenThrow(new HotelNotFoundException("Hotel with id 1 not found"));

        // When & Then
        mockMvc.perform(get("/api/v1/hotels/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());

        verify(hotelService, times(1)).getHotelById(1L);
        verify(hotelMapper, never()).mapToDto(any(Hotel.class));
    }

    @Test
    void shouldDeleteHotel() throws Exception {
        // Given
        doNothing().when(hotelService).deleteHotel(1L);

        // When & Then
        mockMvc.perform(delete("/api/v1/hotels/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        verify(hotelService, times(1)).deleteHotel(1L);
    }

    @Test
    void shouldHandleExceptionWhenDeletingNonExistentHotel() throws Exception {
        // Given
        doThrow(new HotelNotFoundException("Hotel with id 99 not found"))
                .when(hotelService).deleteHotel(99L);

        // When & Then
        mockMvc.perform(delete("/api/v1/hotels/99")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());

        verify(hotelService, times(1)).deleteHotel(99L);
    }

    @Test
    void shouldRemoveOutdatedOffers() throws Exception {
        // Given
        when(hotelService.removeOutdatedOffers()).thenReturn(5);

        // When & Then
        mockMvc.perform(delete("/api/v1/hotels/outdated")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", is("Removed 5 outdated hotel offers")));

        verify(hotelService, times(1)).removeOutdatedOffers();
    }
}