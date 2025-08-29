package com.kodilla.flightapiservice.mapper;

import com.kodilla.flightapiservice.domain.Flight;
import com.kodilla.flightapiservice.domain.FlightSegment;
import com.kodilla.flightapiservice.domain.TravelerPrice;
import com.kodilla.flightapiservice.dto.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class FlightMapperTest {

    @InjectMocks
    private FlightMapper flightMapper;

    private FlightSearchResponseDTO testFlightResponseDTO;
    private Flight testFlight;

    @BeforeEach
    void setUp() {
        // Create test flight segment DTOs
        FlightSegmentDTO outboundSegmentDTO = new FlightSegmentDTO(
                "WAW",
                "AYT",
                "XQ",
                "2025-08-09T18:40:00Z",
                "2025-08-09T22:15:00Z"
        );

        FlightSegmentDTO inboundSegmentDTO = new FlightSegmentDTO(
                "AYT",
                "WAW",
                "XQ",
                "2025-08-16T23:05:00Z",
                "2025-08-17T00:40:00Z"
        );

        // Create test flight bound DTOs
        FlightBoundDTO outboundDTO = new FlightBoundDTO(
                "PT3H35M",
                215,
                List.of(outboundSegmentDTO)
        );

        FlightBoundDTO inboundDTO = new FlightBoundDTO(
                "PT3H35M",
                215,
                List.of(inboundSegmentDTO)
        );

        // Create test price DTO
        PriceDTO priceDTO = new PriceDTO(
                "PLN",
                "2400.00"
        );

        // Create test traveler price DTO
        TravelerPriceDTO travelerPriceDTO = new TravelerPriceDTO(
                "ADULT",
                new PriceDTO("PLN", "1200.00")
        );

        // Create test flight response DTO
        testFlightResponseDTO = new FlightSearchResponseDTO(
                false,
                outboundDTO,
                inboundDTO,
                priceDTO,
                List.of(travelerPriceDTO)
        );

        // Create test flight segments
        FlightSegment outboundSegment = FlightSegment.builder()
                .id(1L)
                .fromIata("WAW")
                .toIata("AYT")
                .carrierCode("XQ")
                .departureAt("2025-08-09T18:40:00Z")
                .arrivalAt("2025-08-09T22:15:00Z")
                .direction("outbound")
                .segmentOrder(0)
                .build();

        FlightSegment inboundSegment = FlightSegment.builder()
                .id(2L)
                .fromIata("AYT")
                .toIata("WAW")
                .carrierCode("XQ")
                .departureAt("2025-08-16T23:05:00Z")
                .arrivalAt("2025-08-17T00:40:00Z")
                .direction("inbound")
                .segmentOrder(0)
                .build();

        // Create test traveler price
        TravelerPrice travelerPrice = TravelerPrice.builder()
                .id(1L)
                .travelerType("ADULT")
                .currency("PLN")
                .price("1200.00")
                .build();

        // Create test flight
        testFlight = Flight.builder()
                .id(1L)
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

        testFlight.addOutboundSegment(outboundSegment);
        testFlight.addInboundSegment(inboundSegment);
        testFlight.addTravelerPrice(travelerPrice);
    }

    @Test
    void shouldMapToFlight() {
        // When
        Flight result = flightMapper.mapToFlight(testFlightResponseDTO);

        // Then
        assertNotNull(result);
        assertEquals(testFlightResponseDTO.oneWay(), result.isOneWay());
        assertEquals(testFlightResponseDTO.totalPrice().currency(), result.getCurrency());
        assertEquals(testFlightResponseDTO.totalPrice().total(), result.getTotalPrice());
        assertEquals(testFlightResponseDTO.outbound().durationIso(), result.getOutboundDurationIso());
        assertEquals(testFlightResponseDTO.outbound().durationMinutes(), result.getOutboundDurationMinutes());
        assertEquals(testFlightResponseDTO.inbound().durationIso(), result.getInboundDurationIso());
        assertEquals(testFlightResponseDTO.inbound().durationMinutes(), result.getInboundDurationMinutes());
        
        // Check outbound segments
        assertEquals(1, result.getOutboundSegments().size());
        FlightSegment outboundSegment = result.getOutboundSegments().get(0);
        assertEquals("WAW", outboundSegment.getFromIata());
        assertEquals("AYT", outboundSegment.getToIata());
        assertEquals("XQ", outboundSegment.getCarrierCode());
        assertEquals("2025-08-09T18:40:00Z", outboundSegment.getDepartureAt());
        assertEquals("2025-08-09T22:15:00Z", outboundSegment.getArrivalAt());
        
        // Check inbound segments
        assertEquals(1, result.getInboundSegments().size());
        FlightSegment inboundSegment = result.getInboundSegments().get(0);
        assertEquals("AYT", inboundSegment.getFromIata());
        assertEquals("WAW", inboundSegment.getToIata());
        assertEquals("XQ", inboundSegment.getCarrierCode());
        assertEquals("2025-08-16T23:05:00Z", inboundSegment.getDepartureAt());
        assertEquals("2025-08-17T00:40:00Z", inboundSegment.getArrivalAt());
        
        // Check traveler prices
        assertEquals(1, result.getTravelerPrices().size());
        TravelerPrice travelerPrice = result.getTravelerPrices().get(0);
        assertEquals("ADULT", travelerPrice.getTravelerType());
        assertEquals("PLN", travelerPrice.getCurrency());
        assertEquals("1200.00", travelerPrice.getPrice());
    }

    @Test
    void shouldMapToDto() {
        // When
        FlightSearchResponseDTO result = flightMapper.mapToDto(testFlight);

        // Then
        assertNotNull(result);
        assertEquals(testFlight.isOneWay(), result.oneWay());
        assertEquals(testFlight.getCurrency(), result.totalPrice().currency());
        assertEquals(testFlight.getTotalPrice(), result.totalPrice().total());
        assertEquals(testFlight.getOutboundDurationIso(), result.outbound().durationIso());
        assertEquals(testFlight.getOutboundDurationMinutes(), result.outbound().durationMinutes());
        assertEquals(testFlight.getInboundDurationIso(), result.inbound().durationIso());
        assertEquals(testFlight.getInboundDurationMinutes(), result.inbound().durationMinutes());
        
        // Check outbound segments
        assertEquals(1, result.outbound().segments().size());
        FlightSegmentDTO outboundSegment = result.outbound().segments().get(0);
        assertEquals("WAW", outboundSegment.fromIata());
        assertEquals("AYT", outboundSegment.toIata());
        assertEquals("XQ", outboundSegment.carrierCode());
        assertEquals("2025-08-09T18:40:00Z", outboundSegment.departureAt());
        assertEquals("2025-08-09T22:15:00Z", outboundSegment.arrivalAt());
        
        // Check inbound segments
        assertEquals(1, result.inbound().segments().size());
        FlightSegmentDTO inboundSegment = result.inbound().segments().get(0);
        assertEquals("AYT", inboundSegment.fromIata());
        assertEquals("WAW", inboundSegment.toIata());
        assertEquals("XQ", inboundSegment.carrierCode());
        assertEquals("2025-08-16T23:05:00Z", inboundSegment.departureAt());
        assertEquals("2025-08-17T00:40:00Z", inboundSegment.arrivalAt());
        
        // Check traveler prices
        assertEquals(1, result.travelerPrices().size());
        TravelerPriceDTO travelerPrice = result.travelerPrices().get(0);
        assertEquals("ADULT", travelerPrice.travelerType());
        assertEquals("PLN", travelerPrice.price().currency());
        assertEquals("1200.00", travelerPrice.price().total());
    }

    @Test
    void shouldHandleOneWayFlight() {
        // Given
        testFlightResponseDTO = new FlightSearchResponseDTO(
                true,
                testFlightResponseDTO.outbound(),
                null,
                testFlightResponseDTO.totalPrice(),
                testFlightResponseDTO.travelerPrices()
        );

        // When
        Flight result = flightMapper.mapToFlight(testFlightResponseDTO);

        // Then
        assertNotNull(result);
        assertTrue(result.isOneWay());
        assertNull(result.getInboundDurationIso());
        assertNull(result.getInboundDurationMinutes());
        assertEquals(0, result.getInboundSegments().size());
    }

    @Test
    void shouldHandleEmptySegments() {
        // Given
        FlightBoundDTO emptyOutbound = new FlightBoundDTO(
                "PT0H0M",
                0,
                List.of()
        );
        
        testFlightResponseDTO = new FlightSearchResponseDTO(
                true,
                emptyOutbound,
                null,
                testFlightResponseDTO.totalPrice(),
                testFlightResponseDTO.travelerPrices()
        );

        // When
        Flight result = flightMapper.mapToFlight(testFlightResponseDTO);

        // Then
        assertNotNull(result);
        assertEquals(0, result.getOutboundSegments().size());
    }

    @Test
    void shouldHandleEmptyTravelerPrices() {
        // Given
        testFlightResponseDTO = new FlightSearchResponseDTO(
                false,
                testFlightResponseDTO.outbound(),
                testFlightResponseDTO.inbound(),
                testFlightResponseDTO.totalPrice(),
                List.of()
        );

        // When
        Flight result = flightMapper.mapToFlight(testFlightResponseDTO);

        // Then
        assertNotNull(result);
        assertEquals(0, result.getTravelerPrices().size());
    }
}