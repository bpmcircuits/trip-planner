package com.kodilla.tripplanner.mapper;

import com.kodilla.tripplanner.domain.Flight;
import com.kodilla.tripplanner.dto.FlightDTO;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

class FlightMapperTest {

    private final FlightMapper mapper = new FlightMapper();

    @Test
    void shouldMapToFlightDTO() {
        Flight flight = Flight.builder()
                .id(1L)
                .flightNumber("LO123")
                .departureAirport("WAW")
                .departureAirportCode("WAW")
                .arrivalAirport("JFK")
                .arrivalAirportCode("JFK")
                .departureDateTime(LocalDateTime.now())
                .arrivalDateTime(LocalDateTime.now().plusHours(9))
                .price(BigDecimal.valueOf(1000.0))
                .currency("PLN")
                .build();

        FlightDTO dto = mapper.toFlightDTO(flight);

        assertThat(dto).isNotNull();
        assertThat(dto.id()).isEqualTo(1L);
        assertThat(dto.flightNumber()).isEqualTo("LO123");
        assertThat(dto.departureAirport()).isEqualTo("WAW");
        assertThat(dto.arrivalAirport()).isEqualTo("JFK");
        assertThat(dto.price()).isEqualTo(BigDecimal.valueOf(1000.0));
        assertThat(dto.currency()).isEqualTo("PLN");
    }

    @Test
    void shouldMapToFlight() {
        LocalDateTime now = LocalDateTime.now();
        FlightDTO dto = new FlightDTO(2L, "LH456", "FRA",
                "FRA", "ORD", "ORD",
                now, now.plusHours(10), BigDecimal.valueOf(2000.0), "EUR");

        Flight flight = mapper.toFlight(dto);

        assertThat(flight).isNotNull();
        assertThat(flight.getId()).isEqualTo(2L);
        assertThat(flight.getFlightNumber()).isEqualTo("LH456");
        assertThat(flight.getDepartureAirport()).isEqualTo("FRA");
        assertThat(flight.getArrivalAirport()).isEqualTo("ORD");
        assertThat(flight.getPrice()).isEqualTo(BigDecimal.valueOf(2000.0));
        assertThat(flight.getCurrency()).isEqualTo("EUR");
    }

    @Test
    void shouldReturnNullForNullInput() {
        assertThat(mapper.toFlightDTO(null)).isNull();
        assertThat(mapper.toFlight(null)).isNull();
    }
}