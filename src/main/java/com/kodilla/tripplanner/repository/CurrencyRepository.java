package com.kodilla.tripplanner.repository;

import com.kodilla.tripplanner.domain.Currency;
import org.springframework.data.repository.CrudRepository;

public interface CurrencyRepository extends CrudRepository<Currency, Long> {
}
