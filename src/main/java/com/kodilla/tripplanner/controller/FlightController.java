package com.kodilla.tripplanner.controller;

import com.kodilla.tripplanner.dto.flights.FlightSearchRequestDTO;
import com.kodilla.tripplanner.dto.flights.FlightSearchResponseDTO;
import com.kodilla.tripplanner.service.FlightService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/flights")
public class FlightController {

    private final FlightService flightService;

    @PostMapping("/flight-offer")
    public ResponseEntity<List<FlightSearchResponseDTO>> getFlightOffers(@RequestBody FlightSearchRequestDTO flightOfferRequest) {
        List<FlightSearchResponseDTO> flightSearchResponseDTO = flightService.getFlightOffers(flightOfferRequest);
        return ResponseEntity.ok(flightSearchResponseDTO);
    }

    @GetMapping("/{id}")
    public String getFlightDetails(@PathVariable String id) {
        return "Details for flight ID: " + id;
    }

    @PostMapping
    public String addFlight(@RequestBody String flightId) {
        return "Flight added with ID: " + flightId;
    }

    @PutMapping("/{id}")
    public String updateFlight(@PathVariable String id, @RequestBody String flightDetails) {
        return "Flight with id: " + id + " updated with details: " + flightDetails;
    }

    @DeleteMapping("/{id}")
    public String deleteFlight(@PathVariable String id) {
        return "Flight deleted with ID: " + id;
    }
}