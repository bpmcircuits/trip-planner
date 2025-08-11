package com.kodilla.tripplanner.dto;

import com.kodilla.tripplanner.domain.BaggageType;
import com.kodilla.tripplanner.domain.Gender;
import com.kodilla.tripplanner.domain.PersonType;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class TravelerDTOTest {

    @Test
    void testTravelerDTOCreation() {
        // Given
        Long id = 1L;
        String firstName = "John";
        String lastName = "Doe";
        Gender gender = Gender.MALE;
        PersonType personType = PersonType.ADULT;
        int age = 30;
        BaggageType baggage = BaggageType.CHECKED;
        Long tripId = 2L;
        
        // When
        TravelerDTO travelerDTO = new TravelerDTO(id, firstName, lastName, gender, personType, age, baggage, tripId);
        
        // Then
        assertEquals(id, travelerDTO.id());
        assertEquals(firstName, travelerDTO.firstName());
        assertEquals(lastName, travelerDTO.lastName());
        assertEquals(gender, travelerDTO.gender());
        assertEquals(personType, travelerDTO.personType());
        assertEquals(age, travelerDTO.age());
        assertEquals(baggage, travelerDTO.baggage());
        assertEquals(tripId, travelerDTO.tripId());
    }
    
    @Test
    void testTravelerDTOEquality() {
        // Given
        TravelerDTO travelerDTO1 = new TravelerDTO(1L, "John", "Doe", Gender.MALE, PersonType.ADULT, 30, BaggageType.CHECKED, 2L);
        TravelerDTO travelerDTO2 = new TravelerDTO(1L, "John", "Doe", Gender.MALE, PersonType.ADULT, 30, BaggageType.CHECKED, 2L);
        TravelerDTO travelerDTO3 = new TravelerDTO(2L, "Jane", "Smith", Gender.FEMALE, PersonType.ADULT, 25, BaggageType.CABIN, 2L);
        
        // Then
        assertEquals(travelerDTO1, travelerDTO2);
        assertNotEquals(travelerDTO1, travelerDTO3);
        assertEquals(travelerDTO1.hashCode(), travelerDTO2.hashCode());
        assertNotEquals(travelerDTO1.hashCode(), travelerDTO3.hashCode());
    }
    
    @Test
    void testTravelerDTOToString() {
        // Given
        TravelerDTO travelerDTO = new TravelerDTO(1L, "John", "Doe", Gender.MALE, PersonType.ADULT, 30, BaggageType.CHECKED, 2L);
        
        // When
        String toString = travelerDTO.toString();
        
        // Then
        assertTrue(toString.contains("John"));
        assertTrue(toString.contains("Doe"));
        assertTrue(toString.contains("MALE"));
        assertTrue(toString.contains("ADULT"));
        assertTrue(toString.contains("30"));
        assertTrue(toString.contains("CHECKED"));
    }
}