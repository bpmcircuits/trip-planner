package com.kodilla.flightapiservice.repository;

import com.kodilla.flightapiservice.domain.Flight;
import com.kodilla.flightapiservice.domain.FlightSegment;
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
class FlightRepositoryTest {

    @Autowired
    private FlightRepository flightRepository;

    @Test
    void shouldSaveAndFindFlight() {
        // Given
        UUID searchId = UUID.randomUUID();
        LocalDateTime lastUpdated = LocalDateTime.now();
        
        Flight flight = Flight.builder()
                .oneWay(false)
                .searchId(searchId)
                .currency("PLN")
                .totalPrice("2400.00")
                .outboundDurationIso("PT3H35M")
                .outboundDurationMinutes(215)
                .inboundDurationIso("PT3H35M")
                .inboundDurationMinutes(215)
                .lastUpdated(lastUpdated)
                .build();

        // When
        Flight savedFlight = flightRepository.save(flight);
        Optional<Flight> foundFlight = flightRepository.findById(savedFlight.getId());

        // Then
        assertTrue(foundFlight.isPresent());
        assertEquals(searchId, foundFlight.get().getSearchId());
        assertEquals("PLN", foundFlight.get().getCurrency());
        assertEquals("2400.00", foundFlight.get().getTotalPrice());
        assertEquals("PT3H35M", foundFlight.get().getOutboundDurationIso());
        assertEquals(215, foundFlight.get().getOutboundDurationMinutes());

        // Cleanup
        flightRepository.delete(savedFlight);
    }

    @Test
    void shouldFindBySearchId() {
        // Given
        UUID searchId = UUID.randomUUID();
        LocalDateTime lastUpdated = LocalDateTime.now();
        
        Flight flight = Flight.builder()
                .oneWay(false)
                .searchId(searchId)
                .currency("PLN")
                .totalPrice("2400.00")
                .outboundDurationIso("PT3H35M")
                .outboundDurationMinutes(215)
                .inboundDurationIso("PT3H35M")
                .inboundDurationMinutes(215)
                .lastUpdated(lastUpdated)
                .outboundSegments(new ArrayList<>())
                .inboundSegments(new ArrayList<>())
                .travelerPrices(new ArrayList<>())
                .build();

        flightRepository.save(flight);

        // When
        Optional<Flight> foundFlight = flightRepository.findBySearchId(searchId);

        // Then
        assertTrue(foundFlight.isPresent());
        assertEquals(searchId, foundFlight.get().getSearchId());
        assertEquals("PLN", foundFlight.get().getCurrency());
        assertEquals("2400.00", foundFlight.get().getTotalPrice());

        // Cleanup
        flightRepository.delete(flight);
    }

    @Test
    void shouldFindByLastUpdatedBefore() {
        // Given
        UUID searchId1 = UUID.randomUUID();
        UUID searchId2 = UUID.randomUUID();
        LocalDateTime oldDate = LocalDateTime.now().minusDays(31);
        LocalDateTime recentDate = LocalDateTime.now();
        
        Flight oldFlight = Flight.builder()
                .oneWay(false)
                .searchId(searchId1)
                .currency("PLN")
                .totalPrice("2400.00")
                .outboundDurationIso("PT3H35M")
                .outboundDurationMinutes(215)
                .inboundDurationIso("PT3H35M")
                .inboundDurationMinutes(215)
                .lastUpdated(oldDate)
                .outboundSegments(new ArrayList<>())
                .inboundSegments(new ArrayList<>())
                .travelerPrices(new ArrayList<>())
                .build();

        Flight recentFlight = Flight.builder()
                .oneWay(false)
                .searchId(searchId2)
                .currency("PLN")
                .totalPrice("2400.00")
                .outboundDurationIso("PT3H35M")
                .outboundDurationMinutes(215)
                .inboundDurationIso("PT3H35M")
                .inboundDurationMinutes(215)
                .lastUpdated(recentDate)
                .outboundSegments(new ArrayList<>())
                .inboundSegments(new ArrayList<>())
                .travelerPrices(new ArrayList<>())
                .build();

        flightRepository.save(oldFlight);
        flightRepository.save(recentFlight);

        // When
        LocalDateTime cutoffDate = LocalDateTime.now().minusDays(30);
        List<Flight> outdatedFlights = flightRepository.findByLastUpdatedBefore(cutoffDate);

        // Then
        assertEquals(1, outdatedFlights.size());
        assertEquals(searchId1, outdatedFlights.get(0).getSearchId());

        // Cleanup
        flightRepository.deleteAll();
    }

    @Test
    void shouldFindExistingFlight() {
        // Given
        UUID searchId = UUID.randomUUID();
        LocalDateTime lastUpdated = LocalDateTime.now();
        
        Flight flight = Flight.builder()
                .oneWay(false)
                .searchId(searchId)
                .currency("PLN")
                .totalPrice("2400.00")
                .outboundDurationIso("PT3H35M")
                .outboundDurationMinutes(215)
                .inboundDurationIso("PT3H35M")
                .inboundDurationMinutes(215)
                .lastUpdated(lastUpdated)
                .outboundSegments(new ArrayList<>())
                .inboundSegments(new ArrayList<>())
                .travelerPrices(new ArrayList<>())
                .build();

        flightRepository.save(flight);

        // When
        Optional<Flight> foundFlight = flightRepository.findExistingFlight(searchId);

        // Then
        assertTrue(foundFlight.isPresent());
        assertEquals(searchId, foundFlight.get().getSearchId());
        assertEquals("PLN", foundFlight.get().getCurrency());
        assertEquals("2400.00", foundFlight.get().getTotalPrice());

        // Cleanup
        flightRepository.delete(flight);
    }

    @Test
    void shouldAddAndRetrieveFlightSegments() {
        // Given
        UUID searchId = UUID.randomUUID();
        LocalDateTime lastUpdated = LocalDateTime.now();
        
        Flight flight = Flight.builder()
                .oneWay(false)
                .searchId(searchId)
                .currency("PLN")
                .totalPrice("2400.00")
                .outboundDurationIso("PT3H35M")
                .outboundDurationMinutes(215)
                .inboundDurationIso("PT3H35M")
                .inboundDurationMinutes(215)
                .lastUpdated(lastUpdated)
                .outboundSegments(new ArrayList<>())
                .inboundSegments(new ArrayList<>())
                .travelerPrices(new ArrayList<>())
                .build();

        FlightSegment outboundSegment = FlightSegment.builder()
                .fromIata("WAW")
                .toIata("AYT")
                .carrierCode("XQ")
                .departureAt("2025-08-09T18:40:00Z")
                .arrivalAt("2025-08-09T22:15:00Z")
                .build();

        FlightSegment inboundSegment = FlightSegment.builder()
                .fromIata("AYT")
                .toIata("WAW")
                .carrierCode("XQ")
                .departureAt("2025-08-16T23:05:00Z")
                .arrivalAt("2025-08-17T00:40:00Z")
                .build();

        flight.addOutboundSegment(outboundSegment);
        flight.addInboundSegment(inboundSegment);

        // When
        Flight savedFlight = flightRepository.save(flight);
        Optional<Flight> foundFlight = flightRepository.findById(savedFlight.getId());

        // Then
        assertTrue(foundFlight.isPresent());
        assertEquals(1, foundFlight.get().getOutboundSegments().size());
        assertEquals(1, foundFlight.get().getInboundSegments().size());
        assertEquals("WAW", foundFlight.get().getOutboundSegments().get(0).getFromIata());
        assertEquals("AYT", foundFlight.get().getOutboundSegments().get(0).getToIata());
        assertEquals("AYT", foundFlight.get().getInboundSegments().get(0).getFromIata());
        assertEquals("WAW", foundFlight.get().getInboundSegments().get(0).getToIata());

        // Cleanup
        flightRepository.delete(savedFlight);
    }

    @Test
    void shouldAddAndRetrieveTravelerPrices() {
        // Given
        UUID searchId = UUID.randomUUID();
        LocalDateTime lastUpdated = LocalDateTime.now();
        
        Flight flight = Flight.builder()
                .oneWay(false)
                .searchId(searchId)
                .currency("PLN")
                .totalPrice("2400.00")
                .outboundDurationIso("PT3H35M")
                .outboundDurationMinutes(215)
                .inboundDurationIso("PT3H35M")
                .inboundDurationMinutes(215)
                .lastUpdated(lastUpdated)
                .outboundSegments(new ArrayList<>())
                .inboundSegments(new ArrayList<>())
                .travelerPrices(new ArrayList<>())
                .build();

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

        flight.addTravelerPrice(adultPrice);
        flight.addTravelerPrice(childPrice);

        // When
        Flight savedFlight = flightRepository.save(flight);
        Optional<Flight> foundFlight = flightRepository.findById(savedFlight.getId());

        // Then
        assertTrue(foundFlight.isPresent());
        assertEquals(2, foundFlight.get().getTravelerPrices().size());
        
        List<TravelerPrice> prices = foundFlight.get().getTravelerPrices();
        assertTrue(prices.stream().anyMatch(p -> p.getTravelerType().equals("ADULT") && p.getPrice().equals("1200.00")));
        assertTrue(prices.stream().anyMatch(p -> p.getTravelerType().equals("CHILD") && p.getPrice().equals("800.00")));

        // Cleanup
        flightRepository.delete(savedFlight);
    }
}