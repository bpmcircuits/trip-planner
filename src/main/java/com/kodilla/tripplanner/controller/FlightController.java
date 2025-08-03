package com.kodilla.tripplanner.controller;

import com.kodilla.tripplanner.dto.FlightRequestDTO;
import com.kodilla.tripplanner.rapidapi.kiwiflights.dto.KiwiSearchResponseOneWayDTO;
import com.kodilla.tripplanner.rapidapi.kiwiflights.dto.KiwiSearchResponseRoundTripDTO;
import com.kodilla.tripplanner.service.FlightService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/flights")
public class FlightController {

    private final FlightService flightService;

    @GetMapping("/one-way")
    public ResponseEntity<KiwiSearchResponseOneWayDTO> getAllFlightsOneWay(
            @RequestBody FlightRequestDTO requestBody) {
        return ResponseEntity.ok(flightService.getOneWayFlights(requestBody));
    }

    @GetMapping("/round-trip")
    public ResponseEntity<KiwiSearchResponseRoundTripDTO> getAllFlightsRoundTrip(
            @RequestBody FlightRequestDTO requestBody) {
        return ResponseEntity.ok(flightService.getRoundTripFlights(requestBody));
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
