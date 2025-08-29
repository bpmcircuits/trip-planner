package com.kodilla.nbpapiservice.mapper;

import com.kodilla.nbpapiservice.domain.Currency;
import com.kodilla.nbpapiservice.dto.NBPRateDTO;
import com.kodilla.nbpapiservice.dto.NBPTableDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CurrencyMapperTest {

    @Autowired
    CurrencyMapper currencyMapper;

    private final LocalDate effectiveDate = LocalDate.of(2025, 8, 18);

    @Test
    void shouldMapToCurrency() {
        // Given
        NBPRateDTO nbpRateDTO = new NBPRateDTO(
                "US Dollar",
                "USD",
                new BigDecimal("4.0123")
        );

        List<NBPRateDTO> rates = new ArrayList<>();
        rates.add(nbpRateDTO);

        NBPTableDTO nbpTableDTO = new NBPTableDTO(
                "A",
                "123/A/NBP/2025",
                effectiveDate,
                rates
        );

        // When
        Currency result = currencyMapper.mapToCurrency(nbpTableDTO, nbpRateDTO);

        // Then
        assertNotNull(result);
        assertNull(result.getId()); // ID should be null as it's not set by the mapper
        assertEquals("US Dollar", result.getCurrencyName());
        assertEquals("USD", result.getCurrencyCode());
        assertEquals(new BigDecimal("4.0123"), result.getValue());
        assertEquals(effectiveDate, result.getLastUpdated());
    }

    @Test
    void shouldMapToCurrencyWithDifferentValues() {
        // Given
        NBPRateDTO nbpRateDTO = new NBPRateDTO(
                "Euro",
                "EUR",
                new BigDecimal("4.3210")
        );

        List<NBPRateDTO> rates = new ArrayList<>();
        rates.add(nbpRateDTO);

        NBPTableDTO nbpTableDTO = new NBPTableDTO(
                "A",
                "123/A/NBP/2025",
                effectiveDate,
                rates
        );

        // When
        Currency result = currencyMapper.mapToCurrency(nbpTableDTO, nbpRateDTO);

        // Then
        assertNotNull(result);
        assertNull(result.getId()); // ID should be null as it's not set by the mapper
        assertEquals("Euro", result.getCurrencyName());
        assertEquals("EUR", result.getCurrencyCode());
        assertEquals(new BigDecimal("4.3210"), result.getValue());
        assertEquals(effectiveDate, result.getLastUpdated());
    }
}