package com.kodilla.tripplanner.mapper;

import com.kodilla.tripplanner.domain.BaggageType;
import com.kodilla.tripplanner.domain.Gender;
import com.kodilla.tripplanner.domain.PersonType;
import com.kodilla.tripplanner.domain.Traveler;
import com.kodilla.tripplanner.domain.Trip;
import com.kodilla.tripplanner.dto.TravelerDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TravelerMapperTest {

    private TravelerMapper travelerMapper;

    @BeforeEach
    void setUp() {
        travelerMapper = new TravelerMapper();
    }

    @Test
    void testToTravelerDTO() {
        // Given
        Trip trip = Trip.builder().id(1L).build();
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
        TravelerDTO travelerDTO = travelerMapper.toTravelerDTO(traveler);

        // Then
        assertNotNull(travelerDTO);
        assertEquals(traveler.getId(), travelerDTO.id());
        assertEquals(traveler.getFirstName(), travelerDTO.firstName());
        assertEquals(traveler.getLastName(), travelerDTO.lastName());
        assertEquals(traveler.getGender(), travelerDTO.gender());
        assertEquals(traveler.getPersonType(), travelerDTO.personType());
        assertEquals(traveler.getAge(), travelerDTO.age());
        assertEquals(traveler.getBaggage(), travelerDTO.baggage());
        assertEquals(traveler.getTrip().getId(), travelerDTO.tripId());
    }

    @Test
    void testToTravelerDTOWithNullTrip() {
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
        TravelerDTO travelerDTO = travelerMapper.toTravelerDTO(traveler);

        // Then
        assertNotNull(travelerDTO);
        assertEquals(traveler.getId(), travelerDTO.id());
        assertEquals(traveler.getFirstName(), travelerDTO.firstName());
        assertEquals(traveler.getLastName(), travelerDTO.lastName());
        assertEquals(traveler.getGender(), travelerDTO.gender());
        assertEquals(traveler.getPersonType(), travelerDTO.personType());
        assertEquals(traveler.getAge(), travelerDTO.age());
        assertEquals(traveler.getBaggage(), travelerDTO.baggage());
        assertNull(travelerDTO.tripId());
    }

    @Test
    void testToTravelerDTOWithNullInput() {
        // When
        TravelerDTO travelerDTO = travelerMapper.toTravelerDTO(null);

        // Then
        assertNull(travelerDTO);
    }

    @Test
    void testToTravelerDTOList() {
        // Given
        Trip trip = Trip.builder().id(1L).build();
        Traveler traveler1 = Traveler.builder()
                .id(1L)
                .firstName("John")
                .lastName("Doe")
                .gender(Gender.MALE)
                .personType(PersonType.ADULT)
                .age(30)
                .baggage(BaggageType.CHECKED)
                .trip(trip)
                .build();

        Traveler traveler2 = Traveler.builder()
                .id(2L)
                .firstName("Jane")
                .lastName("Smith")
                .gender(Gender.FEMALE)
                .personType(PersonType.ADULT)
                .age(25)
                .baggage(BaggageType.CABIN)
                .trip(trip)
                .build();

        List<Traveler> travelers = Arrays.asList(traveler1, traveler2);

        // When
        List<TravelerDTO> travelerDTOs = travelerMapper.toTravelerDTOList(travelers);

        // Then
        assertNotNull(travelerDTOs);
        assertEquals(2, travelerDTOs.size());
        assertEquals(traveler1.getId(), travelerDTOs.get(0).id());
        assertEquals(traveler1.getFirstName(), travelerDTOs.get(0).firstName());
        assertEquals(traveler2.getId(), travelerDTOs.get(1).id());
        assertEquals(traveler2.getFirstName(), travelerDTOs.get(1).firstName());
    }

    @Test
    void testToTravelerDTOListWithNullInput() {
        // When
        List<TravelerDTO> travelerDTOs = travelerMapper.toTravelerDTOList(null);

        // Then
        assertNull(travelerDTOs);
    }

    @Test
    void testToTraveler() {
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
        Traveler traveler = travelerMapper.toTraveler(travelerDTO);

        // Then
        assertNotNull(traveler);
        assertNull(traveler.getId()); // ID is not set when converting from DTO to entity
        assertEquals(travelerDTO.firstName(), traveler.getFirstName());
        assertEquals(travelerDTO.lastName(), traveler.getLastName());
        assertEquals(travelerDTO.gender(), traveler.getGender());
        assertEquals(travelerDTO.personType(), traveler.getPersonType());
        assertEquals(travelerDTO.age(), traveler.getAge());
        assertEquals(travelerDTO.baggage(), traveler.getBaggage());
        assertNull(traveler.getTrip()); // Trip is not set when converting from DTO to entity
    }

    @Test
    void testToTravelerWithNullInput() {
        // When
        Traveler traveler = travelerMapper.toTraveler(null);

        // Then
        assertNull(traveler);
    }
}