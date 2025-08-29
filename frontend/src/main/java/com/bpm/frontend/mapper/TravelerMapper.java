package com.bpm.frontend.mapper;

import com.bpm.frontend.dto.TravelerDTO;
import com.bpm.frontend.tripplanerback.dto.travelers.TravelerRequestDTO;
import com.bpm.frontend.tripplanerback.dto.travelers.TravelerResponseDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TravelerMapper {

    public List<TravelerDTO> mapFromResponseToList(List<TravelerResponseDTO> travelerResponseDTO) {
        return travelerResponseDTO.stream()
                .map(this::mapFromResponse)
                .toList();
    }

    public TravelerDTO mapFromResponse(TravelerResponseDTO travelerResponseDTO) {
        return new TravelerDTO(
                travelerResponseDTO.id(),
                travelerResponseDTO.firstName(),
                travelerResponseDTO.lastName(),
                travelerResponseDTO.gender(),
                travelerResponseDTO.personType(),
                travelerResponseDTO.baggage()
        );
    }

    public TravelerRequestDTO mapToRequest(TravelerDTO travelerDTO) {
        return new TravelerRequestDTO(
                travelerDTO.firstName(),
                travelerDTO.lastName(),
                travelerDTO.gender(),
                travelerDTO.personType(),
                travelerDTO.baggageType()
        );
    }
}
