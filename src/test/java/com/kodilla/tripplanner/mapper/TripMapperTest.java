package com.kodilla.tripplanner.mapper;

import com.kodilla.tripplanner.domain.Flight;
import com.kodilla.tripplanner.domain.Hotel;
import com.kodilla.tripplanner.domain.Traveler;
import com.kodilla.tripplanner.domain.Trip;
import com.kodilla.tripplanner.dto.TripDTO;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class TripMapperTest {

    private final TripMapper mapper = new TripMapper();

    @Test
    void shouldMapToTripDTO() {
        Flight flight = Flight.builder().id(1L).build();
        Hotel hotel = Hotel.builder().id(2L).build();
        Traveler traveler = Traveler.builder().id(3L).build();
        Trip trip = Trip.builder()
                .id(4L)
                .name("Wakacje")
                .description("Opis")
                .createdAt(LocalDateTime.now())
                .flight(flight)
                .hotel(hotel)
                .travelers(List.of(traveler))
                .build();

        TripDTO dto = mapper.toTripDTO(trip);

        assertThat(dto).isNotNull();
        assertThat(dto.id()).isEqualTo(4L);
        assertThat(dto.name()).isEqualTo("Wakacje");
        assertThat(dto.description()).isEqualTo("Opis");
        assertThat(dto.flightId()).isEqualTo(1L);
        assertThat(dto.hotelId()).isEqualTo(2L);
        assertThat(dto.travelerIds()).containsExactly(3L);
    }

    @Test
    void shouldMapToTrip() {
        TripDTO dto = new TripDTO(5L, "Wyjazd", "Opis2",
                LocalDateTime.now(), 6L, 7L, List.of(8L));
        Flight flight = Flight.builder().id(6L).build();
        Hotel hotel = Hotel.builder().id(7L).build();
        Traveler traveler = Traveler.builder().id(8L).build();

        Trip trip = mapper.toTrip(dto, flight, hotel, List.of(traveler));

        assertThat(trip).isNotNull();
        assertThat(trip.getId()).isEqualTo(5L);
        assertThat(trip.getName()).isEqualTo("Wyjazd");
        assertThat(trip.getDescription()).isEqualTo("Opis2");
        assertThat(trip.getFlight()).isEqualTo(flight);
        assertThat(trip.getHotel()).isEqualTo(hotel);
        assertThat(trip.getTravelers()).containsExactly(traveler);
    }

    @Test
    void shouldReturnNullForNullInput() {
        assertThat(mapper.toTripDTO(null)).isNull();
        assertThat(mapper.toTrip(null, null, null, null)).isNull();
    }
}