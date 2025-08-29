package com.kodilla.flightapiservice.mapper;

import com.kodilla.flightapiservice.amadeus.client.AmadeusFlightsFlightOffersDataDTO;
import com.kodilla.flightapiservice.amadeus.dto.*;
import com.kodilla.flightapiservice.dto.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringJUnitConfig
class FlightOfferMapperTest {

    private FlightOfferMapper flightOfferMapper = new FlightOfferMapper();

    private AmadeusFlightsFlightOfferDTO testAmadeusFlightOffer;
    private AmadeusFlightsFlightOffersDataDTO testAmadeusFlightOffersData;
    private FlightSearchRequestDTO testFlightSearchRequest;

    @BeforeEach
    void setUp() {
        // Create test Amadeus flight end points
        AmadeusFlightsFlightEndPointDTO departureEndPoint = new AmadeusFlightsFlightEndPointDTO(
                "WAW",
                "2025-08-09T18:40:00Z",
                "2025-08-09T18:40:00Z"
        );

        AmadeusFlightsFlightEndPointDTO arrivalEndPoint = new AmadeusFlightsFlightEndPointDTO(
                "AYT",
                "2025-08-09T22:15:00Z",
                "2025-08-09T22:15:00Z"
        );

        AmadeusFlightsFlightEndPointDTO returnDepartureEndPoint = new AmadeusFlightsFlightEndPointDTO(
                "AYT",
                "2025-08-16T23:05:00Z",
                "2025-08-16T23:05:00Z"
        );

        AmadeusFlightsFlightEndPointDTO returnArrivalEndPoint = new AmadeusFlightsFlightEndPointDTO(
                "WAW",
                "2025-08-17T00:40:00Z",
                "2025-08-17T00:40:00Z"
        );

        // Create test Amadeus flight segments
        AmadeusFlightsSegmentsDTO outboundSegment = new AmadeusFlightsSegmentsDTO(
                departureEndPoint,
                arrivalEndPoint,
                "XQ"
        );

        AmadeusFlightsSegmentsDTO inboundSegment = new AmadeusFlightsSegmentsDTO(
                returnDepartureEndPoint,
                returnArrivalEndPoint,
                "XQ"
        );

        // Create test Amadeus itineraries
        AmadeusFlightsItinerariesDTO outboundItinerary = new AmadeusFlightsItinerariesDTO(
                "PT3H35M",
                List.of(outboundSegment)
        );

        AmadeusFlightsItinerariesDTO inboundItinerary = new AmadeusFlightsItinerariesDTO(
                "PT3H35M",
                List.of(inboundSegment)
        );

        // Create test Amadeus price
        AmadeusFlightsPriceDTO price = new AmadeusFlightsPriceDTO(
                "2400.00",
                "PLN"
        );

        // Create test Amadeus traveler pricings
        AmadeusFlightsTravelerPricingsDTO travelerPricing = new AmadeusFlightsTravelerPricingsDTO(
                "1",
                "STANDARD",
                "ADULT",
                price
        );

        // Create test Amadeus flight offer
        testAmadeusFlightOffer = new AmadeusFlightsFlightOfferDTO(
                false,
                List.of(outboundItinerary, inboundItinerary),
                price,
                List.of(travelerPricing)
        );

        // Create test Amadeus flight offers data
        testAmadeusFlightOffersData = new AmadeusFlightsFlightOffersDataDTO(List.of(testAmadeusFlightOffer));

        // Create test flight search request
        testFlightSearchRequest = new FlightSearchRequestDTO(
                "WAW",
                "AYT",
                "2025-08-09",
                "2025-08-16",
                2,
                0,
                0,
                "PLN"
        );
    }

    @Test
    void shouldMapFromAmadeusToFlightSearch() {
        // When
        FlightSearchResponseDTO result = flightOfferMapper.mapFromAmadeusToFlightSearch(testAmadeusFlightOffer);

        // Then
        assertNotNull(result);
        assertFalse(result.oneWay());

        // Check outbound
        assertNotNull(result.outbound());
        assertEquals("PT3H35M", result.outbound().durationIso());
        assertEquals(215, result.outbound().durationMinutes());
        assertEquals(1, result.outbound().segments().size());

        FlightSegmentDTO outboundSegment = result.outbound().segments().get(0);
        assertEquals("WAW", outboundSegment.fromIata());
        assertEquals("AYT", outboundSegment.toIata());
        assertEquals("XQ", outboundSegment.carrierCode());
        assertEquals("2025-08-09T18:40:00Z", outboundSegment.departureAt());
        assertEquals("2025-08-09T22:15:00Z", outboundSegment.arrivalAt());

        // Check inbound
        assertNotNull(result.inbound());
        assertEquals("PT3H35M", result.inbound().durationIso());
        assertEquals(215, result.inbound().durationMinutes());
        assertEquals(1, result.inbound().segments().size());

        FlightSegmentDTO inboundSegment = result.inbound().segments().get(0);
        assertEquals("AYT", inboundSegment.fromIata());
        assertEquals("WAW", inboundSegment.toIata());
        assertEquals("XQ", inboundSegment.carrierCode());
        assertEquals("2025-08-16T23:05:00Z", inboundSegment.departureAt());
        assertEquals("2025-08-17T00:40:00Z", inboundSegment.arrivalAt());

        // Check price
        assertEquals("PLN", result.totalPrice().currency());
        assertEquals("2400.00", result.totalPrice().total());

        // Check traveler prices
        assertEquals(1, result.travelerPrices().size());
        assertEquals("ADULT", result.travelerPrices().get(0).travelerType());
        assertEquals("PLN", result.travelerPrices().get(0).price().currency());
        assertEquals("2400.00", result.travelerPrices().get(0).price().total());
    }

    @Test
    void shouldMapAllFromAmadeus() {
        // When
        List<FlightSearchResponseDTO> results = flightOfferMapper.mapAllFromAmadeus(testAmadeusFlightOffersData);

        // Then
        assertNotNull(results);
        assertEquals(1, results.size());

        FlightSearchResponseDTO result = results.get(0);
        assertFalse(result.oneWay());
        assertEquals("PT3H35M", result.outbound().durationIso());
        assertEquals("PT3H35M", result.inbound().durationIso());
        assertEquals("PLN", result.totalPrice().currency());
        assertEquals("2400.00", result.totalPrice().total());
    }

    @Test
    void shouldHandleNullAmadeusFlightOffersData() {
        // When
        List<FlightSearchResponseDTO> results = flightOfferMapper.mapAllFromAmadeus(null);

        // Then
        assertNotNull(results);
        assertTrue(results.isEmpty());
    }

    @Test
    void shouldHandleEmptyAmadeusFlightOffersData() {
        // Given
        AmadeusFlightsFlightOffersDataDTO emptyData = new AmadeusFlightsFlightOffersDataDTO(List.of());

        // When
        List<FlightSearchResponseDTO> results = flightOfferMapper.mapAllFromAmadeus(emptyData);

        // Then
        assertNotNull(results);
        assertTrue(results.isEmpty());
    }

    @Test
    void shouldHandleOneWayFlight() {
        // Given
        AmadeusFlightsFlightOfferDTO oneWayOffer = new AmadeusFlightsFlightOfferDTO(
                true,
                List.of(testAmadeusFlightOffer.itineraries().get(0)),
                testAmadeusFlightOffer.price(),
                testAmadeusFlightOffer.travelerPricings()
        );

        // When
        FlightSearchResponseDTO result = flightOfferMapper.mapFromAmadeusToFlightSearch(oneWayOffer);

        // Then
        assertNotNull(result);
        assertTrue(result.oneWay());
        assertNotNull(result.outbound());
        assertNull(result.inbound());
    }

    @Test
    void shouldHandleNullItineraries() {
        // Given
        AmadeusFlightsFlightOfferDTO nullItinerariesOffer = new AmadeusFlightsFlightOfferDTO(
                false,
                null,
                testAmadeusFlightOffer.price(),
                testAmadeusFlightOffer.travelerPricings()
        );

        // When
        FlightSearchResponseDTO result = flightOfferMapper.mapFromAmadeusToFlightSearch(nullItinerariesOffer);

        // Then
        assertNotNull(result);
        assertTrue(result.oneWay());
        assertNull(result.outbound());
        assertNull(result.inbound());
    }

    @Test
    void shouldHandleNullPrice() {
        // Given
        AmadeusFlightsFlightOfferDTO nullPriceOffer = new AmadeusFlightsFlightOfferDTO(
                false,
                testAmadeusFlightOffer.itineraries(),
                null,
                testAmadeusFlightOffer.travelerPricings()
        );

        // When
        FlightSearchResponseDTO result = flightOfferMapper.mapFromAmadeusToFlightSearch(nullPriceOffer);

        // Then
        assertNotNull(result);
        assertNotNull(result.totalPrice());
        assertNull(result.totalPrice().currency());
        assertNull(result.totalPrice().total());
    }

    @Test
    void shouldHandleNullTravelerPricings() {
        // Given
        AmadeusFlightsFlightOfferDTO nullTravelerPricingsOffer = new AmadeusFlightsFlightOfferDTO(
                false,
                testAmadeusFlightOffer.itineraries(),
                testAmadeusFlightOffer.price(),
                null
        );

        // When
        FlightSearchResponseDTO result = flightOfferMapper.mapFromAmadeusToFlightSearch(nullTravelerPricingsOffer);

        // Then
        assertNotNull(result);
        assertNotNull(result.travelerPrices());
        assertTrue(result.travelerPrices().isEmpty());
    }

    @Test
    void shouldMapToAmadeusRequest() {
        // When
        AmadeusFlightsAirportSearchRequestDTO result = flightOfferMapper.mapToAmadeusRequest(testFlightSearchRequest);

        // Then
        assertNotNull(result);
        assertEquals("WAW", result.originLocationCode());
        assertEquals("AYT", result.destinationLocationCode());
        assertEquals("2025-08-09", result.departureDate());
        assertEquals("2025-08-16", result.returnDate());
        assertEquals(2, result.adults());
        assertEquals(0, result.children());
        assertEquals(0, result.infants());
        assertEquals("PLN", result.currencyCode());
    }
}