package com.kodilla.nbpapiservice.scheduler;

import com.kodilla.nbpapiservice.dto.NBPRateDTO;
import com.kodilla.nbpapiservice.dto.NBPTableDTO;
import com.kodilla.nbpapiservice.service.CurrencyService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CurrencySchedulerTest {

    @Mock
    private CurrencyService currencyService;

    @InjectMocks
    private CurrencyScheduler currencyScheduler;

    @Test
    void shouldUpdateExchangeRates() {
        // Given
        LocalDate effectiveDate = LocalDate.of(2025, 8, 18);
        
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
        
        when(currencyService.updateExchangeRates()).thenReturn(nbpTableDTO);

        // When
        currencyScheduler.updateExchangeRates();

        // Then
        verify(currencyService, times(1)).updateExchangeRates();
    }
}