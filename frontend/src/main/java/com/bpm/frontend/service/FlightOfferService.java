package com.bpm.frontend.service;

import com.bpm.frontend.dto.SearchRequestDTO;
import com.bpm.frontend.tripplanerback.client.BackendClient;
import com.bpm.frontend.tripplanerback.dto.flights.FlightSearchResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FlightOfferService {

    private final BackendClient backendClient;

    public List<FlightSearchResponseDTO> getFlightOffers(SearchRequestDTO searchRequestDTO) {
        return backendClient.getFlightOffers(searchRequestDTO);
    }
}