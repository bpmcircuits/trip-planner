package com.kodilla.tripplanner.controller;

import com.kodilla.tripplanner.domain.Traveler;
import com.kodilla.tripplanner.dto.TravelerDTO;
import com.kodilla.tripplanner.mapper.TravelerMapper;
import com.kodilla.tripplanner.service.TripService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/trips")
@RequiredArgsConstructor
public class TripController {

    private final TripService tripService;
    private final TravelerMapper travelerMapper;

    @GetMapping
    public String getAllTrips() {
        return "List of all trips";
    }

    @GetMapping("/{id}")
    public String getTripById(@PathVariable String id) {
        return "Details for trip ID: " + id;
    }

    @PostMapping
    public String createTrip(String tripDetails) {
        return "Trip created with details: " + tripDetails;
    }

    @PutMapping("/{id}/flight")
    public String updateTripFlight(@PathVariable String id, @RequestBody String flightDetails) {
        return "Trip " + id + " flight updated with details: " + flightDetails;
    }

    @PutMapping("/{id}/hotel")
    public String updateTripHotel(@PathVariable String id, @RequestBody String hotelDetails) {
        return "Trip " + id + " hotel updated with details: " + hotelDetails;
    }

    @GetMapping("/travelers")
    public ResponseEntity<List<TravelerDTO>> getAllTravelers() {
        List<Traveler> travelers = tripService.getAllTravelers();
        return ResponseEntity.ok(travelerMapper.toTravelerDTOList(travelers));
    }

    @PostMapping("/travelers/add")
    public ResponseEntity<TravelerDTO> addTraveler(@RequestBody TravelerDTO travelerDTO) {
        Traveler traveler = travelerMapper.toTraveler(travelerDTO);
        Traveler addedTraveler = tripService.addTraveler(traveler);
        return ResponseEntity.ok(travelerMapper.toTravelerDTO(addedTraveler));
    }

    @DeleteMapping("/travelers/{travelerId}")
    public ResponseEntity<Void> removeTraveler(@PathVariable Long travelerId) {
        tripService.removeTraveler(travelerId);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{id}/book")
    public String bookTrip(@PathVariable String id) {
        return "Trip booked with id: " + id;
    }

    @DeleteMapping("/{id}")
    public String deleteTrip(@PathVariable String id) {
        return "Trip deleted with ID: " + id;
    }

}
