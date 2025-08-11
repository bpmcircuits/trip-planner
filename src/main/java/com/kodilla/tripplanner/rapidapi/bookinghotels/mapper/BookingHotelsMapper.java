package com.kodilla.tripplanner.rapidapi.bookinghotels.mapper;

import com.kodilla.tripplanner.rapidapi.bookinghotels.dto.BookingHotelsApiDTO;
import com.kodilla.tripplanner.rapidapi.bookinghotels.dto.BookingHotelsResponseDTO;
import com.kodilla.tripplanner.rapidapi.bookinghotels.dto.BookingHotelsSearchResponseApiDTO;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

@Component
public class BookingHotelsMapper {

    public List<BookingHotelsResponseDTO> mapToHotelInfoList(BookingHotelsSearchResponseApiDTO response) {
        return response.data().stream()
                .map(this::mapToHotelInfo)
                .toList();
    }

    public BookingHotelsResponseDTO mapToHotelInfo(BookingHotelsApiDTO response) {
        return new BookingHotelsResponseDTO(
                response.name(),
                response.countryCode(),
                response.wishlistName(),
                BigDecimal.valueOf(response.priceBreakdown().grossPrice().value()),
                response.currency(),
                response.checkinDate() != null ? java.time.LocalDate.parse(response.checkinDate()) : null,
                response.checkoutDate() != null ? java.time.LocalDate.parse(response.checkoutDate()) : null,
                response.reviewScore(),
                response.reviewScoreWord(),
                response.reviewCount()
        );
    }
}