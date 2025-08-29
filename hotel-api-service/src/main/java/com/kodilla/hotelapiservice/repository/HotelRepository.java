package com.kodilla.hotelapiservice.repository;

import com.kodilla.hotelapiservice.domain.Hotel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface HotelRepository extends JpaRepository<Hotel, Long> {
    
    Optional<Hotel> findByNameAndCityAndCountryCode(String name, String city, String countryCode);
    
    List<Hotel> findByLastUpdatedBefore(LocalDateTime date);
    
    @Query("SELECT h FROM Hotel h WHERE h.name = :name AND h.city = :city AND h.countryCode = :countryCode")
    Optional<Hotel> findExistingHotel(@Param("name") String name, 
                                      @Param("city") String city, 
                                      @Param("countryCode") String countryCode);
}
