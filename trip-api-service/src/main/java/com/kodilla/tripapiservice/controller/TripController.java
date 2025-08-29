package com.kodilla.tripapiservice.controller;

import com.kodilla.tripapiservice.domain.Trip;
import com.kodilla.tripapiservice.dto.TripDTO;
import com.kodilla.tripapiservice.exception.FlightNotFoundException;
import com.kodilla.tripapiservice.exception.HotelNotFoundException;
import com.kodilla.tripapiservice.exception.TripNotFoundException;
import com.kodilla.tripapiservice.mapper.TripMapper;
import com.kodilla.tripapiservice.service.TripService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/trips")
@RequiredArgsConstructor
public class TripController {

    private final TripService tripService;
    private final TripMapper tripMapper;

    @GetMapping
    public ResponseEntity<List<TripDTO>> getAllTrips() {
        List<TripDTO> trips = tripMapper.toTripDTOList(tripService.findAll());
        return ResponseEntity.ok(trips);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TripDTO> getTripById(@PathVariable Long id) throws TripNotFoundException {
        Trip trip = tripService.findById(id);
        TripDTO tripDTO = tripMapper.toTripDTO(trip);
        return ResponseEntity.ok(tripDTO);
    }

    @PostMapping
    public ResponseEntity<TripDTO> createTrip(@RequestBody TripDTO tripDTO) {
        Trip trip = tripMapper.toTrip(tripDTO, null);
        Trip savedTrip = tripService.save(trip);
        return ResponseEntity.ok(tripMapper.toTripDTO(savedTrip));
    }

    @PutMapping("/{id}/flight")
    public ResponseEntity<TripDTO> updateTripFlight(@PathVariable Long id, @RequestBody Long flightId) 
            throws TripNotFoundException, FlightNotFoundException {
        Trip updatedTrip = tripService.updateTripFlight(id, flightId);
        return ResponseEntity.ok(tripMapper.toTripDTO(updatedTrip));
    }

    @PutMapping("/{id}/hotel")
    public ResponseEntity<TripDTO> updateTripHotel(@PathVariable Long id, @RequestBody Long hotelId) 
            throws TripNotFoundException, HotelNotFoundException {
        Trip updatedTrip = tripService.updateTripHotel(id, hotelId);
        return ResponseEntity.ok(tripMapper.toTripDTO(updatedTrip));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTrip(@PathVariable Long id) throws TripNotFoundException {
        tripService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

}
