package com.kodilla.tripplannerhotelapi.service;

import com.kodilla.tripplannerhotelapi.rapidapi.client.BookingHotelsClient;
import com.kodilla.tripplannerhotelapi.rapidapi.dto.BookingHotelsDestinationApiDTO;
import com.kodilla.tripplannerhotelapi.rapidapi.dto.BookingHotelsDestinationResponseApiDTO;
import com.kodilla.tripplannerhotelapi.rapidapi.dto.BookingHotelsRequestDTO;
import com.kodilla.tripplannerhotelapi.rapidapi.dto.BookingHotelsSearchResponseApiDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BookingService {

    private final BookingHotelsClient bookingHotelsClient;

    public BookingHotelsSearchResponseApiDTO searchHotels(BookingHotelsRequestDTO request) {
        BookingHotelsDestinationResponseApiDTO autoComplete = bookingHotelsClient.getAutoComplete(request.query());

        if (!autoComplete.message().equalsIgnoreCase("Successful") || autoComplete.data().isEmpty()) {
            throw new IllegalArgumentException("No destination found for query: " + request.query());
        }

        BookingHotelsDestinationApiDTO dest = autoComplete.data().getFirst();

        return bookingHotelsClient.getHotelResults(dest, request);
    }
}