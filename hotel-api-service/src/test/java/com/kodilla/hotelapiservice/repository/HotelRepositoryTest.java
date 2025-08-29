package com.kodilla.hotelapiservice.repository;

import com.kodilla.hotelapiservice.domain.Hotel;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
class HotelRepositoryTest {

    @Autowired
    private HotelRepository hotelRepository;

    @Test
    void shouldSaveAndFindHotel() {
        // Given
        LocalDate checkInDate = LocalDate.now();
        LocalDate checkOutDate = checkInDate.plusDays(5);
        
        Hotel hotel = Hotel.builder()
                .id(1L) // Manually set ID
                .name("Test Hotel")
                .countryCode("PL")
                .city("Warsaw")
                .price(new BigDecimal("100.00"))
                .currency("PLN")
                .checkInDate(checkInDate)
                .checkOutDate(checkOutDate)
                .reviewScore(8.5)
                .reviewScoreWord("Very Good")
                .reviewCount(100)
                .lastUpdated(LocalDateTime.now())
                .build();

        // When
        Hotel savedHotel = hotelRepository.save(hotel);
        Optional<Hotel> foundHotel = hotelRepository.findById(savedHotel.getId());

        // Then
        assertTrue(foundHotel.isPresent());
        assertEquals("Test Hotel", foundHotel.get().getName());
        assertEquals("PL", foundHotel.get().getCountryCode());
        assertEquals("Warsaw", foundHotel.get().getCity());
        assertEquals(new BigDecimal("100.00"), foundHotel.get().getPrice());

        // Cleanup
        hotelRepository.delete(savedHotel);
    }

    @Test
    void shouldFindByNameAndCityAndCountryCode() {
        // Given
        LocalDate checkInDate = LocalDate.now();
        LocalDate checkOutDate = checkInDate.plusDays(5);
        
        Hotel hotel = Hotel.builder()
                .id(2L) // Manually set ID
                .name("Test Hotel")
                .countryCode("PL")
                .city("Warsaw")
                .price(new BigDecimal("100.00"))
                .currency("PLN")
                .checkInDate(checkInDate)
                .checkOutDate(checkOutDate)
                .reviewScore(8.5)
                .reviewScoreWord("Very Good")
                .reviewCount(100)
                .lastUpdated(LocalDateTime.now())
                .build();

        hotelRepository.save(hotel);

        // When
        Optional<Hotel> foundHotel = hotelRepository.findByNameAndCityAndCountryCode(
                "Test Hotel", "Warsaw", "PL");

        // Then
        assertTrue(foundHotel.isPresent());
        assertEquals("Test Hotel", foundHotel.get().getName());
        assertEquals("PL", foundHotel.get().getCountryCode());
        assertEquals("Warsaw", foundHotel.get().getCity());

        // Cleanup
        hotelRepository.delete(hotel);
    }

    @Test
    void shouldFindByLastUpdatedBefore() {
        // Given
        LocalDate checkInDate = LocalDate.now();
        LocalDate checkOutDate = checkInDate.plusDays(5);
        LocalDateTime oldDate = LocalDateTime.now().minusDays(31);
        
        Hotel hotel1 = Hotel.builder()
                .id(3L) // Manually set ID
                .name("Old Hotel")
                .countryCode("PL")
                .city("Warsaw")
                .price(new BigDecimal("100.00"))
                .currency("PLN")
                .checkInDate(checkInDate)
                .checkOutDate(checkOutDate)
                .reviewScore(8.5)
                .reviewScoreWord("Very Good")
                .reviewCount(100)
                .lastUpdated(oldDate)
                .build();

        Hotel hotel2 = Hotel.builder()
                .id(4L) // Manually set ID
                .name("New Hotel")
                .countryCode("PL")
                .city("Warsaw")
                .price(new BigDecimal("150.00"))
                .currency("PLN")
                .checkInDate(checkInDate)
                .checkOutDate(checkOutDate)
                .reviewScore(9.0)
                .reviewScoreWord("Excellent")
                .reviewCount(200)
                .lastUpdated(LocalDateTime.now())
                .build();

        hotelRepository.save(hotel1);
        hotelRepository.save(hotel2);

        // When
        LocalDateTime cutoffDate = LocalDateTime.now().minusDays(30);
        List<Hotel> outdatedHotels = hotelRepository.findByLastUpdatedBefore(cutoffDate);

        // Then
        assertEquals(1, outdatedHotels.size());
        assertEquals("Old Hotel", outdatedHotels.get(0).getName());

        // Cleanup
        hotelRepository.deleteAll();
    }

    @Test
    void shouldFindExistingHotel() {
        // Given
        LocalDate checkInDate = LocalDate.now();
        LocalDate checkOutDate = checkInDate.plusDays(5);
        
        Hotel hotel = Hotel.builder()
                .id(5L) // Manually set ID
                .name("Test Hotel")
                .countryCode("PL")
                .city("Warsaw")
                .price(new BigDecimal("100.00"))
                .currency("PLN")
                .checkInDate(checkInDate)
                .checkOutDate(checkOutDate)
                .reviewScore(8.5)
                .reviewScoreWord("Very Good")
                .reviewCount(100)
                .lastUpdated(LocalDateTime.now())
                .build();

        hotelRepository.save(hotel);

        // When
        Optional<Hotel> foundHotel = hotelRepository.findExistingHotel(
                "Test Hotel", "Warsaw", "PL");

        // Then
        assertTrue(foundHotel.isPresent());
        assertEquals("Test Hotel", foundHotel.get().getName());
        assertEquals("PL", foundHotel.get().getCountryCode());
        assertEquals("Warsaw", foundHotel.get().getCity());

        // Cleanup
        hotelRepository.delete(hotel);
    }
}