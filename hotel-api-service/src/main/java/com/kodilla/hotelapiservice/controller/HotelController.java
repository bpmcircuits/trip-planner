package com.kodilla.tripplannerhotelapi.controller;

import com.kodilla.tripplannerhotelapi.rapidapi.dto.BookingHotelsRequestDTO;
import com.kodilla.tripplannerhotelapi.rapidapi.dto.BookingHotelsResponseDTO;
import com.kodilla.tripplannerhotelapi.rapidapi.dto.BookingHotelsSearchResponseApiDTO;
import com.kodilla.tripplannerhotelapi.rapidapi.mapper.BookingHotelsMapper;
import com.kodilla.tripplannerhotelapi.service.BookingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/hotels")
public class HotelController {

    private final BookingService bookingService;
    private final BookingHotelsMapper bookingHotelsMapper;

    @PostMapping("/hotel-offer")
    public ResponseEntity<List<BookingHotelsResponseDTO>> searchHotels(@RequestBody BookingHotelsRequestDTO hotelSearchRequestDTO) {
        BookingHotelsSearchResponseApiDTO response = bookingService.searchHotels(hotelSearchRequestDTO);
        return ResponseEntity.ok(bookingHotelsMapper.mapToHotelInfoList(response));
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