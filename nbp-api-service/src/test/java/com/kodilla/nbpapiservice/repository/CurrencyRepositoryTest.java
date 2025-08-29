package com.kodilla.nbpapiservice.repository;

import com.kodilla.nbpapiservice.domain.Currency;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
class CurrencyRepositoryTest {

    @Autowired
    private CurrencyRepository currencyRepository;

    @Test
    void shouldSaveAndFindCurrency() {
        // Given
        Currency currency = Currency.builder()
                .currencyName("US Dollar")
                .currencyCode("USD")
                .value(new BigDecimal("4.0123"))
                .lastUpdated(LocalDate.now())
                .build();

        // When
        Currency savedCurrency = currencyRepository.save(currency);
        Optional<Currency> foundCurrency = currencyRepository.findById(savedCurrency.getId());

        // Then
        assertTrue(foundCurrency.isPresent());
        assertEquals("US Dollar", foundCurrency.get().getCurrencyName());
        assertEquals("USD", foundCurrency.get().getCurrencyCode());
        assertEquals(new BigDecimal("4.0123"), foundCurrency.get().getValue());

        // Cleanup
        currencyRepository.delete(savedCurrency);
    }
}