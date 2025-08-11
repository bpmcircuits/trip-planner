package com.kodilla.tripplanner.domain;

import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class TripTest {

    @Test
    void testTripBuilder() {
        // Given
        String name = "Summer Vacation";
        String description = "Family trip to Spain";
        LocalDateTime createdAt = LocalDateTime.now();
        Flight flight = null;
        Hotel hotel = null;
        List<Traveler> travelers = new ArrayList<>();
        
        // When
        Trip trip = Trip.builder()
                .name(name)
                .description(description)
                .createdAt(createdAt)
                .flight(flight)
                .hotel(hotel)
                .travelers(travelers)
                .build();
        
        // Then
        assertNotNull(trip);
        assertEquals(name, trip.getName());
        assertEquals(description, trip.getDescription());
        assertEquals(createdAt, trip.getCreatedAt());
        assertNull(trip.getFlight());
        assertNull(trip.getHotel());
        assertEquals(travelers, trip.getTravelers());
    }
    
    @Test
    void testTripAllArgsConstructor() {
        // Given
        Long id = 1L;
        String name = "Winter Holiday";
        String description = "Skiing in Alps";
        LocalDateTime createdAt = LocalDateTime.now();
        Flight flight = null;
        Hotel hotel = null;
        List<Traveler> travelers = new ArrayList<>();
        
        // When
        Trip trip = new Trip(id, name, description, createdAt, flight, hotel, travelers);
        
        // Then
        assertNotNull(trip);
        assertEquals(id, trip.getId());
        assertEquals(name, trip.getName());
        assertEquals(description, trip.getDescription());
        assertEquals(createdAt, trip.getCreatedAt());
        assertNull(trip.getFlight());
        assertNull(trip.getHotel());
        assertEquals(travelers, trip.getTravelers());
    }
    
    @Test
    void testTripNoArgsConstructor() {
        // When
        Trip trip = new Trip();
        
        // Then
        assertNotNull(trip);
        assertNull(trip.getId());
        assertNull(trip.getName());
        assertNull(trip.getDescription());
        assertNull(trip.getCreatedAt());
        assertNull(trip.getFlight());
        assertNull(trip.getHotel());
        assertNull(trip.getTravelers());
    }
    
    @Test
    void testSetters() {
        // Given
        Trip trip = new Trip();
        String name = "City Break";
        String description = "Weekend in Paris";
        
        // When
        trip.setName(name);
        trip.setDescription(description);
        
        // Then
        assertEquals(name, trip.getName());
        assertEquals(description, trip.getDescription());
    }
}