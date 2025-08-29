package com.kodilla.tripapiservice.repository;

import com.kodilla.tripapiservice.domain.Traveler;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TravelerRepository extends JpaRepository<Traveler, Long> {
}
