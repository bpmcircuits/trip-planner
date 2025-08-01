package com.kodilla.tripplanner.mapper;

import com.kodilla.tripplanner.domain.Baggage;
import com.kodilla.tripplanner.domain.Traveler;
import com.kodilla.tripplanner.dto.BaggageDTO;
import org.springframework.stereotype.Service;

@Service
public class BaggageMapper {
    public BaggageDTO toBaggageDTO(Baggage baggage) {
        if (baggage == null) return null;

        return new BaggageDTO(
                baggage.getId(),
                baggage.getBaggageType(),
                baggage.getWeight(),
                baggage.getTraveler() != null ? baggage.getTraveler().getId() : null
        );
    }

    public Baggage toBaggage(BaggageDTO baggageDTO, Traveler traveler) {
        if (baggageDTO == null) return null;

        return Baggage.builder()
                .id(baggageDTO.id())
                .baggageType(baggageDTO.type())
                .weight(baggageDTO.weight())
                .traveler(traveler)
                .build();
    }
}
