package com.kodilla.flightapiservice.controller;

import com.kodilla.flightapiservice.domain.Flight;
import com.kodilla.flightapiservice.dto.FlightSearchRequestDTO;
import com.kodilla.flightapiservice.dto.FlightSearchResponseDTO;
import com.kodilla.flightapiservice.mapper.FlightMapper;
import com.kodilla.flightapiservice.service.FlightService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/flights")
public class FlightController {

    private final FlightService flightService;
    private final FlightMapper flightMapper;

    @PostMapping("/flight-offer")
    public ResponseEntity<List<FlightSearchResponseDTO>> getFlightOffers(@RequestBody FlightSearchRequestDTO flightOfferRequest) {
        List<FlightSearchResponseDTO> flightSearchResponseDTO = flightService.getFlightOffers(flightOfferRequest);
        return ResponseEntity.ok(flightSearchResponseDTO);
    }
    
    @PostMapping("/flight-offer/save")
    public ResponseEntity<List<FlightSearchResponseDTO>> searchAndSaveFlightOffers(@RequestBody FlightSearchRequestDTO flightOfferRequest) {
        List<Flight> savedFlights = flightService.searchAndSaveFlightOffers(flightOfferRequest);
        List<FlightSearchResponseDTO> flightSearchResponseDTO = savedFlights.stream()
                .map(flightMapper::mapToDto)
                .toList();
        return ResponseEntity.ok(flightSearchResponseDTO);
    }

    @GetMapping
    public ResponseEntity<List<FlightSearchResponseDTO>> getAllFlights() {
        List<Flight> flights = flightService.getAllFlights();
        List<FlightSearchResponseDTO> flightSearchResponseDTO = flights.stream()
                .map(flightMapper::mapToDto)
                .toList();
        return ResponseEntity.ok(flightSearchResponseDTO);
    }

    @GetMapping("/{id}")
    public ResponseEntity<FlightSearchResponseDTO> getFlightById(@PathVariable Long id) {
        return flightService.getFlightById(id)
                .map(flightMapper::mapToDto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFlight(@PathVariable Long id) {
        flightService.deleteFlight(id);
        return ResponseEntity.noContent().build();
    }
    
    @DeleteMapping("/cleanup")
    public ResponseEntity<Integer> cleanupOutdatedFlights() {
        int removedCount = flightService.removeOutdatedFlights();
        return ResponseEntity.ok(removedCount);
    }
}
