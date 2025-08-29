package com.kodilla.tripapiservice.controller;

import com.kodilla.tripapiservice.domain.Traveler;
import com.kodilla.tripapiservice.dto.TravelerDTO;
import com.kodilla.tripapiservice.exception.TravelerNotFoundException;
import com.kodilla.tripapiservice.mapper.TravelerMapper;
import com.kodilla.tripapiservice.service.TravelerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/trips/travelers")
@RequiredArgsConstructor
public class TravelerController {

    private final TravelerService travelerService;
    private final TravelerMapper travelerMapper;

    @GetMapping
    public ResponseEntity<List<TravelerDTO>> getAllTravelers() {
        List<Traveler> travelers = travelerService.getAllTravelers();
        return ResponseEntity.ok(travelerMapper.toTravelerDTOList(travelers));
    }

    @PostMapping("/add")
    public ResponseEntity<TravelerDTO> addTraveler(@RequestBody TravelerDTO travelerDTO) {
        Traveler traveler = travelerMapper.toTraveler(travelerDTO);
        Traveler addedTraveler = travelerService.addTraveler(traveler);
        return ResponseEntity.ok(travelerMapper.toTravelerDTO(addedTraveler));
    }

    @DeleteMapping("/{travelerId}")
    public ResponseEntity<Void> removeTraveler(@PathVariable Long travelerId) throws TravelerNotFoundException {
        travelerService.removeTraveler(travelerId);
        return ResponseEntity.ok().build();
    }
}
