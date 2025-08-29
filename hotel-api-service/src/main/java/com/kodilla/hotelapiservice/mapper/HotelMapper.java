package com.kodilla.hotelapiservice.mapper;

import com.kodilla.hotelapiservice.domain.Hotel;
import com.kodilla.hotelapiservice.rapidapi.dto.BookingHotelsResponseDTO;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.StreamSupport;

@Component
public class HotelMapper {

    /**
     * Maps a BookingHotelsResponseDTO to a Hotel entity
     * @param dto The DTO to map
     * @return A Hotel entity
     */
    public Hotel mapToHotel(BookingHotelsResponseDTO dto) {
        return Hotel.builder()
                .name(dto.name())
                .countryCode(dto.countryCode())
                .city(dto.city())
                .price(dto.price())
                .currency(dto.currency())
                .checkInDate(dto.checkInDate())
                .checkOutDate(dto.checkOutDate())
                .reviewScore(dto.reviewScore())
                .reviewScoreWord(dto.reviewScoreWord())
                .reviewCount(dto.reviewCount())
                .lastUpdated(LocalDateTime.now())
                .build();
    }

    /**
     * Maps a list of BookingHotelsResponseDTO to a list of Hotel entities
     * @param dtos The DTOs to map
     * @return A list of Hotel entities
     */
    public List<Hotel> mapToHotelList(List<BookingHotelsResponseDTO> dtos) {
        return dtos.stream()
                .map(this::mapToHotel)
                .toList();
    }

    /**
     * Maps a Hotel entity to a BookingHotelsResponseDTO
     * @param hotel The Hotel entity to map
     * @return A BookingHotelsResponseDTO
     */
    public BookingHotelsResponseDTO mapToDto(Hotel hotel) {
        return new BookingHotelsResponseDTO(
                hotel.getName(),
                hotel.getCountryCode(),
                hotel.getCity(),
                hotel.getPrice(),
                hotel.getCurrency(),
                hotel.getCheckInDate(),
                hotel.getCheckOutDate(),
                hotel.getReviewScore(),
                hotel.getReviewScoreWord(),
                hotel.getReviewCount()
        );
    }

    /**
     * Maps a list of Hotel entities to a list of BookingHotelsResponseDTO
     * @param hotels The Hotel entities to map
     * @return A list of BookingHotelsResponseDTO
     */
    public List<BookingHotelsResponseDTO> mapToDtoList(Iterable<Hotel> hotels) {
        return StreamSupport.stream(hotels.spliterator(), false)
                .map(this::mapToDto)
                .toList();
    }
}