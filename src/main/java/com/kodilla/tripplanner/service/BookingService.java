package com.kodilla.tripplanner.service;

import com.kodilla.tripplanner.dto.HotelSearchRequestDTO;
import com.kodilla.tripplanner.rapidapi.bookinghotels.client.BookingHotelsClient;
import com.kodilla.tripplanner.rapidapi.bookinghotels.dto.BookingHotelsDestinationApiDTO;
import com.kodilla.tripplanner.rapidapi.bookinghotels.dto.BookingHotelsDestinationResponseApiDTO;
import com.kodilla.tripplanner.rapidapi.bookinghotels.dto.BookingHotelsSearchResponseApiDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BookingService {

    private final BookingHotelsClient bookingHotelsClient;

    public BookingHotelsSearchResponseApiDTO searchHotels(HotelSearchRequestDTO request) {
        BookingHotelsDestinationResponseApiDTO autoComplete = bookingHotelsClient.getAutoComplete(request.query());

        if (!autoComplete.message().equalsIgnoreCase("Successful") || autoComplete.data().isEmpty()) {
            throw new IllegalArgumentException("No destination found for query: " + request.query());
        }

        BookingHotelsDestinationApiDTO dest = autoComplete.data().getFirst();

        return bookingHotelsClient.getHotelResults(dest, request);
    }
}
