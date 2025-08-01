package com.kodilla.tripplanner.mapper;

import com.kodilla.tripplanner.domain.Hotel;
import com.kodilla.tripplanner.dto.HotelDTO;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

class HotelMapperTest {

    private final HotelMapper mapper = new HotelMapper();

    @Test
    void shouldMapToHotelDTO() {
        Hotel hotel = Hotel.builder()
                .id(1L)
                .name("Hilton")
                .country("Poland")
                .city("Warsaw")
                .checkInDate(LocalDate.now())
                .checkOutDate(LocalDate.now().plusDays(2))
                .price(BigDecimal.valueOf(500.0))
                .build();

        HotelDTO dto = mapper.toHotelDTO(hotel);

        assertThat(dto).isNotNull();
        assertThat(dto.id()).isEqualTo(1L);
        assertThat(dto.name()).isEqualTo("Hilton");
        assertThat(dto.country()).isEqualTo("Poland");
        assertThat(dto.city()).isEqualTo("Warsaw");
        assertThat(dto.price()).isEqualTo(BigDecimal.valueOf(500.0));
    }

    @Test
    void shouldMapToHotel() {
        LocalDate now = LocalDate.now();
        HotelDTO dto = new HotelDTO(2L, "Marriott", "USA", "NYC",
                now, now.plusDays(3), BigDecimal.valueOf(800.0));

        Hotel hotel = mapper.toHotel(dto);

        assertThat(hotel).isNotNull();
        assertThat(hotel.getId()).isEqualTo(2L);
        assertThat(hotel.getName()).isEqualTo("Marriott");
        assertThat(hotel.getCountry()).isEqualTo("USA");
        assertThat(hotel.getCity()).isEqualTo("NYC");
        assertThat(hotel.getPrice()).isEqualTo(BigDecimal.valueOf(800.0));
    }

    @Test
    void shouldReturnNullForNullInput() {
        assertThat(mapper.toHotelDTO(null)).isNull();
        assertThat(mapper.toHotel(null)).isNull();
    }
}