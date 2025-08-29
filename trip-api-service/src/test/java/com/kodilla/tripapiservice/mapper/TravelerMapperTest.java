package com.kodilla.tripapiservice.mapper;

import com.kodilla.tripapiservice.domain.BaggageType;
import com.kodilla.tripapiservice.domain.Gender;
import com.kodilla.tripapiservice.domain.PersonType;
import com.kodilla.tripapiservice.domain.Traveler;
import com.kodilla.tripapiservice.domain.Trip;
import com.kodilla.tripapiservice.dto.TravelerDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class TravelerMapperTest {

    @InjectMocks
    private TravelerMapper travelerMapper;

    @Test
    void shouldMapTravelerToTravelerDTO() {
        // Given
        Trip trip = Trip.builder()
                .id(1L)
                .name("Summer Vacation")
                .description("Family vacation to Spain")
                .createdAt(LocalDateTime.now())
                .build();

        Traveler traveler = Traveler.builder()
                .id(1L)
                .firstName("John")
                .lastName("Doe")
                .gender(Gender.MALE)
                .personType(PersonType.ADULT)
                .age(30)
                .baggage(BaggageType.CHECKED)
                .trip(trip)
                .build();

        // When
        TravelerDTO result = travelerMapper.toTravelerDTO(traveler);

        // Then
        assertThat(result).isNotNull();
        assertThat(result.id()).isEqualTo(1L);
        assertThat(result.firstName()).isEqualTo("John");
        assertThat(result.lastName()).isEqualTo("Doe");
        assertThat(result.gender()).isEqualTo(Gender.MALE);
        assertThat(result.personType()).isEqualTo(PersonType.ADULT);
        assertThat(result.age()).isEqualTo(30);
        assertThat(result.baggage()).isEqualTo(BaggageType.CHECKED);
        assertThat(result.tripId()).isEqualTo(1L);
    }

    @Test
    void shouldMapTravelerToTravelerDTOWhenTripIsNull() {
        // Given
        Traveler traveler = Traveler.builder()
                .id(1L)
                .firstName("John")
                .lastName("Doe")
                .gender(Gender.MALE)
                .personType(PersonType.ADULT)
                .age(30)
                .baggage(BaggageType.CHECKED)
                .trip(null)
                .build();

        // When
        TravelerDTO result = travelerMapper.toTravelerDTO(traveler);

        // Then
        assertThat(result).isNotNull();
        assertThat(result.id()).isEqualTo(1L);
        assertThat(result.firstName()).isEqualTo("John");
        assertThat(result.lastName()).isEqualTo("Doe");
        assertThat(result.gender()).isEqualTo(Gender.MALE);
        assertThat(result.personType()).isEqualTo(PersonType.ADULT);
        assertThat(result.age()).isEqualTo(30);
        assertThat(result.baggage()).isEqualTo(BaggageType.CHECKED);
        assertThat(result.tripId()).isNull();
    }

    @Test
    void shouldReturnNullWhenMappingNullTravelerToDTO() {
        // Given
        Traveler traveler = null;

        // When
        TravelerDTO result = travelerMapper.toTravelerDTO(traveler);

        // Then
        assertThat(result).isNull();
    }

    @Test
    void shouldMapTravelerListToTravelerDTOList() {
        // Given
        Traveler traveler1 = Traveler.builder()
                .id(1L)
                .firstName("John")
                .lastName("Doe")
                .gender(Gender.MALE)
                .personType(PersonType.ADULT)
                .age(30)
                .baggage(BaggageType.CHECKED)
                .build();

        Traveler traveler2 = Traveler.builder()
                .id(2L)
                .firstName("Jane")
                .lastName("Doe")
                .gender(Gender.FEMALE)
                .personType(PersonType.ADULT)
                .age(28)
                .baggage(BaggageType.CABIN)
                .build();

        List<Traveler> travelers = List.of(traveler1, traveler2);

        // When
        List<TravelerDTO> result = travelerMapper.toTravelerDTOList(travelers);

        // Then
        assertThat(result).isNotNull();
        assertThat(result).hasSize(2);
        assertThat(result.get(0).id()).isEqualTo(1L);
        assertThat(result.get(0).firstName()).isEqualTo("John");
        assertThat(result.get(1).id()).isEqualTo(2L);
        assertThat(result.get(1).firstName()).isEqualTo("Jane");
    }

    @Test
    void shouldReturnNullWhenMappingNullTravelerListToDTO() {
        // Given
        List<Traveler> travelers = null;

        // When
        List<TravelerDTO> result = travelerMapper.toTravelerDTOList(travelers);

        // Then
        assertThat(result).isNull();
    }

    @Test
    void shouldMapTravelerDTOToTraveler() {
        // Given
        TravelerDTO travelerDTO = new TravelerDTO(
                1L,
                "John",
                "Doe",
                Gender.MALE,
                PersonType.ADULT,
                30,
                BaggageType.CHECKED,
                1L
        );

        // When
        Traveler result = travelerMapper.toTraveler(travelerDTO);

        // Then
        assertThat(result).isNotNull();
        assertThat(result.getFirstName()).isEqualTo("John");
        assertThat(result.getLastName()).isEqualTo("Doe");
        assertThat(result.getGender()).isEqualTo(Gender.MALE);
        assertThat(result.getPersonType()).isEqualTo(PersonType.ADULT);
        assertThat(result.getAge()).isEqualTo(30);
        assertThat(result.getBaggage()).isEqualTo(BaggageType.CHECKED);
        // Note: Trip is not set in the mapper, it's handled separately
    }

    @Test
    void shouldReturnNullWhenMappingNullTravelerDTOToEntity() {
        // Given
        TravelerDTO travelerDTO = null;

        // When
        Traveler result = travelerMapper.toTraveler(travelerDTO);

        // Then
        assertThat(result).isNull();
    }
}