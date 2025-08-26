package com.kodilla.tripplannerhotelapi.repository;

import com.kodilla.tripplannerhotelapi.domain.Hotel;
import org.springframework.data.repository.CrudRepository;

public interface HotelRepository extends CrudRepository<Hotel, Long> {
}
