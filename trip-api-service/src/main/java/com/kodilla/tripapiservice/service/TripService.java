package com.kodilla.tripplanner.service;

import com.kodilla.tripplanner.domain.Traveler;
import com.kodilla.tripplanner.dto.TravelerDTO;
import com.kodilla.tripplanner.repository.TravelerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TripService {

    private final TravelerRepository travelerRepository;

    public List<Traveler> getAllTravelers() {
        return travelerRepository.findAll();
    }

    public Traveler addTraveler(Traveler traveler) {
        return travelerRepository.save(traveler);
    }

    public void removeTraveler(Long travelerId) {
        if (travelerRepository.existsById(travelerId)) {
            travelerRepository.deleteById(travelerId);
        } else {
            throw new IllegalArgumentException("Traveler with ID " + travelerId + " does not exist.");
        }
    }
}
