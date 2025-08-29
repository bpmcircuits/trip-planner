package com.kodilla.tripapiservice.mapper;

import com.kodilla.tripapiservice.domain.BaggageType;
import com.kodilla.tripapiservice.domain.Gender;
import com.kodilla.tripapiservice.domain.PersonType;
import com.kodilla.tripapiservice.domain.Traveler;
import com.kodilla.tripapiservice.domain.Trip;
import com.kodilla.tripapiservice.dto.TripDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class TripMapperTest {

    @InjectMocks
    private TripMapper tripMapper;

    private final LocalDateTime createdAt = LocalDateTime.of(2025, 8, 18, 10, 0);

    @Test
    void shouldMapTripToTripDTO() {
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

        Trip trip = Trip.builder()
                .id(1L)
                .name("Summer Vacation")
                .description("Family vacation to Spain")
                .createdAt(createdAt)
                .flightId(100L)
                .hotelId(200L)
                .travelers(travelers)
                .build();

        // When
        TripDTO result = tripMapper.toTripDTO(trip);

        // Then
        assertThat(result).isNotNull();
        assertThat(result.id()).isEqualTo(1L);
        assertThat(result.name()).isEqualTo("Summer Vacation");
        assertThat(result.description()).isEqualTo("Family vacation to Spain");
        assertThat(result.createdAt()).isEqualTo(createdAt);
        assertThat(result.flightId()).isEqualTo(100L);
        assertThat(result.hotelId()).isEqualTo(200L);
        assertThat(result.travelerIds()).hasSize(2);
        assertThat(result.travelerIds()).contains(1L, 2L);
    }

    @Test
    void shouldMapTripToTripDTOWithNullTravelers() {
        // Given
        Trip trip = Trip.builder()
                .id(1L)
                .name("Summer Vacation")
                .description("Family vacation to Spain")
                .createdAt(createdAt)
                .flightId(100L)
                .hotelId(200L)
                .travelers(null)
                .build();

        // When
        TripDTO result = tripMapper.toTripDTO(trip);

        // Then
        assertThat(result).isNotNull();
        assertThat(result.id()).isEqualTo(1L);
        assertThat(result.name()).isEqualTo("Summer Vacation");
        assertThat(result.description()).isEqualTo("Family vacation to Spain");
        assertThat(result.createdAt()).isEqualTo(createdAt);
        assertThat(result.flightId()).isEqualTo(100L);
        assertThat(result.hotelId()).isEqualTo(200L);
        assertThat(result.travelerIds()).isNull();
    }

    @Test
    void shouldReturnNullWhenMappingNullTripToDTO() {
        // Given
        Trip trip = null;

        // When
        TripDTO result = tripMapper.toTripDTO(trip);

        // Then
        assertThat(result).isNull();
    }

    @Test
    void shouldMapTripListToTripDTOList() {
        // Given
        Trip trip1 = Trip.builder()
                .id(1L)
                .name("Summer Vacation")
                .description("Family vacation to Spain")
                .createdAt(createdAt)
                .flightId(100L)
                .hotelId(200L)
                .travelers(new ArrayList<>())
                .build();

        Trip trip2 = Trip.builder()
                .id(2L)
                .name("Winter Vacation")
                .description("Skiing in the Alps")
                .createdAt(createdAt.plusMonths(6))
                .flightId(101L)
                .hotelId(201L)
                .travelers(new ArrayList<>())
                .build();

        List<Trip> trips = List.of(trip1, trip2);

        // When
        List<TripDTO> result = tripMapper.toTripDTOList(trips);

        // Then
        assertThat(result).isNotNull();
        assertThat(result).hasSize(2);
        assertThat(result.get(0).id()).isEqualTo(1L);
        assertThat(result.get(0).name()).isEqualTo("Summer Vacation");
        assertThat(result.get(1).id()).isEqualTo(2L);
        assertThat(result.get(1).name()).isEqualTo("Winter Vacation");
    }

    @Test
    void shouldReturnNullWhenMappingNullTripListToDTO() {
        // Given
        List<Trip> trips = null;

        // When
        List<TripDTO> result = tripMapper.toTripDTOList(trips);

        // Then
        assertThat(result).isNull();
    }

    @Test
    void shouldMapTripDTOToTrip() {
        // Given
        TripDTO tripDTO = new TripDTO(
                1L,
                "Summer Vacation",
                "Family vacation to Spain",
                createdAt,
                100L,
                200L,
                List.of(1L, 2L)
        );

        List<Traveler> travelers = new ArrayList<>();
        Traveler traveler1 = Traveler.builder()
                .id(1L)
                .firstName("John")
                .lastName("Doe")
                .build();
        Traveler traveler2 = Traveler.builder()
                .id(2L)
                .firstName("Jane")
                .lastName("Doe")
                .build();
        travelers.add(traveler1);
        travelers.add(traveler2);

        // When
        Trip result = tripMapper.toTrip(tripDTO, travelers);

        // Then
        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(1L);
        assertThat(result.getName()).isEqualTo("Summer Vacation");
        assertThat(result.getDescription()).isEqualTo("Family vacation to Spain");
        assertThat(result.getCreatedAt()).isEqualTo(createdAt);
        assertThat(result.getFlightId()).isEqualTo(100L);
        assertThat(result.getHotelId()).isEqualTo(200L);
        assertThat(result.getTravelers()).isEqualTo(travelers);
    }

    @Test
    void shouldMapTripDTOToTripWithNullTravelers() {
        // Given
        TripDTO tripDTO = new TripDTO(
                1L,
                "Summer Vacation",
                "Family vacation to Spain",
                createdAt,
                100L,
                200L,
                List.of(1L, 2L)
        );

        // When
        Trip result = tripMapper.toTrip(tripDTO, null);

        // Then
        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(1L);
        assertThat(result.getName()).isEqualTo("Summer Vacation");
        assertThat(result.getDescription()).isEqualTo("Family vacation to Spain");
        assertThat(result.getCreatedAt()).isEqualTo(createdAt);
        assertThat(result.getFlightId()).isEqualTo(100L);
        assertThat(result.getHotelId()).isEqualTo(200L);
        assertThat(result.getTravelers()).isNull();
    }

    @Test
    void shouldReturnNullWhenMappingNullTripDTOToEntity() {
        // Given
        TripDTO tripDTO = null;

        // When
        Trip result = tripMapper.toTrip(tripDTO, null);

        // Then
        assertThat(result).isNull();
    }
}