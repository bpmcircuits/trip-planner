package com.kodilla.tripplanner.mapper;

import com.kodilla.tripplanner.domain.Baggage;
import com.kodilla.tripplanner.domain.Traveler;
import com.kodilla.tripplanner.domain.Trip;
import com.kodilla.tripplanner.dto.TravelerDTO;
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
                traveler.getBaggageList() != null ? traveler.getBaggageList().stream()
                        .map(Baggage::getId).toList() : null,
                traveler.getTrip() != null ? traveler.getTrip().getId() : null
        );
    }

    public Traveler toTraveler(TravelerDTO travelerDTO, List<Baggage> baggageList, Trip trip) {
        if (travelerDTO == null) return null;

        return Traveler.builder()
                .id(travelerDTO.id())
                .firstName(travelerDTO.firstName())
                .lastName(travelerDTO.lastName())
                .gender(travelerDTO.gender())
                .personType(travelerDTO.personType())
                .age(travelerDTO.age())
                .baggageList(baggageList)
                .trip(trip)
                .build();
    }
}
