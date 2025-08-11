package com.kodilla.tripplanner.domain;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class BaggageTypeTest {

    @Test
    void testEnumValues() {
        // Given & When
        BaggageType[] values = BaggageType.values();
        
        // Then
        assertEquals(2, values.length);
        assertEquals(BaggageType.CABIN, values[0]);
        assertEquals(BaggageType.CHECKED, values[1]);
    }
    
    @Test
    void testEnumValueOf() {
        // Given & When & Then
        assertEquals(BaggageType.CABIN, BaggageType.valueOf("CABIN"));
        assertEquals(BaggageType.CHECKED, BaggageType.valueOf("CHECKED"));
    }
    
    @Test
    void testEnumValueOfInvalidValue() {
        // Given & When & Then
        assertThrows(IllegalArgumentException.class, () -> BaggageType.valueOf("UNKNOWN"));
    }
}