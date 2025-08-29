package com.kodilla.flightapiservice.repository;

import com.kodilla.flightapiservice.domain.FlightSegment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FlightSegmentRepository extends JpaRepository<FlightSegment, Long> {
    
    List<FlightSegment> findByFlightIdAndDirection(Long flightId, String direction);
    
    List<FlightSegment> findByFromIataAndToIataAndCarrierCode(String fromIata, String toIata, String carrierCode);
}