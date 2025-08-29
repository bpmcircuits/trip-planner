package com.kodilla.flightapiservice.service;

import com.kodilla.flightapiservice.amadeus.client.AmadeusFlightsClient;
import com.kodilla.flightapiservice.amadeus.client.AmadeusFlightsFlightOffersDataDTO;
import com.kodilla.flightapiservice.amadeus.dto.AmadeusFlightsAirportSearchDataDTO;
import com.kodilla.flightapiservice.amadeus.dto.AmadeusFlightsAirportSearchRequestDTO;
import com.kodilla.flightapiservice.amadeus.dto.AmadeusFlightsAirportSearchResponseDTO;
import com.kodilla.flightapiservice.domain.Flight;
import com.kodilla.flightapiservice.domain.FlightSegment;
import com.kodilla.flightapiservice.domain.TravelerPrice;
import com.kodilla.flightapiservice.dto.*;
import com.kodilla.flightapiservice.mapper.FlightIataCodeMapper;
import com.kodilla.flightapiservice.mapper.FlightMapper;
import com.kodilla.flightapiservice.mapper.FlightOfferMapper;
import com.kodilla.flightapiservice.repository.FlightRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FlightServiceTest {

    @Mock
    private AmadeusFlightsClient amadeusFlightsClient;

    @Mock
    private FlightIataCodeMapper flightIataCodeMapper;

    @Mock
    private FlightOfferMapper flightOfferMapper;

    @Mock
    private FlightMapper flightMapper;

    @Mock
    private FlightRepository flightRepository;

    @InjectMocks
    private FlightService flightService;

    private Flight testFlight;
    private FlightSearchResponseDTO testFlightResponseDTO;
    private FlightSearchRequestDTO testFlightRequestDTO;
    private FlightIataCodeDTO testDepartureIataCodeDTO;
    private FlightIataCodeDTO testArrivalIataCodeDTO;
    private AmadeusFlightsAirportSearchResponseDTO testDepartureAirportResponseDTO;
    private AmadeusFlightsAirportSearchResponseDTO testArrivalAirportResponseDTO;
    private AmadeusFlightsAirportSearchRequestDTO testAmadeusRequestDTO;
    private AmadeusFlightsFlightOffersDataDTO testAmadeusResponseDTO;
    private UUID testSearchId;

    @BeforeEach
    void setUp() {
        // Set cleanup days using ReflectionTestUtils
        ReflectionTestUtils.setField(flightService, "cleanupDays", 30);

        testSearchId = UUID.randomUUID();

        // Create test flight segments
        FlightSegment outboundSegment = FlightSegment.builder()
                .id(1L)
                .fromIata("WAW")
                .toIata("AYT")
                .carrierCode("XQ")
                .departureAt("2025-08-09T18:40:00Z")
                .arrivalAt("2025-08-09T22:15:00Z")
                .direction("outbound")
                .build();

        FlightSegment inboundSegment = FlightSegment.builder()
                .id(2L)
                .fromIata("AYT")
                .toIata("WAW")
                .carrierCode("XQ")
                .departureAt("2025-08-16T23:05:00Z")
                .arrivalAt("2025-08-17T00:40:00Z")
                .direction("inbound")
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
                .searchId(testSearchId)
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

        // Create test flight request DTO
        testFlightRequestDTO = new FlightSearchRequestDTO(
                "Warsaw",
                "Antalya",
                "2025-08-09",
                "2025-08-16",
                2,
                0,
                0,
                "PLN"
        );

        // Create test IATA code DTOs
        testDepartureIataCodeDTO = new FlightIataCodeDTO("WAW");
        testArrivalIataCodeDTO = new FlightIataCodeDTO("AYT");

        // Create test airport search response DTOs
        AmadeusFlightsAirportSearchDataDTO departureData = new AmadeusFlightsAirportSearchDataDTO("WAW");
        AmadeusFlightsAirportSearchDataDTO arrivalData = new AmadeusFlightsAirportSearchDataDTO("AYT");
        testDepartureAirportResponseDTO = new AmadeusFlightsAirportSearchResponseDTO(List.of(departureData));
        testArrivalAirportResponseDTO = new AmadeusFlightsAirportSearchResponseDTO(List.of(arrivalData));

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

        // Create test Amadeus request and response DTOs
        testAmadeusRequestDTO = new AmadeusFlightsAirportSearchRequestDTO(
                "WAW",
                "AYT",
                "2025-08-09",
                "2025-08-16",
                2,
                0,
                0,
                "PLN"
        );

        testAmadeusResponseDTO = mock(AmadeusFlightsFlightOffersDataDTO.class);
    }

    @Test
    void shouldGetFlightOffers() {
        // Given
        when(amadeusFlightsClient.getAirportCode("Warsaw")).thenReturn(testDepartureAirportResponseDTO);
        when(amadeusFlightsClient.getAirportCode("Antalya")).thenReturn(testArrivalAirportResponseDTO);
        when(flightIataCodeMapper.toDto(testDepartureAirportResponseDTO)).thenReturn(testDepartureIataCodeDTO);
        when(flightIataCodeMapper.toDto(testArrivalAirportResponseDTO)).thenReturn(testArrivalIataCodeDTO);
        when(flightOfferMapper.mapToAmadeusRequest(any(FlightSearchRequestDTO.class))).thenReturn(testAmadeusRequestDTO);
        when(amadeusFlightsClient.getFlightOffer(testAmadeusRequestDTO)).thenReturn(testAmadeusResponseDTO);
        when(flightOfferMapper.mapAllFromAmadeus(testAmadeusResponseDTO)).thenReturn(List.of(testFlightResponseDTO));

        // When
        List<FlightSearchResponseDTO> result = flightService.getFlightOffers(testFlightRequestDTO);

        // Then
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(testFlightResponseDTO, result.get(0));
        verify(amadeusFlightsClient, times(1)).getAirportCode("Warsaw");
        verify(amadeusFlightsClient, times(1)).getAirportCode("Antalya");
        verify(flightOfferMapper, times(1)).mapToAmadeusRequest(any(FlightSearchRequestDTO.class));
        verify(amadeusFlightsClient, times(1)).getFlightOffer(testAmadeusRequestDTO);
        verify(flightOfferMapper, times(1)).mapAllFromAmadeus(testAmadeusResponseDTO);
    }

    @Test
    void shouldSearchAndSaveFlightOffers() {
        // Given
        when(amadeusFlightsClient.getAirportCode("Warsaw")).thenReturn(testDepartureAirportResponseDTO);
        when(amadeusFlightsClient.getAirportCode("Antalya")).thenReturn(testArrivalAirportResponseDTO);
        when(flightIataCodeMapper.toDto(testDepartureAirportResponseDTO)).thenReturn(testDepartureIataCodeDTO);
        when(flightIataCodeMapper.toDto(testArrivalAirportResponseDTO)).thenReturn(testArrivalIataCodeDTO);
        when(flightOfferMapper.mapToAmadeusRequest(any(FlightSearchRequestDTO.class))).thenReturn(testAmadeusRequestDTO);
        when(amadeusFlightsClient.getFlightOffer(testAmadeusRequestDTO)).thenReturn(testAmadeusResponseDTO);
        when(flightOfferMapper.mapAllFromAmadeus(testAmadeusResponseDTO)).thenReturn(List.of(testFlightResponseDTO));
        when(flightMapper.mapToFlight(testFlightResponseDTO)).thenReturn(testFlight);
        when(flightRepository.findBySearchId(testSearchId)).thenReturn(Optional.empty());
        when(flightRepository.save(testFlight)).thenReturn(testFlight);

        // When
        List<Flight> result = flightService.searchAndSaveFlightOffers(testFlightRequestDTO);

        // Then
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(testFlight, result.get(0));
        verify(flightMapper, times(1)).mapToFlight(testFlightResponseDTO);
        verify(flightRepository, times(1)).findBySearchId(testSearchId);
        verify(flightRepository, times(1)).save(testFlight);
    }

    @Test
    void shouldSaveNewFlight() {
        // Given
        when(flightMapper.mapToFlight(testFlightResponseDTO)).thenReturn(testFlight);
        when(flightRepository.findBySearchId(testSearchId)).thenReturn(Optional.empty());
        when(flightRepository.save(testFlight)).thenReturn(testFlight);

        // When
        Flight result = flightService.saveOrUpdateFlight(testFlightResponseDTO);

        // Then
        assertNotNull(result);
        assertEquals(testFlight, result);
        verify(flightMapper, times(1)).mapToFlight(testFlightResponseDTO);
        verify(flightRepository, times(1)).findBySearchId(testSearchId);
        verify(flightRepository, times(1)).save(testFlight);
    }

    @Test
    void shouldUpdateExistingFlight() {
        // Given
        Flight existingFlight = Flight.builder()
                .id(1L)
                .oneWay(true) // Different from testFlight
                .searchId(testSearchId)
                .currency("USD") // Different from testFlight
                .totalPrice("3000.00") // Different from testFlight
                .outboundDurationIso("PT4H00M") // Different from testFlight
                .outboundDurationMinutes(240) // Different from testFlight
                .inboundDurationIso("PT4H00M") // Different from testFlight
                .inboundDurationMinutes(240) // Different from testFlight
                .lastUpdated(LocalDateTime.now().minusDays(1))
                .outboundSegments(new ArrayList<>())
                .inboundSegments(new ArrayList<>())
                .travelerPrices(new ArrayList<>())
                .build();

        when(flightMapper.mapToFlight(testFlightResponseDTO)).thenReturn(testFlight);
        when(flightRepository.findBySearchId(testSearchId)).thenReturn(Optional.of(existingFlight));
        when(flightRepository.save(existingFlight)).thenReturn(existingFlight);

        // When
        Flight result = flightService.saveOrUpdateFlight(testFlightResponseDTO);

        // Then
        assertNotNull(result);
        assertEquals(existingFlight, result);
        assertEquals(false, result.isOneWay());
        assertEquals("PLN", result.getCurrency());
        assertEquals("2400.00", result.getTotalPrice());
        assertEquals("PT3H35M", result.getOutboundDurationIso());
        assertEquals(215, result.getOutboundDurationMinutes());
        assertEquals("PT3H35M", result.getInboundDurationIso());
        assertEquals(215, result.getInboundDurationMinutes());
        verify(flightMapper, times(1)).mapToFlight(testFlightResponseDTO);
        verify(flightRepository, times(1)).findBySearchId(testSearchId);
        verify(flightRepository, times(1)).save(existingFlight);
    }

    @Test
    void shouldRemoveOutdatedFlights() {
        // Given
        LocalDateTime cutoffDate = LocalDateTime.now().minusDays(30);
        List<Flight> outdatedFlights = List.of(testFlight);
        when(flightRepository.findByLastUpdatedBefore(any(LocalDateTime.class))).thenReturn(outdatedFlights);
        doNothing().when(flightRepository).deleteAll(outdatedFlights);

        // When
        int result = flightService.removeOutdatedFlights();

        // Then
        assertEquals(1, result);
        verify(flightRepository, times(1)).findByLastUpdatedBefore(any(LocalDateTime.class));
        verify(flightRepository, times(1)).deleteAll(outdatedFlights);
    }

    @Test
    void shouldNotRemoveAnyFlightsWhenNoOutdatedFlights() {
        // Given
        LocalDateTime cutoffDate = LocalDateTime.now().minusDays(30);
        when(flightRepository.findByLastUpdatedBefore(any(LocalDateTime.class))).thenReturn(List.of());

        // When
        int result = flightService.removeOutdatedFlights();

        // Then
        assertEquals(0, result);
        verify(flightRepository, times(1)).findByLastUpdatedBefore(any(LocalDateTime.class));
        verify(flightRepository, never()).deleteAll(anyList());
    }

    @Test
    void shouldGetAllFlights() {
        // Given
        when(flightRepository.findAll()).thenReturn(List.of(testFlight));

        // When
        List<Flight> result = flightService.getAllFlights();

        // Then
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(testFlight, result.get(0));
        verify(flightRepository, times(1)).findAll();
    }

    @Test
    void shouldGetFlightById() {
        // Given
        when(flightRepository.findById(1L)).thenReturn(Optional.of(testFlight));

        // When
        Optional<Flight> result = flightService.getFlightById(1L);

        // Then
        assertTrue(result.isPresent());
        assertEquals(testFlight, result.get());
        verify(flightRepository, times(1)).findById(1L);
    }

    @Test
    void shouldReturnEmptyOptionalWhenFlightNotFound() {
        // Given
        when(flightRepository.findById(999L)).thenReturn(Optional.empty());

        // When
        Optional<Flight> result = flightService.getFlightById(999L);

        // Then
        assertFalse(result.isPresent());
        verify(flightRepository, times(1)).findById(999L);
    }

    @Test
    void shouldDeleteFlight() {
        // Given
        doNothing().when(flightRepository).deleteById(1L);

        // When
        flightService.deleteFlight(1L);

        // Then
        verify(flightRepository, times(1)).deleteById(1L);
    }
}