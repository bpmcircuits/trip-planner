package com.kodilla.tripplanner.mapper;

import com.kodilla.tripplanner.domain.Hotel;
import com.kodilla.tripplanner.dto.HotelDTO;
import org.springframework.stereotype.Service;

@Service
public class HotelMapper {

    public HotelDTO toHotelDTO(Hotel hotel) {
        if (hotel == null) {
            return null;
        }
        return new HotelDTO(
                hotel.getId(),
                hotel.getName(),
                hotel.getCountry(),
                hotel.getCity(),
                hotel.getCheckInDate(),
                hotel.getCheckOutDate(),
                hotel.getPrice()
        );
    }

    public Hotel toHotel(HotelDTO hotelDTO) {
        if (hotelDTO == null) {
            return null;
        }
        return Hotel.builder()
                .id(hotelDTO.id())
                .name(hotelDTO.name())
                .country(hotelDTO.country())
                .city(hotelDTO.city())
                .checkInDate(hotelDTO.checkInDate())
                .checkOutDate(hotelDTO.checkOutDate())
                .price(hotelDTO.price())
                .build();
    }
}
