package com.kodilla.tripapiservice.repository;

import com.kodilla.tripapiservice.domain.Trip;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class TripRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private TripRepository tripRepository;

    private final LocalDateTime createdAt = LocalDateTime.of(2025, 8, 18, 10, 0);

    @Test
    void shouldSaveTrip() {
        // Given
        Trip trip = Trip.builder()
                .name("Summer Vacation")
                .description("Family vacation to Spain")
                .createdAt(createdAt)
                .flightId(100L)
                .hotelId(200L)
                .travelers(new ArrayList<>())
                .build();

        // When
        Trip savedTrip = tripRepository.save(trip);

        // Then
        assertThat(savedTrip.getId()).isNotNull();
        assertThat(savedTrip.getName()).isEqualTo("Summer Vacation");
        assertThat(savedTrip.getDescription()).isEqualTo("Family vacation to Spain");
        assertThat(savedTrip.getCreatedAt()).isEqualTo(createdAt);
        assertThat(savedTrip.getFlightId()).isEqualTo(100L);
        assertThat(savedTrip.getHotelId()).isEqualTo(200L);
    }

    @Test
    void shouldFindTripById() {
        // Given
        Trip trip = Trip.builder()
                .name("Summer Vacation")
                .description("Family vacation to Spain")
                .createdAt(createdAt)
                .flightId(100L)
                .hotelId(200L)
                .travelers(new ArrayList<>())
                .build();
        
        entityManager.persist(trip);
        entityManager.flush();

        // When
        Optional<Trip> foundTrip = tripRepository.findById(trip.getId());

        // Then
        assertThat(foundTrip).isPresent();
        assertThat(foundTrip.get().getName()).isEqualTo("Summer Vacation");
        assertThat(foundTrip.get().getDescription()).isEqualTo("Family vacation to Spain");
    }

    @Test
    void shouldFindAllTrips() {
        // Given
        Trip trip1 = Trip.builder()
                .name("Summer Vacation")
                .description("Family vacation to Spain")
                .createdAt(createdAt)
                .flightId(100L)
                .hotelId(200L)
                .travelers(new ArrayList<>())
                .build();
        
        Trip trip2 = Trip.builder()
                .name("Winter Vacation")
                .description("Skiing in the Alps")
                .createdAt(createdAt.plusMonths(6))
                .flightId(101L)
                .hotelId(201L)
                .travelers(new ArrayList<>())
                .build();
        
        entityManager.persist(trip1);
        entityManager.persist(trip2);
        entityManager.flush();

        // When
        List<Trip> trips = tripRepository.findAll();

        // Then
        assertThat(trips).hasSize(2);
        assertThat(trips).extracting(Trip::getName).containsExactlyInAnyOrder("Summer Vacation", "Winter Vacation");
    }

    @Test
    void shouldUpdateTrip() {
        // Given
        Trip trip = Trip.builder()
                .name("Summer Vacation")
                .description("Family vacation to Spain")
                .createdAt(createdAt)
                .flightId(100L)
                .hotelId(200L)
                .travelers(new ArrayList<>())
                .build();
        
        entityManager.persist(trip);
        entityManager.flush();
        
        // When
        Trip savedTrip = tripRepository.findById(trip.getId()).get();
        savedTrip = Trip.builder()
                .id(savedTrip.getId())
                .name("Updated Summer Vacation")
                .description("Family vacation to Italy")
                .createdAt(savedTrip.getCreatedAt())
                .flightId(102L)
                .hotelId(202L)
                .travelers(new ArrayList<>())
                .build();
        
        tripRepository.save(savedTrip);
        
        // Then
        Trip updatedTrip = tripRepository.findById(trip.getId()).get();
        assertThat(updatedTrip.getName()).isEqualTo("Updated Summer Vacation");
        assertThat(updatedTrip.getDescription()).isEqualTo("Family vacation to Italy");
        assertThat(updatedTrip.getFlightId()).isEqualTo(102L);
        assertThat(updatedTrip.getHotelId()).isEqualTo(202L);
    }

    @Test
    void shouldDeleteTrip() {
        // Given
        Trip trip = Trip.builder()
                .name("Summer Vacation")
                .description("Family vacation to Spain")
                .createdAt(createdAt)
                .flightId(100L)
                .hotelId(200L)
                .travelers(new ArrayList<>())
                .build();
        
        entityManager.persist(trip);
        entityManager.flush();
        
        // When
        tripRepository.deleteById(trip.getId());
        
        // Then
        Optional<Trip> deletedTrip = tripRepository.findById(trip.getId());
        assertThat(deletedTrip).isEmpty();
    }

    @Test
    void shouldCheckIfTripExists() {
        // Given
        Trip trip = Trip.builder()
                .name("Summer Vacation")
                .description("Family vacation to Spain")
                .createdAt(createdAt)
                .flightId(100L)
                .hotelId(200L)
                .travelers(new ArrayList<>())
                .build();
        
        entityManager.persist(trip);
        entityManager.flush();
        
        // When & Then
        assertThat(tripRepository.existsById(trip.getId())).isTrue();
        assertThat(tripRepository.existsById(999L)).isFalse();
    }
}