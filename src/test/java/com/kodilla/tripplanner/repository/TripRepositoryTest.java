package com.kodilla.tripplanner.repository;

import com.kodilla.tripplanner.domain.Flight;
import com.kodilla.tripplanner.domain.Hotel;
import com.kodilla.tripplanner.domain.Trip;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class TripRepositoryTest {

    @Autowired
    private TripRepository tripRepository;

    @Autowired(required = false)
    private FlightRepository flightRepository;

    @Autowired(required = false)
    private HotelRepository hotelRepository;

    private Flight createFlight() {
        if (flightRepository == null) return null;
        Flight flight = Flight.builder()
                .flightNumber(UUID.randomUUID().toString())
                .airline("Airline")
                .departureAirport("Warsaw Chopin Airport")
                .departureAirportCode("WAW")
                .arrivalAirport("John F. Kennedy International Airport")
                .arrivalAirportCode("JFK")
                .departureDateTime(LocalDateTime.now())
                .arrivalDateTime(LocalDateTime.now().plusHours(10))
                .price(BigDecimal.valueOf(1500))
                .currency("USD")
                .build();
        return flightRepository.save(flight);
    }

    private Hotel createHotel() {
        if (hotelRepository == null) return null;
        Hotel hotel = Hotel.builder()
                .name("Hilton")
                .country("Poland")
                .city("Łódź")
                .checkInDate(LocalDateTime.now().toLocalDate())
                .checkOutDate(LocalDateTime.now().plusDays(2).toLocalDate())
                .price(BigDecimal.valueOf(500))
                .build();
        return hotelRepository.save(hotel);
    }

    @Test
    void shouldSaveAndFindName() {
        Trip trip = Trip.builder()
                .name("Wakacje")
                .createdAt(LocalDateTime.now())
                .build();
        Trip saved = null;
        try {
            saved = tripRepository.save(trip);
            Optional<Trip> found = tripRepository.findById(saved.getId());
            assertThat(found).isPresent();
            assertThat(found.get().getName()).isEqualTo("Wakacje");
        } finally {
            if (saved != null && saved.getId() != null) tripRepository.deleteById(saved.getId());
        }
    }

    @Test
    void shouldSaveAndFindDescription() {
        Trip trip = Trip.builder()
                .name("Wyjazd")
                .description("Opis wyjazdu")
                .createdAt(LocalDateTime.now())
                .build();
        Trip saved = null;
        try {
            saved = tripRepository.save(trip);
            Optional<Trip> found = tripRepository.findById(saved.getId());
            assertThat(found).isPresent();
            assertThat(found.get().getDescription()).isEqualTo("Opis wyjazdu");
        } finally {
            if (saved != null && saved.getId() != null) tripRepository.deleteById(saved.getId());
        }
    }

    @Test
    void shouldSaveAndFindCreatedAt() {
        LocalDateTime now = LocalDateTime.now();
        Trip trip = Trip.builder()
                .name("Test")
                .createdAt(now)
                .build();
        Trip saved = null;
        try {
            saved = tripRepository.save(trip);
            Optional<Trip> found = tripRepository.findById(saved.getId());
            assertThat(found).isPresent();
            assertThat(found.get().getCreatedAt()).isEqualTo(now);
        } finally {
            if (saved != null && saved.getId() != null) tripRepository.deleteById(saved.getId());
        }
    }

    @Test
    void shouldSaveAndFindFlight() {
        Flight flight = createFlight();
        Trip trip = Trip.builder()
                .name("Lot")
                .createdAt(LocalDateTime.now())
                .flight(flight)
                .build();
        Trip saved = null;
        try {
            saved = tripRepository.save(trip);
            Optional<Trip> found = tripRepository.findById(saved.getId());
            assertThat(found).isPresent();
            if (flight != null) {
                assertThat(found.get().getFlight().getId()).isEqualTo(flight.getId());
            } else {
                assertThat(found.get().getFlight()).isNull();
            }
        } finally {
            if (saved != null && saved.getId() != null) tripRepository.deleteById(saved.getId());
            if (flight != null && flight.getId() != null && flightRepository != null) flightRepository.deleteById(flight.getId());
        }
    }

    @Test
    void shouldSaveAndFindHotel() {
        Hotel hotel = createHotel();
        Trip trip = Trip.builder()
                .name("Hotel")
                .createdAt(LocalDateTime.now())
                .hotel(hotel)
                .build();
        Trip saved = null;
        try {
            saved = tripRepository.save(trip);
            Optional<Trip> found = tripRepository.findById(saved.getId());
            assertThat(found).isPresent();
            if (hotel != null) {
                assertThat(found.get().getHotel().getId()).isEqualTo(hotel.getId());
            } else {
                assertThat(found.get().getHotel()).isNull();
            }
        } finally {
            if (saved != null && saved.getId() != null) tripRepository.deleteById(saved.getId());
            if (hotel != null && hotel.getId() != null && hotelRepository != null) hotelRepository.deleteById(hotel.getId());
        }
    }
}