package com.kodilla.tripapiservice.mapper;

import com.kodilla.tripapiservice.domain.Traveler;
import com.kodilla.tripapiservice.domain.Trip;
import com.kodilla.tripapiservice.dto.TripDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TripMapper {

    public List<TripDTO> toTripDTOList(List<Trip> trips) {
        if (trips == null) return null;

        return trips.stream()
                .map(this::toTripDTO)
                .toList();
    }
    public TripDTO toTripDTO(Trip trip) {
        if (trip == null) return null;

        return new TripDTO(
                trip.getId(),
                trip.getName(),
                trip.getDescription(),
                trip.getCreatedAt(),
                trip.getFlightId(),
                trip.getHotelId(),
                trip.getTravelers() != null ? trip.getTravelers().stream()
                        .map(Traveler::getId).toList() : null
        );
    }

    public Trip toTrip(TripDTO tripDTO, List<Traveler> travelers) {
        if (tripDTO == null) return null;
        return Trip.builder()
                .id(tripDTO.id())
                .name(tripDTO.name())
                .description(tripDTO.description())
                .createdAt(tripDTO.createdAt())
                .flightId(tripDTO.flightId())
                .hotelId(tripDTO.hotelId())
                .travelers(travelers)
                .build();
    }
}
