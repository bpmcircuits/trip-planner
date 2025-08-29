package com.bpm.frontend.service;

import com.bpm.frontend.dto.HotelSearchRequestDTO;
import com.bpm.frontend.tripplanerback.client.BackendClient;
import com.bpm.frontend.tripplanerback.dto.hotels.BookingHotelsResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class HotelOfferService {

    private final BackendClient backendClient;

    public List<BookingHotelsResponseDTO> getHotelOffers(HotelSearchRequestDTO hotelSearchRequestDTO) {
        return backendClient.getHotelOffers(hotelSearchRequestDTO);
    }
}
