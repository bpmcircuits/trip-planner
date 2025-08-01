package com.kodilla.tripplanner.mapper;

import com.kodilla.tripplanner.domain.Baggage;
import com.kodilla.tripplanner.domain.BaggageType;
import com.kodilla.tripplanner.domain.Traveler;
import com.kodilla.tripplanner.dto.BaggageDTO;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

class BaggageMapperTest {

    private final BaggageMapper mapper = new BaggageMapper();

    @Test
    void shouldMapToBaggageDTO() {
        Traveler traveler = Traveler.builder().id(1L).build();
        Baggage baggage = Baggage.builder().id(2L).baggageType(BaggageType.CARRY_ON).weight(BigDecimal.valueOf(10.0)).traveler(traveler).build();

        BaggageDTO dto = mapper.toBaggageDTO(baggage);

        assertThat(dto).isNotNull();
        assertThat(dto.id()).isEqualTo(2L);
        assertThat(dto.type()).isEqualTo(BaggageType.CARRY_ON);
        assertThat(dto.weight()).isEqualTo(BigDecimal.valueOf(10.0));
        assertThat(dto.travelerId()).isEqualTo(1L);
    }

    @Test
    void shouldMapToBaggage() {
        BaggageDTO dto = new BaggageDTO(3L, BaggageType.CHECKED, BigDecimal.valueOf(20.0), 4L);
        Traveler traveler = Traveler.builder().id(4L).build();

        Baggage baggage = mapper.toBaggage(dto, traveler);

        assertThat(baggage).isNotNull();
        assertThat(baggage.getId()).isEqualTo(3L);
        assertThat(baggage.getBaggageType()).isEqualTo(BaggageType.CHECKED);
        assertThat(baggage.getWeight()).isEqualTo(BigDecimal.valueOf(20.0));
        assertThat(baggage.getTraveler()).isEqualTo(traveler);
    }

    @Test
    void shouldReturnNullForNullInput() {
        assertThat(mapper.toBaggageDTO(null)).isNull();
        assertThat(mapper.toBaggage(null, null)).isNull();
    }
}