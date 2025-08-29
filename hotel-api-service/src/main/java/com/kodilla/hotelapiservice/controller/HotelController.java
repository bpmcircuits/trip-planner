package com.kodilla.hotelapiservice.controller;

import com.kodilla.hotelapiservice.domain.Hotel;
import com.kodilla.hotelapiservice.exception.BookingApiException;
import com.kodilla.hotelapiservice.exception.DatabaseOperationException;
import com.kodilla.hotelapiservice.exception.DestinationNotFoundException;
import com.kodilla.hotelapiservice.exception.HotelNotFoundException;
import com.kodilla.hotelapiservice.mapper.HotelMapper;
import com.kodilla.hotelapiservice.rapidapi.dto.BookingHotelsRequestDTO;
import com.kodilla.hotelapiservice.rapidapi.dto.BookingHotelsResponseDTO;
import com.kodilla.hotelapiservice.rapidapi.dto.BookingHotelsSearchResponseApiDTO;
import com.kodilla.hotelapiservice.rapidapi.mapper.BookingHotelsMapper;
import com.kodilla.hotelapiservice.service.BookingService;
import com.kodilla.hotelapiservice.service.HotelService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/hotels")
@Slf4j
public class HotelController {

    private final BookingService bookingService;
    private final HotelService hotelService;
    private final BookingHotelsMapper bookingHotelsMapper;
    private final HotelMapper hotelMapper;

    @PostMapping("/hotel-offer")
    public ResponseEntity<List<BookingHotelsResponseDTO>> searchHotels(@RequestBody BookingHotelsRequestDTO hotelSearchRequestDTO) 
            throws DestinationNotFoundException, BookingApiException {
        log.info("Searching for hotels with query: {}", hotelSearchRequestDTO.query());
        BookingHotelsSearchResponseApiDTO response = bookingService.searchHotels(hotelSearchRequestDTO);
        return ResponseEntity.ok(bookingHotelsMapper.mapToHotelInfoList(response));
    }

    @PostMapping("/hotel-offer/save")
    public ResponseEntity<List<BookingHotelsResponseDTO>> searchAndSaveHotels(@RequestBody BookingHotelsRequestDTO hotelSearchRequestDTO) 
            throws DestinationNotFoundException, BookingApiException, DatabaseOperationException {
        log.info("Searching and saving hotels with query: {}", hotelSearchRequestDTO.query());
        List<Hotel> savedHotels = bookingService.searchAndSaveHotels(hotelSearchRequestDTO);
        return ResponseEntity.ok(hotelMapper.mapToDtoList(savedHotels));
    }

    @GetMapping
    public ResponseEntity<List<BookingHotelsResponseDTO>> getAllHotels() 
            throws DatabaseOperationException {
        log.info("Getting all hotels");
        List<Hotel> hotels = hotelService.getAllHotels();
        return ResponseEntity.ok(hotelMapper.mapToDtoList(hotels));
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookingHotelsResponseDTO> getHotelDetails(@PathVariable Long id) 
            throws HotelNotFoundException, DatabaseOperationException {
        log.info("Getting hotel details for ID: {}", id);
        Hotel hotel = hotelService.getHotelById(id);
        return ResponseEntity.ok(hotelMapper.mapToDto(hotel));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteHotel(@PathVariable Long id) 
            throws HotelNotFoundException, DatabaseOperationException {
        log.info("Deleting hotel with ID: {}", id);
        hotelService.deleteHotel(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/outdated")
    public ResponseEntity<String> removeOutdatedOffers() 
            throws DatabaseOperationException {
        log.info("Manually triggering cleanup of outdated offers");
        int count = hotelService.removeOutdatedOffers();
        return ResponseEntity.ok("Removed " + count + " outdated hotel offers");
    }
}