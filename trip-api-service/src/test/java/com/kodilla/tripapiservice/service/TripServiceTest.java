package com.kodilla.tripapiservice.service;

import com.kodilla.tripapiservice.domain.Traveler;
import com.kodilla.tripapiservice.domain.Trip;
import com.kodilla.tripapiservice.exception.FlightNotFoundException;
import com.kodilla.tripapiservice.exception.HotelNotFoundException;
import com.kodilla.tripapiservice.exception.TripNotFoundException;
import com.kodilla.tripapiservice.repository.TripRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TripServiceTest {

    @Mock
    private TripRepository tripRepository;

    @InjectMocks
    private TripService tripService;

    private Trip trip;
    private final LocalDateTime createdAt = LocalDateTime.of(2025, 8, 18, 10, 0);

    @BeforeEach
    void setUp() {
        trip = Trip.builder()
                .id(1L)
                .name("Summer Vacation")
                .description("Family vacation to Spain")
                .createdAt(createdAt)
                .flightId(100L)
                .hotelId(200L)
                .travelers(new ArrayList<>())
                .build();
    }

    @Test
    void shouldFindAllTrips() {
        // Given
        when(tripRepository.findAll()).thenReturn(List.of(trip));

        // When
        List<Trip> result = tripService.findAll();

        // Then
        assertThat(result).hasSize(1);
        assertThat(result.get(0).getName()).isEqualTo("Summer Vacation");
        assertThat(result.get(0).getDescription()).isEqualTo("Family vacation to Spain");
        verify(tripRepository, times(1)).findAll();
    }

    @Test
    void shouldFindTripById() throws TripNotFoundException {
        // Given
        Long tripId = 1L;
        when(tripRepository.findById(tripId)).thenReturn(Optional.of(trip));

        // When
        Trip result = tripService.findById(tripId);

        // Then
        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(1L);
        assertThat(result.getName()).isEqualTo("Summer Vacation");
        verify(tripRepository, times(1)).findById(tripId);
    }

    @Test
    void shouldThrowExceptionWhenTripNotFound() {
        // Given
        Long tripId = 999L;
        when(tripRepository.findById(tripId)).thenReturn(Optional.empty());

        // When & Then
        TripNotFoundException exception = assertThrows(
                TripNotFoundException.class,
                () -> tripService.findById(tripId)
        );

        assertThat(exception.getMessage()).contains("Trip not found with id: " + tripId);
        verify(tripRepository, times(1)).findById(tripId);
    }

    @Test
    void shouldSaveTrip() {
        // Given
        when(tripRepository.save(any(Trip.class))).thenReturn(trip);

        // When
        Trip result = tripService.save(trip);

        // Then
        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(1L);
        assertThat(result.getName()).isEqualTo("Summer Vacation");
        verify(tripRepository, times(1)).save(any(Trip.class));
    }

    @Test
    void shouldUpdateTripFlight() throws TripNotFoundException, FlightNotFoundException {
        // Given
        Long tripId = 1L;
        Long newFlightId = 101L;
        when(tripRepository.findById(tripId)).thenReturn(Optional.of(trip));
        
        Trip updatedTrip = Trip.builder()
                .id(1L)
                .name("Summer Vacation")
                .description("Family vacation to Spain")
                .createdAt(createdAt)
                .flightId(newFlightId)
                .hotelId(200L)
                .travelers(new ArrayList<>())
                .build();
        
        when(tripRepository.save(any(Trip.class))).thenReturn(updatedTrip);

        // When
        Trip result = tripService.updateTripFlight(tripId, newFlightId);

        // Then
        assertThat(result).isNotNull();
        assertThat(result.getFlightId()).isEqualTo(newFlightId);
        verify(tripRepository, times(1)).findById(tripId);
        verify(tripRepository, times(1)).save(any(Trip.class));
    }

    @Test
    void shouldThrowExceptionWhenUpdatingTripWithNullFlightId() {
        // Given
        Long tripId = 1L;
        Long nullFlightId = null;

        // When & Then
        FlightNotFoundException exception = assertThrows(
                FlightNotFoundException.class,
                () -> tripService.updateTripFlight(tripId, nullFlightId)
        );

        assertThat(exception.getMessage()).contains("Flight ID cannot be null");
        verify(tripRepository, never()).findById(any());
        verify(tripRepository, never()).save(any());
    }

    @Test
    void shouldUpdateTripHotel() throws TripNotFoundException, HotelNotFoundException {
        // Given
        Long tripId = 1L;
        Long newHotelId = 201L;
        when(tripRepository.findById(tripId)).thenReturn(Optional.of(trip));
        
        Trip updatedTrip = Trip.builder()
                .id(1L)
                .name("Summer Vacation")
                .description("Family vacation to Spain")
                .createdAt(createdAt)
                .flightId(100L)
                .hotelId(newHotelId)
                .travelers(new ArrayList<>())
                .build();
        
        when(tripRepository.save(any(Trip.class))).thenReturn(updatedTrip);

        // When
        Trip result = tripService.updateTripHotel(tripId, newHotelId);

        // Then
        assertThat(result).isNotNull();
        assertThat(result.getHotelId()).isEqualTo(newHotelId);
        verify(tripRepository, times(1)).findById(tripId);
        verify(tripRepository, times(1)).save(any(Trip.class));
    }

    @Test
    void shouldThrowExceptionWhenUpdatingTripWithNullHotelId() {
        // Given
        Long tripId = 1L;
        Long nullHotelId = null;

        // When & Then
        HotelNotFoundException exception = assertThrows(
                HotelNotFoundException.class,
                () -> tripService.updateTripHotel(tripId, nullHotelId)
        );

        assertThat(exception.getMessage()).contains("Hotel ID cannot be null");
        verify(tripRepository, never()).findById(any());
        verify(tripRepository, never()).save(any());
    }

    @Test
    void shouldDeleteTripById() throws TripNotFoundException {
        // Given
        Long tripId = 1L;
        when(tripRepository.existsById(tripId)).thenReturn(true);
        doNothing().when(tripRepository).deleteById(tripId);

        // When
        tripService.deleteById(tripId);

        // Then
        verify(tripRepository, times(1)).existsById(tripId);
        verify(tripRepository, times(1)).deleteById(tripId);
    }

    @Test
    void shouldThrowExceptionWhenDeletingNonExistentTrip() {
        // Given
        Long tripId = 999L;
        when(tripRepository.existsById(tripId)).thenReturn(false);

        // When & Then
        TripNotFoundException exception = assertThrows(
                TripNotFoundException.class,
                () -> tripService.deleteById(tripId)
        );

        assertThat(exception.getMessage()).contains("Trip not found with id: " + tripId);
        verify(tripRepository, times(1)).existsById(tripId);
        verify(tripRepository, never()).deleteById(any());
    }
}