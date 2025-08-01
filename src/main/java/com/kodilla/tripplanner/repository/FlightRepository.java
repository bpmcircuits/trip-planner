package com.kodilla.tripplanner.repository;

import com.kodilla.tripplanner.domain.Flight;
import org.springframework.data.repository.CrudRepository;

public interface FlightRepository extends CrudRepository<Flight, Long> {
}
