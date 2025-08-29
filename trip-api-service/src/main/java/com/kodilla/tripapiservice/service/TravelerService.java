package com.kodilla.tripapiservice.service;

import com.kodilla.tripapiservice.domain.Traveler;
import com.kodilla.tripapiservice.exception.TravelerNotFoundException;
import com.kodilla.tripapiservice.repository.TravelerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TravelerService {

    private final TravelerRepository travelerRepository;

    public List<Traveler> getAllTravelers() {
        return travelerRepository.findAll();
    }

    public Traveler addTraveler(Traveler traveler) {
        return travelerRepository.save(traveler);
    }

    public void removeTraveler(Long travelerId) throws TravelerNotFoundException {
        if (!travelerRepository.existsById(travelerId)) {
            throw new TravelerNotFoundException("Traveler with ID " + travelerId + " does not exist.");
        }
        travelerRepository.deleteById(travelerId);
    }
}