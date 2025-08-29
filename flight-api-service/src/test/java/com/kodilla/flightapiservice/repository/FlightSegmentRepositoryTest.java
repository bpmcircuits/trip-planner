package com.kodilla.flightapiservice.repository;

import com.kodilla.flightapiservice.domain.Flight;
import com.kodilla.flightapiservice.domain.FlightSegment;
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
class FlightSegmentRepositoryTest {

    @Autowired
    private FlightSegmentRepository flightSegmentRepository;

    @Autowired
    private FlightRepository flightRepository;

    @Test
    void shouldSaveAndFindFlightSegment() {
        // Given
        Flight flight = createTestFlight();
        FlightSegment segment = FlightSegment.builder()
                .fromIata("WAW")
                .toIata("AYT")
                .carrierCode("XQ")
                .departureAt("2025-08-09T18:40:00Z")
                .arrivalAt("2025-08-09T22:15:00Z")
                .direction("outbound")
                .build();
        
        flight.addOutboundSegment(segment);
        flightRepository.save(flight);

        // When
        Optional<FlightSegment> foundSegment = flightSegmentRepository.findById(segment.getId());

        // Then
        assertTrue(foundSegment.isPresent());
        assertEquals("WAW", foundSegment.get().getFromIata());
        assertEquals("AYT", foundSegment.get().getToIata());
        assertEquals("XQ", foundSegment.get().getCarrierCode());
        assertEquals("outbound", foundSegment.get().getDirection());

        // Cleanup
        flightRepository.delete(flight);
    }

    @Test
    void shouldFindByFlightIdAndDirection() {
        // Given
        Flight flight = createTestFlight();
        
        FlightSegment outboundSegment1 = FlightSegment.builder()
                .fromIata("WAW")
                .toIata("MUC")
                .carrierCode("LH")
                .departureAt("2025-08-09T10:00:00Z")
                .arrivalAt("2025-08-09T12:00:00Z")
                .direction("outbound")
                .build();
        
        FlightSegment outboundSegment2 = FlightSegment.builder()
                .fromIata("MUC")
                .toIata("AYT")
                .carrierCode("LH")
                .departureAt("2025-08-09T13:00:00Z")
                .arrivalAt("2025-08-09T16:00:00Z")
                .direction("outbound")
                .build();
        
        FlightSegment inboundSegment = FlightSegment.builder()
                .fromIata("AYT")
                .toIata("WAW")
                .carrierCode("XQ")
                .departureAt("2025-08-16T23:05:00Z")
                .arrivalAt("2025-08-17T00:40:00Z")
                .direction("inbound")
                .build();
        
        flight.addOutboundSegment(outboundSegment1);
        flight.addOutboundSegment(outboundSegment2);
        flight.addInboundSegment(inboundSegment);
        
        Flight savedFlight = flightRepository.save(flight);

        // When
        List<FlightSegment> outboundSegments = flightSegmentRepository.findByFlightIdAndDirection(savedFlight.getId(), "outbound");
        List<FlightSegment> inboundSegments = flightSegmentRepository.findByFlightIdAndDirection(savedFlight.getId(), "inbound");

        // Then
        assertEquals(2, outboundSegments.size());
        assertEquals(1, inboundSegments.size());
        
        assertTrue(outboundSegments.stream().anyMatch(s -> s.getFromIata().equals("WAW") && s.getToIata().equals("MUC")));
        assertTrue(outboundSegments.stream().anyMatch(s -> s.getFromIata().equals("MUC") && s.getToIata().equals("AYT")));
        assertEquals("AYT", inboundSegments.get(0).getFromIata());
        assertEquals("WAW", inboundSegments.get(0).getToIata());

        // Cleanup
        flightRepository.delete(savedFlight);
    }

    @Test
    void shouldFindByFromIataAndToIataAndCarrierCode() {
        // Given
        Flight flight = createTestFlight();
        
        FlightSegment segment1 = FlightSegment.builder()
                .fromIata("WAW")
                .toIata("AYT")
                .carrierCode("XQ")
                .departureAt("2025-08-09T18:40:00Z")
                .arrivalAt("2025-08-09T22:15:00Z")
                .direction("outbound")
                .build();
        
        FlightSegment segment2 = FlightSegment.builder()
                .fromIata("WAW")
                .toIata("AYT")
                .carrierCode("XQ")
                .departureAt("2025-08-10T18:40:00Z")
                .arrivalAt("2025-08-10T22:15:00Z")
                .direction("outbound")
                .build();
        
        FlightSegment segment3 = FlightSegment.builder()
                .fromIata("WAW")
                .toIata("AYT")
                .carrierCode("TK")
                .departureAt("2025-08-11T18:40:00Z")
                .arrivalAt("2025-08-11T22:15:00Z")
                .direction("outbound")
                .build();
        
        flight.addOutboundSegment(segment1);
        flight.addOutboundSegment(segment2);
        flight.addOutboundSegment(segment3);
        
        flightRepository.save(flight);

        // When
        List<FlightSegment> foundSegments = flightSegmentRepository.findByFromIataAndToIataAndCarrierCode("WAW", "AYT", "XQ");

        // Then
        assertEquals(2, foundSegments.size());
        assertTrue(foundSegments.stream().allMatch(s -> s.getFromIata().equals("WAW") && s.getToIata().equals("AYT") && s.getCarrierCode().equals("XQ")));

        // Cleanup
        flightRepository.delete(flight);
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