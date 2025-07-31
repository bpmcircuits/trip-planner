package com.kodilla.tripplanner.controller;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/flights")
public class FlightController {

    @GetMapping
    public String getAllFlightsForSearchedDate(@RequestBody String date) {
        return "List of flights for the searched date: " + date;
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
