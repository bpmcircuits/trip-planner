package com.bpm.frontend.dto;

public record HotelSearchRequestDTO(String query,
                                    String checkinDate,
                                    String checkoutDate,
                                    int adultsNumber) {
}
