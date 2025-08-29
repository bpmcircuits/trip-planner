package com.kodilla.tripapiservice.service;

import com.kodilla.tripapiservice.domain.Trip;
import com.kodilla.tripapiservice.exception.FlightNotFoundException;
import com.kodilla.tripapiservice.exception.HotelNotFoundException;
import com.kodilla.tripapiservice.exception.TripNotFoundException;
import com.kodilla.tripapiservice.repository.TripRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TripService {

    private final TripRepository tripRepository;

    public List<Trip> findAll() {
        return tripRepository.findAll();
    }

    public Trip findById(Long id) throws TripNotFoundException {
        return tripRepository.findById(id)
                .orElseThrow(() -> new TripNotFoundException("Trip not found with id: " + id));
    }

    public Trip save(Trip trip) {
        return tripRepository.save(trip);
    }

    public Trip updateTripFlight(Long id, Long flightId) throws TripNotFoundException, FlightNotFoundException {
        if (flightId == null) {
            throw new FlightNotFoundException("Flight ID cannot be null");
        }
        
        Trip trip = findById(id);
        trip = Trip.builder()
                .id(trip.getId())
                .name(trip.getName())
                .description(trip.getDescription())
                .createdAt(trip.getCreatedAt())
                .flightId(flightId)
                .hotelId(trip.getHotelId())
                .travelers(trip.getTravelers())
                .build();
        return save(trip);
    }

    public Trip updateTripHotel(Long id, Long hotelId) throws TripNotFoundException, HotelNotFoundException {
        if (hotelId == null) {
            throw new HotelNotFoundException("Hotel ID cannot be null");
        }
        
        Trip trip = findById(id);
        trip = Trip.builder()
                .id(trip.getId())
                .name(trip.getName())
                .description(trip.getDescription())
                .createdAt(trip.getCreatedAt())
                .flightId(trip.getFlightId())
                .hotelId(hotelId)
                .travelers(trip.getTravelers())
                .build();
        return save(trip);
    }

    public void deleteById(Long id) throws TripNotFoundException {
        if (!tripRepository.existsById(id)) {
            throw new TripNotFoundException("Trip not found with id: " + id);
        }
        tripRepository.deleteById(id);
    }

}
