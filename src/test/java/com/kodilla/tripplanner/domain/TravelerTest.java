package com.kodilla.tripplanner.domain;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class TravelerTest {

    @Test
    void testTravelerBuilder() {
        // Given
        String firstName = "John";
        String lastName = "Doe";
        Gender gender = Gender.MALE;
        PersonType personType = PersonType.ADULT;
        int age = 30;
        BaggageType baggageType = BaggageType.CHECKED;
        
        // When
        Traveler traveler = Traveler.builder()
                .firstName(firstName)
                .lastName(lastName)
                .gender(gender)
                .personType(personType)
                .age(age)
                .baggage(baggageType)
                .build();
        
        // Then
        assertNotNull(traveler);
        assertEquals(firstName, traveler.getFirstName());
        assertEquals(lastName, traveler.getLastName());
        assertEquals(gender, traveler.getGender());
        assertEquals(personType, traveler.getPersonType());
        assertEquals(age, traveler.getAge());
        assertEquals(baggageType, traveler.getBaggage());
    }
    
    @Test
    void testTravelerAllArgsConstructor() {
        // Given
        Long id = 1L;
        String firstName = "Jane";
        String lastName = "Smith";
        Gender gender = Gender.FEMALE;
        PersonType personType = PersonType.ADULT;
        int age = 25;
        BaggageType baggageType = BaggageType.CABIN;
        Trip trip = null;
        
        // When
        Traveler traveler = new Traveler(id, firstName, lastName, gender, personType, age, baggageType, trip);
        
        // Then
        assertNotNull(traveler);
        assertEquals(id, traveler.getId());
        assertEquals(firstName, traveler.getFirstName());
        assertEquals(lastName, traveler.getLastName());
        assertEquals(gender, traveler.getGender());
        assertEquals(personType, traveler.getPersonType());
        assertEquals(age, traveler.getAge());
        assertEquals(baggageType, traveler.getBaggage());
        assertNull(traveler.getTrip());
    }
    
    @Test
    void testTravelerNoArgsConstructor() {
        // When
        Traveler traveler = new Traveler();
        
        // Then
        assertNotNull(traveler);
        assertNull(traveler.getId());
        assertNull(traveler.getFirstName());
        assertNull(traveler.getLastName());
        assertNull(traveler.getGender());
        assertNull(traveler.getPersonType());
        assertEquals(0, traveler.getAge());
        assertNull(traveler.getBaggage());
        assertNull(traveler.getTrip());
    }
}