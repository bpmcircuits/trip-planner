package com.kodilla.flightapiservice.repository;

import com.kodilla.flightapiservice.domain.Flight;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface FlightRepository extends JpaRepository<Flight, Long> {
    
    Optional<Flight> findBySearchId(UUID searchId);
    
    List<Flight> findByLastUpdatedBefore(LocalDateTime date);
    
    @Query("SELECT f FROM Flight f WHERE f.searchId = :searchId")
    Optional<Flight> findExistingFlight(@Param("searchId") UUID searchId);
}