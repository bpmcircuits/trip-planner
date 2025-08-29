package com.bpm.frontend.dto;

public record HotelOrderDTO(
        String hotelName,
        String city,
        String country,
        String checkInDate,
        String checkOutDate,
        int numberOfGuests,
        int numberOfRooms,
        String roomType,
        double totalPrice
) {
}
