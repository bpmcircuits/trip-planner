package com.kodilla.tripapiservice.service;

import com.kodilla.tripapiservice.domain.BaggageType;
import com.kodilla.tripapiservice.domain.Gender;
import com.kodilla.tripapiservice.domain.PersonType;
import com.kodilla.tripapiservice.domain.Traveler;
import com.kodilla.tripapiservice.exception.TravelerNotFoundException;
import com.kodilla.tripapiservice.repository.TravelerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TravelerServiceTest {

    @Mock
    private TravelerRepository travelerRepository;

    @InjectMocks
    private TravelerService travelerService;

    private Traveler traveler;

    @BeforeEach
    void setUp() {
        traveler = Traveler.builder()
                .id(1L)
                .firstName("John")
                .lastName("Doe")
                .gender(Gender.MALE)
                .personType(PersonType.ADULT)
                .age(30)
                .baggage(BaggageType.CHECKED)
                .build();
    }

    @Test
    void shouldGetAllTravelers() {
        // Given
        when(travelerRepository.findAll()).thenReturn(List.of(traveler));

        // When
        List<Traveler> result = travelerService.getAllTravelers();

        // Then
        assertThat(result).hasSize(1);
        assertThat(result.get(0).getFirstName()).isEqualTo("John");
        assertThat(result.get(0).getLastName()).isEqualTo("Doe");
        verify(travelerRepository, times(1)).findAll();
    }

    @Test
    void shouldAddTraveler() {
        // Given
        when(travelerRepository.save(any(Traveler.class))).thenReturn(traveler);

        // When
        Traveler result = travelerService.addTraveler(traveler);

        // Then
        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(1L);
        assertThat(result.getFirstName()).isEqualTo("John");
        assertThat(result.getLastName()).isEqualTo("Doe");
        verify(travelerRepository, times(1)).save(any(Traveler.class));
    }

    @Test
    void shouldRemoveTravelerWhenExists() throws TravelerNotFoundException {
        // Given
        Long travelerId = 1L;
        when(travelerRepository.existsById(travelerId)).thenReturn(true);
        doNothing().when(travelerRepository).deleteById(travelerId);

        // When
        travelerService.removeTraveler(travelerId);

        // Then
        verify(travelerRepository, times(1)).existsById(travelerId);
        verify(travelerRepository, times(1)).deleteById(travelerId);
    }

    @Test
    void shouldThrowExceptionWhenRemovingNonExistentTraveler() {
        // Given
        Long travelerId = 999L;
        when(travelerRepository.existsById(travelerId)).thenReturn(false);

        // When & Then
        TravelerNotFoundException exception = assertThrows(
                TravelerNotFoundException.class,
                () -> travelerService.removeTraveler(travelerId)
        );

        assertThat(exception.getMessage()).contains("Traveler with ID " + travelerId + " does not exist.");
        verify(travelerRepository, times(1)).existsById(travelerId);
        verify(travelerRepository, never()).deleteById(any());
    }
}