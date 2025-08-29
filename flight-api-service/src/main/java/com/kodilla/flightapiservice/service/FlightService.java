package com.kodilla.flightapiservice.service;

import com.kodilla.flightapiservice.amadeus.client.AmadeusFlightsClient;
import com.kodilla.flightapiservice.amadeus.client.AmadeusFlightsFlightOffersDataDTO;
import com.kodilla.flightapiservice.amadeus.dto.AmadeusFlightsAirportSearchRequestDTO;
import com.kodilla.flightapiservice.domain.Flight;
import com.kodilla.flightapiservice.dto.FlightIataCodeDTO;
import com.kodilla.flightapiservice.dto.FlightSearchRequestDTO;
import com.kodilla.flightapiservice.dto.FlightSearchResponseDTO;
import com.kodilla.flightapiservice.mapper.FlightIataCodeMapper;
import com.kodilla.flightapiservice.mapper.FlightMapper;
import com.kodilla.flightapiservice.mapper.FlightOfferMapper;
import com.kodilla.flightapiservice.repository.FlightRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class FlightService {

    private final AmadeusFlightsClient amadeusFlightsClient;
    private final FlightIataCodeMapper flightIataCodeMapper;
    private final FlightOfferMapper flightOfferMapper;
    private final FlightMapper flightMapper;
    private final FlightRepository flightRepository;
    
    @Value("${flight.cleanup.days:30}")
    private int cleanupDays;

    public List<FlightSearchResponseDTO> getFlightOffers(FlightSearchRequestDTO flightSearchRequest) {
        FlightSearchRequestDTO requestWithIataCodes = new FlightSearchRequestDTO(
                getIataCode(flightSearchRequest.departureCity()).iataCode(),
                getIataCode(flightSearchRequest.arrivalCity()).iataCode(),
                flightSearchRequest.departureDate(),
                flightSearchRequest.returnDate(),
                flightSearchRequest.adults(),
                flightSearchRequest.children(),
                flightSearchRequest.infants(),
                flightSearchRequest.currencyCode()
        );

        AmadeusFlightsAirportSearchRequestDTO requestDTO = flightOfferMapper.mapToAmadeusRequest(requestWithIataCodes);
        AmadeusFlightsFlightOffersDataDTO flightsOfferData = amadeusFlightsClient.getFlightOffer(requestDTO);
        return flightOfferMapper.mapAllFromAmadeus(flightsOfferData);
    }
    
    @Transactional
    public List<Flight> searchAndSaveFlightOffers(FlightSearchRequestDTO flightSearchRequest) {
        List<FlightSearchResponseDTO> flightOffers = getFlightOffers(flightSearchRequest);
        log.info("Saving {} flight offers", flightOffers.size());
        
        return flightOffers.stream()
                .map(this::saveOrUpdateFlight)
                .collect(Collectors.toList());
    }
    
    @Transactional
    public Flight saveOrUpdateFlight(FlightSearchResponseDTO flightDto) {
        Flight flight = flightMapper.mapToFlight(flightDto);
        UUID searchId = flight.getSearchId();
        
        Optional<Flight> existingFlight = flightRepository.findBySearchId(searchId);
        
        if (existingFlight.isPresent()) {
            log.info("Flight with searchId {} already exists, updating", searchId);
            Flight existing = existingFlight.get();
            
            // Update the existing flight with new data
            existing.setOneWay(flight.isOneWay());
            existing.setCurrency(flight.getCurrency());
            existing.setTotalPrice(flight.getTotalPrice());
            existing.setOutboundDurationIso(flight.getOutboundDurationIso());
            existing.setOutboundDurationMinutes(flight.getOutboundDurationMinutes());
            existing.setInboundDurationIso(flight.getInboundDurationIso());
            existing.setInboundDurationMinutes(flight.getInboundDurationMinutes());
            existing.setLastUpdated(LocalDateTime.now());
            
            // Clear existing segments and prices
            existing.getOutboundSegments().clear();
            existing.getInboundSegments().clear();
            existing.getTravelerPrices().clear();
            
            // Add new segments and prices
            flight.getOutboundSegments().forEach(existing::addOutboundSegment);
            flight.getInboundSegments().forEach(existing::addInboundSegment);
            flight.getTravelerPrices().forEach(existing::addTravelerPrice);
            
            return flightRepository.save(existing);
        } else {
            log.info("Creating new flight with searchId {}", searchId);
            return flightRepository.save(flight);
        }
    }
    
    @Transactional
    public int removeOutdatedFlights() {
        LocalDateTime cutoffDate = LocalDateTime.now().minusDays(cleanupDays);
        log.info("Removing flight offers not updated since {}", cutoffDate);
        
        List<Flight> outdatedFlights = flightRepository.findByLastUpdatedBefore(cutoffDate);
        int count = outdatedFlights.size();
        
        if (!outdatedFlights.isEmpty()) {
            flightRepository.deleteAll(outdatedFlights);
            log.info("Removed {} outdated flight offers", count);
        } else {
            log.info("No outdated flight offers found");
        }
        
        return count;
    }
    
    public List<Flight> getAllFlights() {
        return flightRepository.findAll();
    }
    
    public Optional<Flight> getFlightById(Long id) {
        return flightRepository.findById(id);
    }
    
    @Transactional
    public void deleteFlight(Long id) {
        flightRepository.deleteById(id);
    }

    private FlightIataCodeDTO getIataCode(String city) {
        return flightIataCodeMapper.toDto(amadeusFlightsClient.getAirportCode(city));
    }
}