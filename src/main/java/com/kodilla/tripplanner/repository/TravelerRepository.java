package com.kodilla.tripplanner.repository;

import com.kodilla.tripplanner.domain.Traveler;
import org.springframework.data.repository.CrudRepository;

public interface TravelerRepository extends CrudRepository<Traveler, Long> {
}
