package com.kodilla.tripapiservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.kodilla.tripapiservice.domain.Trip;
import com.kodilla.tripapiservice.dto.TripDTO;
import com.kodilla.tripapiservice.exception.FlightNotFoundException;
import com.kodilla.tripapiservice.exception.HotelNotFoundException;
import com.kodilla.tripapiservice.exception.TripNotFoundException;
import com.kodilla.tripapiservice.mapper.TripMapper;
import com.kodilla.tripapiservice.service.TripService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class TripControllerTest {

    private MockMvc mockMvc;

    private ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());

    @Mock
    private TripService tripService;

    @Mock
    private TripMapper tripMapper;

    @InjectMocks
    private TripController tripController;

    private Trip trip;
    private TripDTO tripDTO;
    private final LocalDateTime createdAt = LocalDateTime.of(2025, 8, 18, 10, 0);

    @BeforeEach
    void setUp() {

        mockMvc = MockMvcBuilders.standaloneSetup(tripController)
                .setControllerAdvice(new com.kodilla.tripapiservice.exception.GlobalHttpErrorHandler())
                .build();

        trip = Trip.builder()
                .id(1L)
                .name("Summer Vacation")
                .description("Family vacation to Spain")
                .createdAt(createdAt)
                .flightId(100L)
                .hotelId(200L)
                .travelers(new ArrayList<>())
                .build();

        tripDTO = new TripDTO(
                1L,
                "Summer Vacation",
                "Family vacation to Spain",
                createdAt,
                100L,
                200L,
                new ArrayList<>()
        );
    }

    @Test
    void shouldGetAllTrips() throws Exception {
        // Given
        when(tripService.findAll()).thenReturn(List.of(trip));
        when(tripMapper.toTripDTOList(any())).thenReturn(List.of(tripDTO));

        // When & Then
        mockMvc.perform(get("/api/v1/trips")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].name", is("Summer Vacation")))
                .andExpect(jsonPath("$[0].description", is("Family vacation to Spain")));

        verify(tripService, times(1)).findAll();
        verify(tripMapper, times(1)).toTripDTOList(any());
    }

    @Test
    void shouldGetTripById() throws Exception {
        // Given
        Long tripId = 1L;
        when(tripService.findById(tripId)).thenReturn(trip);
        when(tripMapper.toTripDTO(any(Trip.class))).thenReturn(tripDTO);

        // When & Then
        mockMvc.perform(get("/api/v1/trips/{id}", tripId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.name", is("Summer Vacation")))
                .andExpect(jsonPath("$.description", is("Family vacation to Spain")));

        verify(tripService, times(1)).findById(tripId);
        verify(tripMapper, times(1)).toTripDTO(any(Trip.class));
    }

    @Test
    void shouldReturn404WhenTripNotFound() throws Exception {
        // Given
        Long tripId = 999L;
        when(tripService.findById(tripId)).thenThrow(new TripNotFoundException("Trip not found with id: " + tripId));

        // When & Then
        mockMvc.perform(get("/api/v1/trips/{id}", tripId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());

        verify(tripService, times(1)).findById(tripId);
    }

    @Test
    void shouldCreateTrip() throws Exception {
        // Given
        when(tripMapper.toTrip(any(TripDTO.class), any())).thenReturn(trip);
        when(tripService.save(any(Trip.class))).thenReturn(trip);
        when(tripMapper.toTripDTO(any(Trip.class))).thenReturn(tripDTO);

        // When & Then
        mockMvc.perform(post("/api/v1/trips")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(tripDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.name", is("Summer Vacation")))
                .andExpect(jsonPath("$.description", is("Family vacation to Spain")));

        verify(tripMapper, times(1)).toTrip(any(TripDTO.class), any());
        verify(tripService, times(1)).save(any(Trip.class));
        verify(tripMapper, times(1)).toTripDTO(any(Trip.class));
    }

    @Test
    void shouldUpdateTripFlight() throws Exception {
        // Given
        Long tripId = 1L;
        Long flightId = 101L;
        Trip updatedTrip = Trip.builder()
                .id(1L)
                .name("Summer Vacation")
                .description("Family vacation to Spain")
                .createdAt(createdAt)
                .flightId(flightId)
                .hotelId(200L)
                .travelers(new ArrayList<>())
                .build();
        
        TripDTO updatedTripDTO = new TripDTO(
                1L,
                "Summer Vacation",
                "Family vacation to Spain",
                createdAt,
                flightId,
                200L,
                new ArrayList<>()
        );

        when(tripService.updateTripFlight(anyLong(), anyLong())).thenReturn(updatedTrip);
        when(tripMapper.toTripDTO(any(Trip.class))).thenReturn(updatedTripDTO);

        // When & Then
        mockMvc.perform(put("/api/v1/trips/{id}/flight", tripId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(flightId)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.flightId", is(101)));

        verify(tripService, times(1)).updateTripFlight(tripId, flightId);
        verify(tripMapper, times(1)).toTripDTO(any(Trip.class));
    }

    @Test
    void shouldReturn404WhenUpdatingFlightForNonExistentTrip() throws Exception {
        // Given
        Long tripId = 999L;
        Long flightId = 101L;
        when(tripService.updateTripFlight(tripId, flightId))
                .thenThrow(new TripNotFoundException("Trip not found with id: " + tripId));

        // When & Then
        mockMvc.perform(put("/api/v1/trips/{id}/flight", tripId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(flightId)))
                .andExpect(status().isNotFound());

        verify(tripService, times(1)).updateTripFlight(tripId, flightId);
    }

    @Test
    void shouldReturn404WhenUpdatingTripWithInvalidFlightId() throws Exception {
        // Given
        Long tripId = 1L;
        Long flightId = 0L;
        when(tripService.updateTripFlight(tripId, flightId))
                .thenThrow(new FlightNotFoundException("Invalid Flight ID"));

        // When & Then
        mockMvc.perform(put("/api/v1/trips/{id}/flight", tripId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(flightId)))
                .andExpect(status().isNotFound());

        verify(tripService, times(1)).updateTripFlight(tripId, flightId);
    }

    @Test
    void shouldUpdateTripHotel() throws Exception {
        // Given
        Long tripId = 1L;
        Long hotelId = 201L;
        Trip updatedTrip = Trip.builder()
                .id(1L)
                .name("Summer Vacation")
                .description("Family vacation to Spain")
                .createdAt(createdAt)
                .flightId(100L)
                .hotelId(hotelId)
                .travelers(new ArrayList<>())
                .build();
        
        TripDTO updatedTripDTO = new TripDTO(
                1L,
                "Summer Vacation",
                "Family vacation to Spain",
                createdAt,
                100L,
                hotelId,
                new ArrayList<>()
        );

        when(tripService.updateTripHotel(anyLong(), anyLong())).thenReturn(updatedTrip);
        when(tripMapper.toTripDTO(any(Trip.class))).thenReturn(updatedTripDTO);

        // When & Then
        mockMvc.perform(put("/api/v1/trips/{id}/hotel", tripId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(hotelId)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.hotelId", is(201)));

        verify(tripService, times(1)).updateTripHotel(tripId, hotelId);
        verify(tripMapper, times(1)).toTripDTO(any(Trip.class));
    }

    @Test
    void shouldReturn404WhenUpdatingHotelForNonExistentTrip() throws Exception {
        // Given
        Long tripId = 999L;
        Long hotelId = 201L;
        when(tripService.updateTripHotel(tripId, hotelId))
                .thenThrow(new TripNotFoundException("Trip not found with id: " + tripId));

        // When & Then
        mockMvc.perform(put("/api/v1/trips/{id}/hotel", tripId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(hotelId)))
                .andExpect(status().isNotFound());

        verify(tripService, times(1)).updateTripHotel(tripId, hotelId);
    }

    @Test
    void shouldReturn404WhenUpdatingTripWithInvalidHotelId() throws Exception {
        // Given
        Long tripId = 1L;
        Long hotelId = 0L;
        when(tripService.updateTripHotel(tripId, hotelId))
                .thenThrow(new HotelNotFoundException("Invalid Hotel ID"));

        // When & Then
        mockMvc.perform(put("/api/v1/trips/{id}/hotel", tripId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(hotelId)))
                .andExpect(status().isNotFound());

        verify(tripService, times(1)).updateTripHotel(tripId, hotelId);
    }

    @Test
    void shouldDeleteTrip() throws Exception {
        // Given
        Long tripId = 1L;
        doNothing().when(tripService).deleteById(tripId);

        // When & Then
        mockMvc.perform(delete("/api/v1/trips/{id}", tripId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        verify(tripService, times(1)).deleteById(tripId);
    }

    @Test
    void shouldReturn404WhenDeletingNonExistentTrip() throws Exception {
        // Given
        Long tripId = 999L;
        doThrow(new TripNotFoundException("Trip not found with id: " + tripId))
                .when(tripService).deleteById(tripId);

        // When & Then
        mockMvc.perform(delete("/api/v1/trips/{id}", tripId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());

        verify(tripService, times(1)).deleteById(tripId);
    }
}