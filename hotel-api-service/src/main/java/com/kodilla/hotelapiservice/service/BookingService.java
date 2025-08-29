package com.kodilla.hotelapiservice.service;

import com.kodilla.hotelapiservice.domain.Hotel;
import com.kodilla.hotelapiservice.exception.BookingApiException;
import com.kodilla.hotelapiservice.exception.DatabaseOperationException;
import com.kodilla.hotelapiservice.exception.DestinationNotFoundException;
import com.kodilla.hotelapiservice.rapidapi.client.BookingHotelsClient;
import com.kodilla.hotelapiservice.rapidapi.dto.*;
import com.kodilla.hotelapiservice.rapidapi.mapper.BookingHotelsMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class BookingService {

    private final BookingHotelsClient bookingHotelsClient;
    private final BookingHotelsMapper bookingHotelsMapper;
    private final HotelService hotelService;

    public BookingHotelsSearchResponseApiDTO searchHotels(BookingHotelsRequestDTO request) 
            throws DestinationNotFoundException, BookingApiException {
        try {
            log.info("Searching for hotels with query: {}", request.query());
            BookingHotelsDestinationResponseApiDTO autoComplete = bookingHotelsClient.getAutoComplete(request.query());

            if (!autoComplete.message().equalsIgnoreCase("Successful") || autoComplete.data().isEmpty()) {
                log.error("No destination found for query: {}", request.query());
                throw new DestinationNotFoundException("No destination found for query: " + request.query());
            }

            BookingHotelsDestinationApiDTO dest = autoComplete.data().getFirst();

            return bookingHotelsClient.getHotelResults(dest, request);
        } catch (DestinationNotFoundException e) {
            throw e;
        } catch (Exception e) {
            log.error("Error searching for hotels", e);
            throw new BookingApiException("Error searching for hotels: " + e.getMessage(), e);
        }
    }

    @Transactional
    public List<Hotel> searchAndSaveHotels(BookingHotelsRequestDTO request) 
            throws DestinationNotFoundException, BookingApiException, DatabaseOperationException {
        log.info("Searching and saving hotels for query: {}", request.query());
        
        try {
            // Search for hotels
            BookingHotelsSearchResponseApiDTO apiResponse = searchHotels(request);
            
            // Map API response to DTOs
            List<BookingHotelsResponseDTO> hotelDtos = bookingHotelsMapper.mapToHotelInfoList(apiResponse);
            
            // Save to database
            return hotelService.saveHotelOffers(hotelDtos);
        } catch (DestinationNotFoundException | BookingApiException | DatabaseOperationException e) {
            // Re-throw these exceptions to be handled by the controller
            throw e;
        } catch (Exception e) {
            log.error("Unexpected error searching and saving hotels", e);
            throw new DatabaseOperationException("Unexpected error searching and saving hotels: " + e.getMessage(), e);
        }
    }
}