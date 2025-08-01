package com.kodilla.tripplanner.mapper;

import com.kodilla.tripplanner.domain.Flight;
import com.kodilla.tripplanner.domain.Hotel;
import com.kodilla.tripplanner.domain.Traveler;
import com.kodilla.tripplanner.domain.Trip;
import com.kodilla.tripplanner.dto.TripDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TripMapper {
    public TripDTO toTripDTO(Trip trip) {
        if (trip == null) return null;

        return new TripDTO(
                trip.getId(),
                trip.getName(),
                trip.getDescription(),
                trip.getCreatedAt(),
                trip.getFlight() != null ? trip.getFlight().getId() : null,
                trip.getHotel() != null ? trip.getHotel().getId() : null,
                trip.getTravelers() != null ? trip.getTravelers().stream()
                        .map(Traveler::getId).toList() : null
        );
    }

    public Trip toTrip(TripDTO tripDTO, Flight flight, Hotel hotel, List<Traveler> travelers) {
        if (tripDTO == null) return null;
        return Trip.builder()
                .id(tripDTO.id())
                .name(tripDTO.name())
                .description(tripDTO.description())
                .createdAt(tripDTO.createdAt())
                .flight(flight)
                .hotel(hotel)
                .travelers(travelers)
                .build();
    }
}
