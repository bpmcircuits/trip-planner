package com.kodilla.tripplanner.mapper;

import com.kodilla.tripplanner.domain.Hotel;
import com.kodilla.tripplanner.dto.hotels.HotelDTO;
import org.springframework.stereotype.Service;

@Service
public class HotelMapper {

    public HotelDTO toHotelDTO(Hotel hotel) {
        if (hotel == null) {
            return null;
        }
        return new HotelDTO(
                hotel.getName(),
                hotel.getCountry(), // Using country as countryCode
                hotel.getCity(),
                hotel.getPrice(),
                "USD", // Default currency
                hotel.getCheckInDate(),
                hotel.getCheckOutDate(),
                4.5, // Default review score
                "Very Good", // Default review score word
                100 // Default review count
        );
    }

    public Hotel toHotel(HotelDTO hotelDTO) {
        if (hotelDTO == null) {
            return null;
        }
        return Hotel.builder()
                .id(null) // ID will be generated
                .name(hotelDTO.name())
                .country(hotelDTO.countryCode()) // Using countryCode as country
                .city(hotelDTO.city())
                .checkInDate(hotelDTO.checkInDate())
                .checkOutDate(hotelDTO.checkOutDate())
                .price(hotelDTO.price())
                .build();
    }
}
