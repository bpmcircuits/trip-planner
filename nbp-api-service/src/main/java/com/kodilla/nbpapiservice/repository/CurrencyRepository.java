package com.kodilla.tripplanner.repository;

import com.kodilla.tripplanner.domain.Currency;
import org.springframework.data.repository.CrudRepository;

import java.time.LocalDate;
import java.util.List;

public interface CurrencyRepository extends CrudRepository<Currency, Long> {
    List<Currency> findByLastUpdated(LocalDate lastUpdated);
    List<Currency> findByCurrencyCode(String currencyCode);
}
