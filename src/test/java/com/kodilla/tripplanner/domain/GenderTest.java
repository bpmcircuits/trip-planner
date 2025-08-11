package com.kodilla.tripplanner.domain;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class GenderTest {

    @Test
    void testEnumValues() {
        // Given & When
        Gender[] values = Gender.values();
        
        // Then
        assertEquals(2, values.length);
        assertEquals(Gender.MALE, values[0]);
        assertEquals(Gender.FEMALE, values[1]);
    }
    
    @Test
    void testEnumValueOf() {
        // Given & When & Then
        assertEquals(Gender.MALE, Gender.valueOf("MALE"));
        assertEquals(Gender.FEMALE, Gender.valueOf("FEMALE"));
    }
    
    @Test
    void testEnumValueOfInvalidValue() {
        // Given & When & Then
        assertThrows(IllegalArgumentException.class, () -> Gender.valueOf("UNKNOWN"));
    }
}