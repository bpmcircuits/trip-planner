package com.kodilla.flightapiservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kodilla.flightapiservice.domain.Flight;
import com.kodilla.flightapiservice.domain.FlightSegment;
import com.kodilla.flightapiservice.domain.TravelerPrice;
import com.kodilla.flightapiservice.dto.*;
import com.kodilla.flightapiservice.mapper.FlightMapper;
import com.kodilla.flightapiservice.service.FlightService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class FlightControllerTest {

    @Mock
    private FlightService flightService;

    @Mock
    private FlightMapper flightMapper;

    @InjectMocks
    private FlightController flightController;

    private MockMvc mockMvc;
    private ObjectMapper objectMapper;
    private Flight testFlight;
    private FlightSearchResponseDTO testFlightResponseDTO;
    private FlightSearchRequestDTO testFlightRequestDTO;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(flightController).build();
        objectMapper = new ObjectMapper();

        // Create test flight segments
        FlightSegment outboundSegment = FlightSegment.builder()
                .id(1L)
                .fromIata("WAW")
                .toIata("AYT")
                .carrierCode("XQ")
                .departureAt("2025-08-09T18:40:00Z")
                .arrivalAt("2025-08-09T22:15:00Z")
                .direction("outbound")
                .build();

        FlightSegment inboundSegment = FlightSegment.builder()
                .id(2L)
                .fromIata("AYT")
                .toIata("WAW")
                .carrierCode("XQ")
                .departureAt("2025-08-16T23:05:00Z")
                .arrivalAt("2025-08-17T00:40:00Z")
                .direction("inbound")
                .build();

        // Create test traveler price
        TravelerPrice travelerPrice = TravelerPrice.builder()
                .id(1L)
                .travelerType("ADULT")
                .currency("PLN")
                .price("1200.00")
                .build();

        // Create test flight
        testFlight = Flight.builder()
                .id(1L)
                .oneWay(false)
                .searchId(UUID.randomUUID())
                .currency("PLN")
                .totalPrice("2400.00")
                .outboundDurationIso("PT3H35M")
                .outboundDurationMinutes(215)
                .inboundDurationIso("PT3H35M")
                .inboundDurationMinutes(215)
                .lastUpdated(LocalDateTime.now())
                .outboundSegments(new ArrayList<>())
                .inboundSegments(new ArrayList<>())
                .travelerPrices(new ArrayList<>())
                .build();

        testFlight.addOutboundSegment(outboundSegment);
        testFlight.addInboundSegment(inboundSegment);
        testFlight.addTravelerPrice(travelerPrice);

        // Create test flight request DTO
        testFlightRequestDTO = new FlightSearchRequestDTO(
                "Warsaw",
                "Antalya",
                "2025-08-09",
                "2025-08-16",
                2,
                0,
                0,
                "PLN"
        );

        // Create test flight segment DTOs
        FlightSegmentDTO outboundSegmentDTO = new FlightSegmentDTO(
                "WAW",
                "AYT",
                "XQ",
                "2025-08-09T18:40:00Z",
                "2025-08-09T22:15:00Z"
        );

        FlightSegmentDTO inboundSegmentDTO = new FlightSegmentDTO(
                "AYT",
                "WAW",
                "XQ",
                "2025-08-16T23:05:00Z",
                "2025-08-17T00:40:00Z"
        );

        // Create test flight bound DTOs
        FlightBoundDTO outboundDTO = new FlightBoundDTO(
                "PT3H35M",
                215,
                List.of(outboundSegmentDTO)
        );

        FlightBoundDTO inboundDTO = new FlightBoundDTO(
                "PT3H35M",
                215,
                List.of(inboundSegmentDTO)
        );

        // Create test price DTO
        PriceDTO priceDTO = new PriceDTO(
                "PLN",
                "2400.00"
        );

        // Create test traveler price DTO
        TravelerPriceDTO travelerPriceDTO = new TravelerPriceDTO(
                "ADULT",
                new PriceDTO("PLN", "1200.00")
        );

        // Create test flight response DTO
        testFlightResponseDTO = new FlightSearchResponseDTO(
                false,
                outboundDTO,
                inboundDTO,
                priceDTO,
                List.of(travelerPriceDTO)
        );
    }

    @Test
    void shouldGetFlightOffers() throws Exception {
        // Given
        when(flightService.getFlightOffers(any(FlightSearchRequestDTO.class)))
                .thenReturn(List.of(testFlightResponseDTO));

        // When & Then
        mockMvc.perform(post("/api/v1/flights/flight-offer")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(testFlightRequestDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].oneWay", is(false)))
                .andExpect(jsonPath("$[0].outbound.durationIso", is("PT3H35M")))
                .andExpect(jsonPath("$[0].inbound.durationIso", is("PT3H35M")))
                .andExpect(jsonPath("$[0].totalPrice.currency", is("PLN")))
                .andExpect(jsonPath("$[0].totalPrice.total", is("2400.00")));

        verify(flightService, times(1)).getFlightOffers(any(FlightSearchRequestDTO.class));
    }

    @Test
    void shouldSearchAndSaveFlightOffers() throws Exception {
        // Given
        when(flightMapper.mapToDto(any(Flight.class))).thenReturn(testFlightResponseDTO);
        when(flightService.searchAndSaveFlightOffers(any(FlightSearchRequestDTO.class)))
                .thenReturn(List.of(testFlight));

        // When & Then
        mockMvc.perform(post("/api/v1/flights/flight-offer/save")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(testFlightRequestDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].oneWay", is(false)))
                .andExpect(jsonPath("$[0].outbound.durationIso", is("PT3H35M")))
                .andExpect(jsonPath("$[0].inbound.durationIso", is("PT3H35M")))
                .andExpect(jsonPath("$[0].totalPrice.currency", is("PLN")))
                .andExpect(jsonPath("$[0].totalPrice.total", is("2400.00")));

        verify(flightService, times(1)).searchAndSaveFlightOffers(any(FlightSearchRequestDTO.class));
        verify(flightMapper, times(1)).mapToDto(any(Flight.class));
    }

    @Test
    void shouldGetAllFlights() throws Exception {
        // Given
        when(flightMapper.mapToDto(any(Flight.class))).thenReturn(testFlightResponseDTO);
        when(flightService.getAllFlights()).thenReturn(List.of(testFlight));

        // When & Then
        mockMvc.perform(get("/api/v1/flights"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].oneWay", is(false)))
                .andExpect(jsonPath("$[0].outbound.durationIso", is("PT3H35M")))
                .andExpect(jsonPath("$[0].inbound.durationIso", is("PT3H35M")))
                .andExpect(jsonPath("$[0].totalPrice.currency", is("PLN")))
                .andExpect(jsonPath("$[0].totalPrice.total", is("2400.00")));

        verify(flightService, times(1)).getAllFlights();
        verify(flightMapper, times(1)).mapToDto(any(Flight.class));
    }

    @Test
    void shouldGetFlightById() throws Exception {
        // Given
        when(flightMapper.mapToDto(any(Flight.class))).thenReturn(testFlightResponseDTO);
        when(flightService.getFlightById(anyLong())).thenReturn(Optional.of(testFlight));

        // When & Then
        mockMvc.perform(get("/api/v1/flights/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.oneWay", is(false)))
                .andExpect(jsonPath("$.outbound.durationIso", is("PT3H35M")))
                .andExpect(jsonPath("$.inbound.durationIso", is("PT3H35M")))
                .andExpect(jsonPath("$.totalPrice.currency", is("PLN")))
                .andExpect(jsonPath("$.totalPrice.total", is("2400.00")));

        verify(flightService, times(1)).getFlightById(1L);
        verify(flightMapper, times(1)).mapToDto(testFlight);
    }

    @Test
    void shouldReturnNotFoundWhenFlightDoesNotExist() throws Exception {
        // Given
        when(flightService.getFlightById(anyLong())).thenReturn(Optional.empty());

        // When & Then
        mockMvc.perform(get("/api/v1/flights/999"))
                .andExpect(status().isNotFound());

        verify(flightService, times(1)).getFlightById(999L);
        verify(flightMapper, never()).mapToDto(any(Flight.class));
    }

    @Test
    void shouldDeleteFlight() throws Exception {
        // Given
        doNothing().when(flightService).deleteFlight(anyLong());

        // When & Then
        mockMvc.perform(delete("/api/v1/flights/1"))
                .andExpect(status().isNoContent());

        verify(flightService, times(1)).deleteFlight(1L);
    }

    @Test
    void shouldRemoveOutdatedFlights() throws Exception {
        // Given
        when(flightService.removeOutdatedFlights()).thenReturn(5);

        // When & Then
        mockMvc.perform(delete("/api/v1/flights/cleanup"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", is(5)));

        verify(flightService, times(1)).removeOutdatedFlights();
    }
}