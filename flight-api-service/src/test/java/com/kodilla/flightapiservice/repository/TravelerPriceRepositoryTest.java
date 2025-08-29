package com.kodilla.flightapiservice.repository;

import com.kodilla.flightapiservice.domain.Flight;
import com.kodilla.flightapiservice.domain.TravelerPrice;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
class TravelerPriceRepositoryTest {

    @Autowired
    private TravelerPriceRepository travelerPriceRepository;

    @Autowired
    private FlightRepository flightRepository;

    @Test
    void shouldSaveAndFindTravelerPrice() {
        // Given
        Flight flight = createTestFlight();
        TravelerPrice travelerPrice = TravelerPrice.builder()
                .travelerType("ADULT")
                .currency("PLN")
                .price("1200.00")
                .build();
        
        flight.addTravelerPrice(travelerPrice);
        flightRepository.save(flight);

        // When
        Optional<TravelerPrice> foundTravelerPrice = travelerPriceRepository.findById(travelerPrice.getId());

        // Then
        assertTrue(foundTravelerPrice.isPresent());
        assertEquals("ADULT", foundTravelerPrice.get().getTravelerType());
        assertEquals("PLN", foundTravelerPrice.get().getCurrency());
        assertEquals("1200.00", foundTravelerPrice.get().getPrice());

        // Cleanup
        flightRepository.delete(flight);
    }

    @Test
    void shouldFindByFlightId() {
        // Given
        Flight flight = createTestFlight();
        
        TravelerPrice adultPrice = TravelerPrice.builder()
                .travelerType("ADULT")
                .currency("PLN")
                .price("1200.00")
                .build();
        
        TravelerPrice childPrice = TravelerPrice.builder()
                .travelerType("CHILD")
                .currency("PLN")
                .price("800.00")
                .build();
        
        TravelerPrice infantPrice = TravelerPrice.builder()
                .travelerType("INFANT")
                .currency("PLN")
                .price("200.00")
                .build();
        
        flight.addTravelerPrice(adultPrice);
        flight.addTravelerPrice(childPrice);
        flight.addTravelerPrice(infantPrice);
        
        Flight savedFlight = flightRepository.save(flight);

        // When
        List<TravelerPrice> travelerPrices = travelerPriceRepository.findByFlightId(savedFlight.getId());

        // Then
        assertEquals(3, travelerPrices.size());
        assertTrue(travelerPrices.stream().anyMatch(p -> p.getTravelerType().equals("ADULT") && p.getPrice().equals("1200.00")));
        assertTrue(travelerPrices.stream().anyMatch(p -> p.getTravelerType().equals("CHILD") && p.getPrice().equals("800.00")));
        assertTrue(travelerPrices.stream().anyMatch(p -> p.getTravelerType().equals("INFANT") && p.getPrice().equals("200.00")));

        // Cleanup
        flightRepository.delete(savedFlight);
    }

    @Test
    void shouldFindByTravelerType() {
        // Given
        Flight flight1 = createTestFlight();
        Flight flight2 = createTestFlight();
        
        TravelerPrice adultPrice1 = TravelerPrice.builder()
                .travelerType("ADULT")
                .currency("PLN")
                .price("1200.00")
                .build();
        
        TravelerPrice adultPrice2 = TravelerPrice.builder()
                .travelerType("ADULT")
                .currency("PLN")
                .price("1500.00")
                .build();
        
        TravelerPrice childPrice = TravelerPrice.builder()
                .travelerType("CHILD")
                .currency("PLN")
                .price("800.00")
                .build();
        
        flight1.addTravelerPrice(adultPrice1);
        flight1.addTravelerPrice(childPrice);
        flight2.addTravelerPrice(adultPrice2);
        
        flightRepository.save(flight1);
        flightRepository.save(flight2);

        // When
        List<TravelerPrice> adultPrices = travelerPriceRepository.findByTravelerType("ADULT");
        List<TravelerPrice> childPrices = travelerPriceRepository.findByTravelerType("CHILD");
        List<TravelerPrice> infantPrices = travelerPriceRepository.findByTravelerType("INFANT");

        // Then
        assertEquals(2, adultPrices.size());
        assertEquals(1, childPrices.size());
        assertEquals(0, infantPrices.size());
        
        assertTrue(adultPrices.stream().anyMatch(p -> p.getPrice().equals("1200.00")));
        assertTrue(adultPrices.stream().anyMatch(p -> p.getPrice().equals("1500.00")));
        assertEquals("800.00", childPrices.get(0).getPrice());

        // Cleanup
        flightRepository.deleteAll();
    }

    private Flight createTestFlight() {
        return Flight.builder()
                .oneWay(false)
                .searchId(UUID.randomUUID())
                .currency("PLN")
                .totalPrice("2400.00")
                .outboundDurationIso("PT3H35M")
                .outboundDurationMinutes(215)
                .inboundDurationIso("PT3H35M")
                .inboundDurationMinutes(215)
                .lastUpdated(LocalDateTime.now())
                .outboundSegments(new ArrayList<>())
                .inboundSegments(new ArrayList<>())
                .travelerPrices(new ArrayList<>())
                .build();
    }
}