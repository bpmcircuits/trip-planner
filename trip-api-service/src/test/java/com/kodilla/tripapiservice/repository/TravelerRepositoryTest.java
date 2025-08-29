package com.kodilla.tripapiservice.repository;

import com.kodilla.tripapiservice.domain.BaggageType;
import com.kodilla.tripapiservice.domain.Gender;
import com.kodilla.tripapiservice.domain.PersonType;
import com.kodilla.tripapiservice.domain.Traveler;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class TravelerRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private TravelerRepository travelerRepository;

    @Test
    void shouldSaveTraveler() {
        // Given
        Traveler traveler = Traveler.builder()
                .firstName("John")
                .lastName("Doe")
                .gender(Gender.MALE)
                .personType(PersonType.ADULT)
                .age(30)
                .baggage(BaggageType.CHECKED)
                .build();

        // When
        Traveler savedTraveler = travelerRepository.save(traveler);

        // Then
        assertThat(savedTraveler.getId()).isNotNull();
        assertThat(savedTraveler.getFirstName()).isEqualTo("John");
        assertThat(savedTraveler.getLastName()).isEqualTo("Doe");
        assertThat(savedTraveler.getGender()).isEqualTo(Gender.MALE);
        assertThat(savedTraveler.getPersonType()).isEqualTo(PersonType.ADULT);
        assertThat(savedTraveler.getAge()).isEqualTo(30);
        assertThat(savedTraveler.getBaggage()).isEqualTo(BaggageType.CHECKED);
    }

    @Test
    void shouldFindTravelerById() {
        // Given
        Traveler traveler = Traveler.builder()
                .firstName("John")
                .lastName("Doe")
                .gender(Gender.MALE)
                .personType(PersonType.ADULT)
                .age(30)
                .baggage(BaggageType.CHECKED)
                .build();
        
        entityManager.persist(traveler);
        entityManager.flush();

        // When
        Optional<Traveler> foundTraveler = travelerRepository.findById(traveler.getId());

        // Then
        assertThat(foundTraveler).isPresent();
        assertThat(foundTraveler.get().getFirstName()).isEqualTo("John");
        assertThat(foundTraveler.get().getLastName()).isEqualTo("Doe");
    }

    @Test
    void shouldFindAllTravelers() {
        // Given
        Traveler traveler1 = Traveler.builder()
                .firstName("John")
                .lastName("Doe")
                .gender(Gender.MALE)
                .personType(PersonType.ADULT)
                .age(30)
                .baggage(BaggageType.CHECKED)
                .build();
        
        Traveler traveler2 = Traveler.builder()
                .firstName("Jane")
                .lastName("Doe")
                .gender(Gender.FEMALE)
                .personType(PersonType.ADULT)
                .age(28)
                .baggage(BaggageType.CABIN)
                .build();
        
        entityManager.persist(traveler1);
        entityManager.persist(traveler2);
        entityManager.flush();

        // When
        List<Traveler> travelers = travelerRepository.findAll();

        // Then
        assertThat(travelers).hasSize(2);
        assertThat(travelers).extracting(Traveler::getFirstName).containsExactlyInAnyOrder("John", "Jane");
    }

    @Test
    void shouldUpdateTraveler() {
        // Given
        Traveler traveler = Traveler.builder()
                .firstName("John")
                .lastName("Doe")
                .gender(Gender.MALE)
                .personType(PersonType.ADULT)
                .age(30)
                .baggage(BaggageType.CHECKED)
                .build();
        
        entityManager.persist(traveler);
        entityManager.flush();
        
        // When
        Traveler savedTraveler = travelerRepository.findById(traveler.getId()).get();
        savedTraveler = Traveler.builder()
                .id(savedTraveler.getId())
                .firstName("Johnny")
                .lastName("Doe")
                .gender(Gender.MALE)
                .personType(PersonType.ADULT)
                .age(31)
                .baggage(BaggageType.CABIN)
                .build();
        
        travelerRepository.save(savedTraveler);
        
        // Then
        Traveler updatedTraveler = travelerRepository.findById(traveler.getId()).get();
        assertThat(updatedTraveler.getFirstName()).isEqualTo("Johnny");
        assertThat(updatedTraveler.getAge()).isEqualTo(31);
        assertThat(updatedTraveler.getBaggage()).isEqualTo(BaggageType.CABIN);
    }

    @Test
    void shouldDeleteTraveler() {
        // Given
        Traveler traveler = Traveler.builder()
                .firstName("John")
                .lastName("Doe")
                .gender(Gender.MALE)
                .personType(PersonType.ADULT)
                .age(30)
                .baggage(BaggageType.CHECKED)
                .build();
        
        entityManager.persist(traveler);
        entityManager.flush();
        
        // When
        travelerRepository.deleteById(traveler.getId());
        
        // Then
        Optional<Traveler> deletedTraveler = travelerRepository.findById(traveler.getId());
        assertThat(deletedTraveler).isEmpty();
    }

    @Test
    void shouldCheckIfTravelerExists() {
        // Given
        Traveler traveler = Traveler.builder()
                .firstName("John")
                .lastName("Doe")
                .gender(Gender.MALE)
                .personType(PersonType.ADULT)
                .age(30)
                .baggage(BaggageType.CHECKED)
                .build();
        
        entityManager.persist(traveler);
        entityManager.flush();
        
        // When & Then
        assertThat(travelerRepository.existsById(traveler.getId())).isTrue();
        assertThat(travelerRepository.existsById(999L)).isFalse();
    }
}