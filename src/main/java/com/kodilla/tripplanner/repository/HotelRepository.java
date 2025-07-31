package com.kodilla.tripplanner.repository;

import com.kodilla.tripplanner.domain.Hotel;
import org.springframework.data.repository.CrudRepository;

public interface HotelRepository extends CrudRepository<Hotel, Long> {
}
