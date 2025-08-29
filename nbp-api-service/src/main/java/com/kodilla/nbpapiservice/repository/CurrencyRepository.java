package com.kodilla.nbpapiservice.repository;

import com.kodilla.nbpapiservice.domain.Currency;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface CurrencyRepository extends JpaRepository<Currency, Long> {
    List<Currency> findByLastUpdated(LocalDate lastUpdated);
    List<Currency> findByCurrencyCode(String currencyCode);
}
