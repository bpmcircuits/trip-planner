package com.kodilla.tripplanner.controller;

import com.kodilla.tripplanner.dto.HotelSearchRequestDTO;
import com.kodilla.tripplanner.rapidapi.bookinghotels.mapper.BookingHotelsMapper;
import com.kodilla.tripplanner.rapidapi.bookinghotels.dto.BookingHotelsResponseDTO;
import com.kodilla.tripplanner.rapidapi.bookinghotels.dto.BookingHotelsSearchResponseApiDTO;
import com.kodilla.tripplanner.service.BookingService;
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
    public ResponseEntity<List<BookingHotelsResponseDTO>> searchHotels(@RequestBody HotelSearchRequestDTO hotelSearchRequestDTO) {
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
