package com.kodilla.tripplanner.repository;

import com.kodilla.tripplanner.domain.Baggage;
import org.springframework.data.repository.CrudRepository;

public interface BaggageRepository extends CrudRepository<Baggage, Long> {
}
