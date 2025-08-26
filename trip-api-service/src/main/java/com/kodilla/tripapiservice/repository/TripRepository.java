package com.kodilla.tripplanner.repository;

import com.kodilla.tripplanner.domain.Trip;
import org.springframework.data.repository.CrudRepository;

public interface TripRepository extends CrudRepository<Trip, Long> {
}
