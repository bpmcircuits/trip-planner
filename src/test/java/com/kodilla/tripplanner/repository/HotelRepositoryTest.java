package com.kodilla.tripplanner.repository;

import com.kodilla.tripplanner.domain.Hotel;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class HotelRepositoryTest {

    @Autowired
    private HotelRepository hotelRepository;

    @Test
    void shouldSaveAndFindName() {
        Hotel hotel = Hotel.builder()
                .name("Hilton")
                .country("Polska")
                .city("Warszawa")
                .checkInDate(LocalDate.now())
                .checkOutDate(LocalDate.now().plusDays(2))
                .price(BigDecimal.valueOf(500))
                .build();
        Hotel saved = null;
        try {
            saved = hotelRepository.save(hotel);
            Optional<Hotel> found = hotelRepository.findById(saved.getId());
            assertThat(found).isPresent();
            assertThat(found.get().getName()).isEqualTo("Hilton");
        } finally {
            if (saved != null && saved.getId() != null) hotelRepository.deleteById(saved.getId());
        }
    }

    @Test
    void shouldSaveAndFindCountry() {
        Hotel hotel = Hotel.builder()
                .name("Marriott")
                .country("Niemcy")
                .city("Berlin")
                .checkInDate(LocalDate.now())
                .checkOutDate(LocalDate.now().plusDays(3))
                .price(BigDecimal.valueOf(600))
                .build();
        Hotel saved = null;
        try {
            saved = hotelRepository.save(hotel);
            Optional<Hotel> found = hotelRepository.findById(saved.getId());
            assertThat(found).isPresent();
            assertThat(found.get().getCountry()).isEqualTo("Niemcy");
        } finally {
            if (saved != null && saved.getId() != null) hotelRepository.deleteById(saved.getId());
        }
    }

    @Test
    void shouldSaveAndFindCity() {
        Hotel hotel = Hotel.builder()
                .name("Sheraton")
                .country("Francja")
                .city("Paryż")
                .checkInDate(LocalDate.now())
                .checkOutDate(LocalDate.now().plusDays(1))
                .price(BigDecimal.valueOf(700))
                .build();
        Hotel saved = null;
        try {
            saved = hotelRepository.save(hotel);
            Optional<Hotel> found = hotelRepository.findById(saved.getId());
            assertThat(found).isPresent();
            assertThat(found.get().getCity()).isEqualTo("Paryż");
        } finally {
            if (saved != null && saved.getId() != null) hotelRepository.deleteById(saved.getId());
        }
    }

    @Test
    void shouldSaveAndFindFromDate() {
        LocalDate from = LocalDate.of(2024, 6, 1);
        Hotel hotel = Hotel.builder()
                .name("Novotel")
                .country("Włochy")
                .city("Rzym")
                .checkInDate(from)
                .checkOutDate(from.plusDays(5))
                .price(BigDecimal.valueOf(800))
                .build();
        Hotel saved = null;
        try {
            saved = hotelRepository.save(hotel);
            Optional<Hotel> found = hotelRepository.findById(saved.getId());
            assertThat(found).isPresent();
            assertThat(found.get().getCheckInDate()).isEqualTo(from);
        } finally {
            if (saved != null && saved.getId() != null) hotelRepository.deleteById(saved.getId());
        }
    }

    @Test
    void shouldSaveAndFindToDate() {
        LocalDate to = LocalDate.of(2024, 7, 10);
        Hotel hotel = Hotel.builder()
                .name("Ibis")
                .country("Hiszpania")
                .city("Madryt")
                .checkInDate(to.minusDays(2))
                .checkOutDate(to)
                .price(BigDecimal.valueOf(400))
                .build();
        Hotel saved = null;
        try {
            saved = hotelRepository.save(hotel);
            Optional<Hotel> found = hotelRepository.findById(saved.getId());
            assertThat(found).isPresent();
            assertThat(found.get().getCheckOutDate()).isEqualTo(to);
        } finally {
            if (saved != null && saved.getId() != null) hotelRepository.deleteById(saved.getId());
        }
    }

    @Test
    void shouldSaveAndFindPrice() {
        BigDecimal price = BigDecimal.valueOf(999.99);
        Hotel hotel = Hotel.builder()
                .name("Radisson")
                .country("Szwecja")
                .city("Sztokholm")
                .checkInDate(LocalDate.now())
                .checkOutDate(LocalDate.now().plusDays(4))
                .price(price)
                .build();
        Hotel saved = null;
        try {
            saved = hotelRepository.save(hotel);
            Optional<Hotel> found = hotelRepository.findById(saved.getId());
            assertThat(found).isPresent();
            assertThat(found.get().getPrice()).isEqualByComparingTo(price);
        } finally {
            if (saved != null && saved.getId() != null) hotelRepository.deleteById(saved.getId());
        }
    }
}