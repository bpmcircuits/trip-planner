package com.kodilla.tripplanner.domain;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class PersonTypeTest {

    @Test
    void testEnumValues() {
        // Given & When
        PersonType[] values = PersonType.values();
        
        // Then
        assertEquals(3, values.length);
        assertEquals(PersonType.ADULT, values[0]);
        assertEquals(PersonType.CHILD, values[1]);
        assertEquals(PersonType.INFANT, values[2]);
    }
    
    @Test
    void testEnumValueOf() {
        // Given & When & Then
        assertEquals(PersonType.ADULT, PersonType.valueOf("ADULT"));
        assertEquals(PersonType.CHILD, PersonType.valueOf("CHILD"));
        assertEquals(PersonType.INFANT, PersonType.valueOf("INFANT"));
    }
    
    @Test
    void testEnumValueOfInvalidValue() {
        // Given & When & Then
        assertThrows(IllegalArgumentException.class, () -> PersonType.valueOf("UNKNOWN"));
    }
}