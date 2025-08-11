package com.kodilla.tripplanner.dto;

import com.kodilla.tripplanner.rapidapi.bookinghotels.dto.BookingHotelsResponseDTO;

import java.util.List;

public record UserSearchResponseDTO(
        List<BookingHotelsResponseDTO> hotels,
        List<String> flights) {
}
