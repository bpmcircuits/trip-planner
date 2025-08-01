package com.kodilla.tripplanner.repository;

import com.kodilla.tripplanner.domain.Gender;
import com.kodilla.tripplanner.domain.PersonType;
import com.kodilla.tripplanner.domain.Traveler;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class TravelerRepositoryTest {

    @Autowired
    private TravelerRepository travelerRepository;

    @Test
    void shouldSaveAndFindFirstName() {
        Traveler traveler = Traveler.builder()
                .firstName("Anna")
                .lastName("Nowak")
                .gender(Gender.FEMALE)
                .personType(PersonType.ADULT)
                .age(30)
                .build();
        Traveler saved = null;
        try {
            saved = travelerRepository.save(traveler);
            Optional<Traveler> found = travelerRepository.findById(saved.getId());
            assertThat(found).isPresent();
            assertThat(found.get().getFirstName()).isEqualTo("Anna");
        } finally {
            if (saved != null && saved.getId() != null) travelerRepository.deleteById(saved.getId());
        }
    }

    @Test
    void shouldSaveAndFindLastName() {
        Traveler traveler = Traveler.builder()
                .firstName("Jan")
                .lastName("Kowalski")
                .gender(Gender.MALE)
                .personType(PersonType.ADULT)
                .age(40)
                .build();
        Traveler saved = null;
        try {
            saved = travelerRepository.save(traveler);
            Optional<Traveler> found = travelerRepository.findById(saved.getId());
            assertThat(found).isPresent();
            assertThat(found.get().getLastName()).isEqualTo("Kowalski");
        } finally {
            if (saved != null && saved.getId() != null) travelerRepository.deleteById(saved.getId());
        }
    }

    @Test
    void shouldSaveAndFindGender() {
        Traveler traveler = Traveler.builder()
                .firstName("Maria")
                .lastName("Wiśniewska")
                .gender(Gender.FEMALE)
                .personType(PersonType.CHILD)
                .age(12)
                .build();
        Traveler saved = null;
        try {
            saved = travelerRepository.save(traveler);
            Optional<Traveler> found = travelerRepository.findById(saved.getId());
            assertThat(found).isPresent();
            assertThat(found.get().getGender()).isEqualTo(Gender.FEMALE);
        } finally {
            if (saved != null && saved.getId() != null) travelerRepository.deleteById(saved.getId());
        }
    }

    @Test
    void shouldSaveAndFindPersonType() {
        Traveler traveler = Traveler.builder()
                .firstName("Piotr")
                .lastName("Zieliński")
                .gender(Gender.MALE)
                .personType(PersonType.ADULT)
                .age(25)
                .build();
        Traveler saved = null;
        try {
            saved = travelerRepository.save(traveler);
            Optional<Traveler> found = travelerRepository.findById(saved.getId());
            assertThat(found).isPresent();
            assertThat(found.get().getPersonType()).isEqualTo(PersonType.ADULT);
        } finally {
            if (saved != null && saved.getId() != null) travelerRepository.deleteById(saved.getId());
        }
    }

    @Test
    void shouldSaveAndFindAge() {
        Traveler traveler = Traveler.builder()
                .firstName("Kasia")
                .lastName("Lewandowska")
                .gender(Gender.FEMALE)
                .personType(PersonType.CHILD)
                .age(8)
                .build();
        Traveler saved = null;
        try {
            saved = travelerRepository.save(traveler);
            Optional<Traveler> found = travelerRepository.findById(saved.getId());
            assertThat(found).isPresent();
            assertThat(found.get().getAge()).isEqualTo(8);
        } finally {
            if (saved != null && saved.getId() != null) travelerRepository.deleteById(saved.getId());
        }
    }
}