package com.kodilla.tripplanner.repository;

import com.kodilla.tripplanner.domain.BaggageType;
import com.kodilla.tripplanner.domain.Gender;
import com.kodilla.tripplanner.domain.PersonType;
import com.kodilla.tripplanner.domain.Traveler;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class TravelerRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private TravelerRepository travelerRepository;

    @Test
    void testSaveAndFindById() {
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
        entityManager.persist(traveler);
        entityManager.flush();

        // Then
        Optional<Traveler> found = travelerRepository.findById(traveler.getId());
        assertTrue(found.isPresent());
        assertEquals("John", found.get().getFirstName());
        assertEquals("Doe", found.get().getLastName());
    }

    @Test
    void testFindAll() {
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
                .lastName("Smith")
                .gender(Gender.FEMALE)
                .personType(PersonType.ADULT)
                .age(25)
                .baggage(BaggageType.CABIN)
                .build();

        entityManager.persist(traveler1);
        entityManager.persist(traveler2);
        entityManager.flush();

        // When
        List<Traveler> travelers = travelerRepository.findAll();

        // Then
        assertEquals(2, travelers.size());
        assertTrue(travelers.stream().anyMatch(t -> t.getFirstName().equals("John")));
        assertTrue(travelers.stream().anyMatch(t -> t.getFirstName().equals("Jane")));
    }

    @Test
    void testDeleteById() {
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
        
        Long id = traveler.getId();

        // When
        travelerRepository.deleteById(id);

        // Then
        Optional<Traveler> found = travelerRepository.findById(id);
        assertFalse(found.isPresent());
    }

    @Test
    void testExistsById() {
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
        
        Long id = traveler.getId();

        // When & Then
        assertTrue(travelerRepository.existsById(id));
        assertFalse(travelerRepository.existsById(id + 1));
    }
}