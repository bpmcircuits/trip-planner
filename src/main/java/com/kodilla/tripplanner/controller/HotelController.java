package com.kodilla.tripplanner.controller;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/hotels")
public class HotelController {

    @GetMapping
    public String getAllHotelsForSearchedDates(@RequestBody String date) {
        return "List of all hotels for: " + date;
    }

    @GetMapping("/{id}")
    public String getHotelDetails(@PathVariable String id) {
        return "Details for hotel ID: " + id;
    }

    @PostMapping
    public String addHotel(@RequestBody String hotelDetails) {
        return "Hotel added with details: " + hotelDetails;
    }

    @PutMapping("/{id}")
    public String updateHotel(@PathVariable String id, @RequestBody String hotelDetails) {
        return "Hotel " + id + " updated with details: " + hotelDetails;
    }

    @DeleteMapping("/{id}")
    public String deleteHotel(@PathVariable String id) {
        return "Hotel deleted with ID: " + id;
    }
}
