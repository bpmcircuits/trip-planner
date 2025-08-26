package com.kodilla.tripplanner.repository;

import com.kodilla.tripplanner.domain.Traveler;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface TravelerRepository extends CrudRepository<Traveler, Long> {

    List<Traveler> findAll();
}
