package com.kodilla.tripplanner.mapper;

import com.kodilla.tripplanner.domain.Flight;
import com.kodilla.tripplanner.dto.FlightDTO;
import org.springframework.stereotype.Service;

@Service
public class FlightMapper {
    public FlightDTO toFlightDTO(Flight flight) {
        if (flight == null) return  null;

        return new FlightDTO(
                flight.getId(),
                flight.getFlightNumber(),
                flight.getDepartureAirport(),
                flight.getDepartureAirportCode(),
                flight.getArrivalAirport(),
                flight.getArrivalAirportCode(),
                flight.getDepartureDateTime(),
                flight.getArrivalDateTime(),
                flight.getPrice(),
                flight.getCurrency()
        );
    }

    public Flight toFlight(FlightDTO flightDTO) {
        if (flightDTO == null) return null;

        return Flight.builder()
                .id(flightDTO.id())
                .flightNumber(flightDTO.flightNumber())
                .departureAirport(flightDTO.departureAirport())
                .departureAirportCode(flightDTO.departureAirportCode())
                .arrivalAirport(flightDTO.arrivalAirport())
                .arrivalAirportCode(flightDTO.arrivalAirportCode())
                .departureDateTime(flightDTO.departureDateTime())
                .arrivalDateTime(flightDTO.arrivalDateTime())
                .price(flightDTO.price())
                .currency(flightDTO.currency())
                .build();
    }
}
