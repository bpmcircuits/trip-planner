package com.kodilla.tripplanner.repository;

import com.kodilla.tripplanner.domain.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.math.BigDecimal;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class BaggageRepositoryTest {
    @Autowired
    private BaggageRepository baggageRepository;

    @Autowired
    private TravelerRepository travelerRepository;

    private Traveler createTraveler() {
        Traveler traveler = Traveler.builder()
                .firstName("Jan")
                .lastName("Kowalski")
                .gender(Gender.MALE)
                .personType(PersonType.ADULT)
                .build();
        return travelerRepository.save(traveler);
    }

    @Test
    void shouldSaveAndFindBaggageType() {
        Traveler traveler = createTraveler();
        Baggage baggage = Baggage.builder()
                .baggageType(BaggageType.CARRY_ON)
                .weight(BigDecimal.valueOf(10))
                .traveler(traveler)
                .build();
        Baggage saved = null;
        try {
            saved = baggageRepository.save(baggage);
            Optional<Baggage> found = baggageRepository.findById(saved.getId());
            assertThat(found).isPresent();
            assertThat(found.get().getBaggageType()).isEqualTo(BaggageType.CARRY_ON);
        } finally {
            if (saved != null && saved.getId() != null) baggageRepository.deleteById(saved.getId());
            travelerRepository.deleteById(traveler.getId());
        }
    }

    @Test
    void shouldSaveAndFindWeight() {
        Traveler traveler = createTraveler();
        Baggage baggage = Baggage.builder()
                .baggageType(BaggageType.CHECKED)
                .weight(BigDecimal.valueOf(23.5))
                .traveler(traveler)
                .build();
        Baggage saved = null;
        try {
            saved = baggageRepository.save(baggage);
            Optional<Baggage> found = baggageRepository.findById(saved.getId());
            assertThat(found).isPresent();
            assertThat(found.get().getWeight()).isEqualByComparingTo(BigDecimal.valueOf(23.5));
        } finally {
            if (saved != null && saved.getId() != null) baggageRepository.deleteById(saved.getId());
            travelerRepository.deleteById(traveler.getId());
        }
    }

    @Test
    void shouldSaveAndFindTraveler() {
        Traveler traveler = createTraveler();
        Baggage baggage = Baggage.builder()
                .baggageType(BaggageType.CARRY_ON)
                .weight(BigDecimal.valueOf(8))
                .traveler(traveler)
                .build();
        Baggage saved = null;
        try {
            saved = baggageRepository.save(baggage);
            Optional<Baggage> found = baggageRepository.findById(saved.getId());
            assertThat(found).isPresent();
            assertThat(found.get().getTraveler().getId()).isEqualTo(traveler.getId());
        } finally {
            if (saved != null && saved.getId() != null) baggageRepository.deleteById(saved.getId());
            travelerRepository.deleteById(traveler.getId());
        }
    }
}