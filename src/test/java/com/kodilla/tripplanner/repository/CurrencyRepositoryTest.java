package com.kodilla.tripplanner.repository;

import com.kodilla.tripplanner.domain.Currency;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class CurrencyRepositoryTest {

    @Autowired
    private CurrencyRepository currencyRepository;

    @Test
    void shouldSaveAndFindCurrencyCode() {
        Currency currency = Currency.builder()
                .currencyName("US Dollar")
                .currencyCode("USD")
                .value(BigDecimal.valueOf(4.20))
                .lastUpdated(LocalDate.now())
                .build();
        Currency saved = null;
        try {
            saved = currencyRepository.save(currency);
            Optional<Currency> found = currencyRepository.findById(saved.getId());
            assertThat(found).isPresent();
            assertThat(found.get().getCurrencyCode()).isEqualTo("USD");
        } finally {
            if (saved != null && saved.getId() != null) currencyRepository.deleteById(saved.getId());
        }
    }

    @Test
    void shouldSaveAndFindValue() {
        BigDecimal value = BigDecimal.valueOf(3.75);
        Currency currency = Currency.builder()
                .currencyName("Euro")
                .currencyCode("EUR")
                .value(value)
                .lastUpdated(LocalDate.now())
                .build();
        Currency saved = null;
        try {
            saved = currencyRepository.save(currency);
            Optional<Currency> found = currencyRepository.findById(saved.getId());
            assertThat(found).isPresent();
            assertThat(found.get().getValue()).isEqualByComparingTo(value);
        } finally {
            if (saved != null && saved.getId() != null) currencyRepository.deleteById(saved.getId());
        }
    }
}