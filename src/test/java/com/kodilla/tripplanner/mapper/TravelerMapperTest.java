package com.kodilla.tripplanner.mapper;

import com.kodilla.tripplanner.domain.*;
import com.kodilla.tripplanner.dto.TravelerDTO;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class TravelerMapperTest {

    private final TravelerMapper mapper = new TravelerMapper();

    @Test
    void shouldMapToTravelerDTO() {
        Trip trip = Trip.builder().id(1L).build();
        Baggage baggage = Baggage.builder().id(2L).build();
        Traveler traveler = Traveler.builder()
                .id(3L)
                .firstName("Jan")
                .lastName("Kowalski")
                .gender(Gender.MALE)
                .personType(PersonType.ADULT)
                .age(30)
                .baggageList(List.of(baggage))
                .trip(trip)
                .build();

        TravelerDTO dto = mapper.toTravelerDTO(traveler);

        assertThat(dto).isNotNull();
        assertThat(dto.id()).isEqualTo(3L);
        assertThat(dto.firstName()).isEqualTo("Jan");
        assertThat(dto.lastName()).isEqualTo("Kowalski");
        assertThat(dto.gender()).isEqualTo(Gender.MALE);
        assertThat(dto.personType()).isEqualTo(PersonType.ADULT);
        assertThat(dto.age()).isEqualTo(30);
        assertThat(dto.baggageIds()).containsExactly(2L);
        assertThat(dto.tripId()).isEqualTo(1L);
    }

    @Test
    void shouldMapToTraveler() {
        TravelerDTO dto = new TravelerDTO(4L, "Anna", "Nowak",
                Gender.FEMALE, PersonType.CHILD, 12, List.of(5L), 6L);
        List<Baggage> baggageList = List.of(Baggage.builder().id(5L).build());
        Trip trip = Trip.builder().id(6L).build();

        Traveler traveler = mapper.toTraveler(dto, baggageList, trip);

        assertThat(traveler).isNotNull();
        assertThat(traveler.getId()).isEqualTo(4L);
        assertThat(traveler.getFirstName()).isEqualTo("Anna");
        assertThat(traveler.getLastName()).isEqualTo("Nowak");
        assertThat(traveler.getGender()).isEqualTo(Gender.FEMALE);
        assertThat(traveler.getPersonType()).isEqualTo(PersonType.CHILD);
        assertThat(traveler.getAge()).isEqualTo(12);
        assertThat(traveler.getBaggageList()).isEqualTo(baggageList);
        assertThat(traveler.getTrip()).isEqualTo(trip);
    }

    @Test
    void shouldReturnNullForNullInput() {
        assertThat(mapper.toTravelerDTO(null)).isNull();
        assertThat(mapper.toTraveler(null, null, null)).isNull();
    }
}