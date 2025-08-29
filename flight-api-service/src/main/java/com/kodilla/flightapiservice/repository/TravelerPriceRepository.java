package com.kodilla.flightapiservice.repository;

import com.kodilla.flightapiservice.domain.TravelerPrice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TravelerPriceRepository extends JpaRepository<TravelerPrice, Long> {
    
    List<TravelerPrice> findByFlightId(Long flightId);
    
    List<TravelerPrice> findByTravelerType(String travelerType);
}