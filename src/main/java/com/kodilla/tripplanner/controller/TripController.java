package com.kodilla.tripplanner.controller;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/trips")
public class TripController {

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

    @PostMapping("/{id}/travelers")
    public String addTravelerToTrip(@PathVariable String id, @RequestBody String travelerDetails) {
        return "Traveler added to trip " + id + " with details: " + travelerDetails;
    }

    @DeleteMapping("/{id}/travelers/{travelerId}")
    public String removeTravelerFromTrip(@PathVariable String id, @PathVariable String travelerId) {
        return "Traveler " + travelerId + " removed from trip " + id;
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
