package com.kodilla.tripapiservice.mapper;

import com.kodilla.tripapiservice.domain.Traveler;
import com.kodilla.tripapiservice.dto.TravelerDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TravelerMapper {

    public TravelerDTO toTravelerDTO(Traveler traveler) {
        if (traveler == null) return null;

        return new TravelerDTO(
                traveler.getId(),
                traveler.getFirstName(),
                traveler.getLastName(),
                traveler.getGender(),
                traveler.getPersonType(),
                traveler.getAge(),
                traveler.getBaggage(),
                traveler.getTrip() != null ? traveler.getTrip().getId() : null
        );
    }

    public List<TravelerDTO> toTravelerDTOList(List<Traveler> travelers) {
        if (travelers == null) return null;

        return travelers.stream()
                .map(this::toTravelerDTO)
                .toList();
    }

    public Traveler toTraveler(TravelerDTO travelerDTO) {
        if (travelerDTO == null) return null;

        return Traveler.builder()
                .firstName(travelerDTO.firstName())
                .lastName(travelerDTO.lastName())
                .gender(travelerDTO.gender())
                .personType(travelerDTO.personType())
                .age(travelerDTO.age())
                .baggage(travelerDTO.baggage())
                .build();
    }
}
