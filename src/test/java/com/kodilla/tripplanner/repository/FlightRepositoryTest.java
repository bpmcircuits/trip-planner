package com.kodilla.tripplanner.repository;

import com.kodilla.tripplanner.domain.Flight;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class FlightRepositoryTest {

    @Autowired
    private FlightRepository flightRepository;

    @Test
    void shouldSaveAndFindFlightNumber() {
        Flight flight = Flight.builder()
                .flightNumber("LO123")
                .airline("LOT")
                .departureAirport("Warszawa")
                .departureAirportCode("WAW")
                .arrivalAirport("Paryż")
                .arrivalAirportCode("CDG")
                .departureDateTime(LocalDateTime.now())
                .arrivalDateTime(LocalDateTime.now().plusHours(2))
                .price(BigDecimal.valueOf(500))
                .currency("PLN")
                .build();
        Flight saved = null;
        try {
            saved = flightRepository.save(flight);
            Optional<Flight> found = flightRepository.findById(saved.getId());
            assertThat(found).isPresent();
            assertThat(found.get().getFlightNumber()).isEqualTo("LO123");
        } finally {
            if (saved != null && saved.getId() != null) flightRepository.deleteById(saved.getId());
        }
    }

    @Test
    void shouldSaveAndFindAirline() {
        Flight flight = Flight.builder()
                .flightNumber("AF456")
                .airline("Air France")
                .departureAirport("Paryż")
                .departureAirportCode("CDG")
                .arrivalAirport("Berlin")
                .arrivalAirportCode("BER")
                .departureDateTime(LocalDateTime.now())
                .arrivalDateTime(LocalDateTime.now().plusHours(1))
                .price(BigDecimal.valueOf(300))
                .currency("EUR")
                .build();
        Flight saved = null;
        try {
            saved = flightRepository.save(flight);
            Optional<Flight> found = flightRepository.findById(saved.getId());
            assertThat(found).isPresent();
            assertThat(found.get().getAirline()).isEqualTo("Air France");
        } finally {
            if (saved != null && saved.getId() != null) flightRepository.deleteById(saved.getId());
        }
    }

    @Test
    void shouldSaveAndFindDepartureAirport() {
        Flight flight = Flight.builder()
                .flightNumber("LH789")
                .airline("Lufthansa")
                .departureAirport("Frankfurt")
                .departureAirportCode("FRA")
                .arrivalAirport("Nowy Jork")
                .arrivalAirportCode("JFK")
                .departureDateTime(LocalDateTime.now())
                .arrivalDateTime(LocalDateTime.now().plusHours(9))
                .price(BigDecimal.valueOf(1200))
                .currency("EUR")
                .build();
        Flight saved = null;
        try {
            saved = flightRepository.save(flight);
            Optional<Flight> found = flightRepository.findById(saved.getId());
            assertThat(found).isPresent();
            assertThat(found.get().getDepartureAirport()).isEqualTo("Frankfurt");
        } finally {
            if (saved != null && saved.getId() != null) flightRepository.deleteById(saved.getId());
        }
    }

    @Test
    void shouldSaveAndFindDepartureAirportCode() {
        Flight flight = Flight.builder()
                .flightNumber("BA001")
                .airline("British Airways")
                .departureAirport("Londyn")
                .departureAirportCode("LHR")
                .arrivalAirport("Warszawa")
                .arrivalAirportCode("WAW")
                .departureDateTime(LocalDateTime.now())
                .arrivalDateTime(LocalDateTime.now().plusHours(2))
                .price(BigDecimal.valueOf(400))
                .currency("GBP")
                .build();
        Flight saved = null;
        try {
            saved = flightRepository.save(flight);
            Optional<Flight> found = flightRepository.findById(saved.getId());
            assertThat(found).isPresent();
            assertThat(found.get().getDepartureAirportCode()).isEqualTo("LHR");
        } finally {
            if (saved != null && saved.getId() != null) flightRepository.deleteById(saved.getId());
        }
    }

    @Test
    void shouldSaveAndFindArrivalAirport() {
        Flight flight = Flight.builder()
                .flightNumber("UA100")
                .airline("United Airlines")
                .departureAirport("Chicago")
                .departureAirportCode("ORD")
                .arrivalAirport("Los Angeles")
                .arrivalAirportCode("LAX")
                .departureDateTime(LocalDateTime.now())
                .arrivalDateTime(LocalDateTime.now().plusHours(4))
                .price(BigDecimal.valueOf(800))
                .currency("USD")
                .build();
        Flight saved = null;
        try {
            saved = flightRepository.save(flight);
            Optional<Flight> found = flightRepository.findById(saved.getId());
            assertThat(found).isPresent();
            assertThat(found.get().getArrivalAirport()).isEqualTo("Los Angeles");
        } finally {
            if (saved != null && saved.getId() != null) flightRepository.deleteById(saved.getId());
        }
    }

    @Test
    void shouldSaveAndFindArrivalAirportCode() {
        Flight flight = Flight.builder()
                .flightNumber("DL200")
                .airline("Delta")
                .departureAirport("Atlanta")
                .departureAirportCode("ATL")
                .arrivalAirport("Miami")
                .arrivalAirportCode("MIA")
                .departureDateTime(LocalDateTime.now())
                .arrivalDateTime(LocalDateTime.now().plusHours(2))
                .price(BigDecimal.valueOf(350))
                .currency("USD")
                .build();
        Flight saved = null;
        try {
            saved = flightRepository.save(flight);
            Optional<Flight> found = flightRepository.findById(saved.getId());
            assertThat(found).isPresent();
            assertThat(found.get().getArrivalAirportCode()).isEqualTo("MIA");
        } finally {
            if (saved != null && saved.getId() != null) flightRepository.deleteById(saved.getId());
        }
    }

    @Test
    void shouldSaveAndFindDepartureDateTime() {
        LocalDateTime departure = LocalDateTime.of(2024, 6, 10, 12, 0);
        Flight flight = Flight.builder()
                .flightNumber("FR300")
                .airline("Ryanair")
                .departureAirport("Kraków")
                .departureAirportCode("KRK")
                .arrivalAirport("Londyn")
                .arrivalAirportCode("STN")
                .departureDateTime(departure)
                .arrivalDateTime(departure.plusHours(2))
                .price(BigDecimal.valueOf(150))
                .currency("PLN")
                .build();
        Flight saved = null;
        try {
            saved = flightRepository.save(flight);
            Optional<Flight> found = flightRepository.findById(saved.getId());
            assertThat(found).isPresent();
            assertThat(found.get().getDepartureDateTime()).isEqualTo(departure);
        } finally {
            if (saved != null && saved.getId() != null) flightRepository.deleteById(saved.getId());
        }
    }

    @Test
    void shouldSaveAndFindArrivalDateTime() {
        LocalDateTime arrival = LocalDateTime.of(2024, 7, 1, 18, 30);
        Flight flight = Flight.builder()
                .flightNumber("W6100")
                .airline("Wizzair")
                .departureAirport("Gdańsk")
                .departureAirportCode("GDN")
                .arrivalAirport("Oslo")
                .arrivalAirportCode("OSL")
                .departureDateTime(arrival.minusHours(2))
                .arrivalDateTime(arrival)
                .price(BigDecimal.valueOf(200))
                .currency("PLN")
                .build();
        Flight saved = null;
        try {
            saved = flightRepository.save(flight);
            Optional<Flight> found = flightRepository.findById(saved.getId());
            assertThat(found).isPresent();
            assertThat(found.get().getArrivalDateTime()).isEqualTo(arrival);
        } finally {
            if (saved != null && saved.getId() != null) flightRepository.deleteById(saved.getId());
        }
    }

    @Test
    void shouldSaveAndFindPrice() {
        BigDecimal price = BigDecimal.valueOf(999.99);
        Flight flight = Flight.builder()
                .flightNumber("EK500")
                .airline("Emirates")
                .departureAirport("Dubaj")
                .departureAirportCode("DXB")
                .arrivalAirport("Sydney")
                .arrivalAirportCode("SYD")
                .departureDateTime(LocalDateTime.now())
                .arrivalDateTime(LocalDateTime.now().plusHours(14))
                .price(price)
                .currency("AED")
                .build();
        Flight saved = null;
        try {
            saved = flightRepository.save(flight);
            Optional<Flight> found = flightRepository.findById(saved.getId());
            assertThat(found).isPresent();
            assertThat(found.get().getPrice()).isEqualByComparingTo(price);
        } finally {
            if (saved != null && saved.getId() != null) flightRepository.deleteById(saved.getId());
        }
    }

    @Test
    void shouldSaveAndFindCurrency() {
        Flight flight = Flight.builder()
                .flightNumber("QF1")
                .airline("Qantas")
                .departureAirport("Londyn")
                .departureAirportCode("LHR")
                .arrivalAirport("Sydney")
                .arrivalAirportCode("SYD")
                .departureDateTime(LocalDateTime.now())
                .arrivalDateTime(LocalDateTime.now().plusHours(20))
                .price(BigDecimal.valueOf(2000))
                .currency("AUD")
                .build();
        Flight saved = null;
        try {
            saved = flightRepository.save(flight);
            Optional<Flight> found = flightRepository.findById(saved.getId());
            assertThat(found).isPresent();
            assertThat(found.get().getCurrency()).isEqualTo("AUD");
        } finally {
            if (saved != null && saved.getId() != null) flightRepository.deleteById(saved.getId());
        }
    }
}