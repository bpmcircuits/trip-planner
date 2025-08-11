package com.kodilla.tripplanner.service;

import com.kodilla.tripplanner.domain.BaggageType;
import com.kodilla.tripplanner.domain.Gender;
import com.kodilla.tripplanner.domain.PersonType;
import com.kodilla.tripplanner.domain.Traveler;
import com.kodilla.tripplanner.repository.TravelerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TripServiceTest {

    @Mock
    private TravelerRepository travelerRepository;

    @InjectMocks
    private TripService tripService;

    private Traveler traveler1;
    private Traveler traveler2;

    @BeforeEach
    void setUp() {
        traveler1 = Traveler.builder()
                .id(1L)
                .firstName("John")
                .lastName("Doe")
                .gender(Gender.MALE)
                .personType(PersonType.ADULT)
                .age(30)
                .baggage(BaggageType.CHECKED)
                .build();

        traveler2 = Traveler.builder()
                .id(2L)
                .firstName("Jane")
                .lastName("Smith")
                .gender(Gender.FEMALE)
                .personType(PersonType.ADULT)
                .age(25)
                .baggage(BaggageType.CABIN)
                .build();
    }

    @Test
    void testGetAllTravelers() {
        // Given
        when(travelerRepository.findAll()).thenReturn(Arrays.asList(traveler1, traveler2));

        // When
        List<Traveler> travelers = tripService.getAllTravelers();

        // Then
        assertEquals(2, travelers.size());
        assertEquals("John", travelers.get(0).getFirstName());
        assertEquals("Jane", travelers.get(1).getFirstName());
        verify(travelerRepository, times(1)).findAll();
    }

    @Test
    void testAddTraveler() {
        // Given
        when(travelerRepository.save(any(Traveler.class))).thenReturn(traveler1);

        // When
        Traveler savedTraveler = tripService.addTraveler(traveler1);

        // Then
        assertNotNull(savedTraveler);
        assertEquals("John", savedTraveler.getFirstName());
        verify(travelerRepository, times(1)).save(traveler1);
    }

    @Test
    void testRemoveTravelerSuccess() {
        // Given
        Long travelerId = 1L;
        when(travelerRepository.existsById(travelerId)).thenReturn(true);
        doNothing().when(travelerRepository).deleteById(travelerId);

        // When & Then
        assertDoesNotThrow(() -> tripService.removeTraveler(travelerId));
        verify(travelerRepository, times(1)).existsById(travelerId);
        verify(travelerRepository, times(1)).deleteById(travelerId);
    }

    @Test
    void testRemoveTravelerNotFound() {
        // Given
        Long travelerId = 999L;
        when(travelerRepository.existsById(travelerId)).thenReturn(false);

        // When & Then
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            tripService.removeTraveler(travelerId);
        });
        
        assertEquals("Traveler with ID " + travelerId + " does not exist.", exception.getMessage());
        verify(travelerRepository, times(1)).existsById(travelerId);
        verify(travelerRepository, never()).deleteById(any());
    }
}