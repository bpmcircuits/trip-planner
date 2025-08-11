package com.kodilla.tripplanner.service;

import com.kodilla.tripplanner.domain.Currency;
import com.kodilla.tripplanner.dto.CurrencyConversionDTO;
import com.kodilla.tripplanner.mapper.CurrencyMapper;
import com.kodilla.tripplanner.nbp.client.NBPClient;
import com.kodilla.tripplanner.nbp.dto.NBPRateDTO;
import com.kodilla.tripplanner.nbp.dto.NBPTableDTO;
import com.kodilla.tripplanner.repository.CurrencyRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CurrencyServiceTest {

    @Mock
    private NBPClient nbpClient;

    @Mock
    private CurrencyRepository currencyRepository;

    @Mock
    private CurrencyMapper currencyMapper;

    @InjectMocks
    private CurrencyService currencyService;

    private NBPTableDTO nbpTableDTO;
    private NBPRateDTO nbpRateDTO1;
    private NBPRateDTO nbpRateDTO2;
    private Currency currency1;
    private Currency currency2;
    private LocalDate today;

    @BeforeEach
    void setUp() {
        today = LocalDate.now();
        
        nbpRateDTO1 = new NBPRateDTO("USD", "dolar amerykański", new BigDecimal("3.9012"));
        nbpRateDTO2 = new NBPRateDTO("EUR", "euro", new BigDecimal("4.3215"));
        
        nbpTableDTO = new NBPTableDTO("A", "A001/25", today, Arrays.asList(nbpRateDTO1, nbpRateDTO2));
        
        currency1 = Currency.builder()
                .id(1L)
                .currencyCode("USD")
                .currencyName("dolar amerykański")
                .value(new BigDecimal("3.9012"))
                .lastUpdated(today)
                .build();
        
        currency2 = Currency.builder()
                .id(2L)
                .currencyCode("EUR")
                .currencyName("euro")
                .value(new BigDecimal("4.3215"))
                .lastUpdated(today)
                .build();
    }

    @Test
    void testGetAvailableCurrencies() {
        // Given
        when(nbpClient.getExchangeRates()).thenReturn(Collections.singletonList(nbpTableDTO));

        // When
        List<NBPTableDTO> result = currencyService.getAvailableCurrencies();

        // Then
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("A", result.get(0).getTableName());
        assertEquals(today, result.get(0).getEffectiveDate());
        assertEquals(2, result.get(0).getCurrencies().size());
        verify(nbpClient, times(1)).getExchangeRates();
    }

    @Test
    void testUpdateExchangeRatesWhenNotActual() {
        // Given
        when(nbpClient.getExchangeRates()).thenReturn(Collections.singletonList(nbpTableDTO));
        when(currencyRepository.findByLastUpdated(today)).thenReturn(Collections.emptyList());
        when(currencyMapper.mapToCurrency(any(NBPTableDTO.class), any(NBPRateDTO.class)))
                .thenReturn(currency1)
                .thenReturn(currency2);
        when(currencyRepository.save(any(Currency.class))).thenReturn(currency1).thenReturn(currency2);

        // When
        currencyService.updateExchangeRates();

        // Then
        verify(nbpClient, times(1)).getExchangeRates();
        verify(currencyRepository, times(1)).findByLastUpdated(today);
        verify(currencyMapper, times(2)).mapToCurrency(any(NBPTableDTO.class), any(NBPRateDTO.class));
        verify(currencyRepository, times(2)).save(any(Currency.class));
    }

    @Test
    void testUpdateExchangeRatesWhenAlreadyActual() {
        // Given
        when(nbpClient.getExchangeRates()).thenReturn(Collections.singletonList(nbpTableDTO));
        when(currencyRepository.findByLastUpdated(today)).thenReturn(Collections.singletonList(currency1));

        // When
        currencyService.updateExchangeRates();

        // Then
        verify(nbpClient, times(1)).getExchangeRates();
        verify(currencyRepository, times(1)).findByLastUpdated(today);
        verify(currencyMapper, never()).mapToCurrency(any(NBPTableDTO.class), any(NBPRateDTO.class));
        verify(currencyRepository, never()).save(any(Currency.class));
    }

    @Test
    void testGetExchangedAmountWithForeignCurrencies() {
        // Given
        String fromCurrency = "USD";
        String toCurrency = "EUR";
        BigDecimal amount = new BigDecimal("100");
        
        when(currencyRepository.findByCurrencyCode(fromCurrency))
                .thenReturn(Collections.singletonList(currency1));
        when(currencyRepository.findByCurrencyCode(toCurrency))
                .thenReturn(Collections.singletonList(currency2));

        // Expected calculation:
        // USD rate = 3.9012, EUR rate = 4.3215
        // 100 USD = 100 * 3.9012 / 4.3215 = 90.28 EUR
        BigDecimal expectedResult = amount
                .multiply(currency1.getValue())
                .divide(currency2.getValue(), 2, RoundingMode.CEILING);
        BigDecimal expectedRate = currency1.getValue()
                .divide(currency2.getValue(), 4, RoundingMode.CEILING);

        // When
        CurrencyConversionDTO result = currencyService.getExchangedAmount(fromCurrency, toCurrency, amount);

        // Then
        assertNotNull(result);
        assertEquals(fromCurrency, result.sourceCurrency());
        assertEquals(toCurrency, result.targetCurrency());
        assertEquals(expectedRate, result.exchangeRate());
        assertEquals(amount, result.originalAmount());
        assertEquals(expectedResult, result.convertedAmount());
        assertEquals(today, result.rateDate());
        
        verify(currencyRepository, times(1)).findByCurrencyCode(fromCurrency);
        verify(currencyRepository, times(1)).findByCurrencyCode(toCurrency);
    }

    @Test
    void testGetExchangedAmountWithPLN() {
        // Given
        String fromCurrency = "PLN";
        String toCurrency = "USD";
        BigDecimal amount = new BigDecimal("100");
        
        when(currencyRepository.findByCurrencyCode(toCurrency))
                .thenReturn(Collections.singletonList(currency1));

        // Expected calculation:
        // PLN rate = 1, USD rate = 3.9012
        // 100 PLN = 100 * 1 / 3.9012 = 25.63 USD
        BigDecimal expectedResult = amount
                .multiply(BigDecimal.ONE)
                .divide(currency1.getValue(), 2, RoundingMode.CEILING);
        BigDecimal expectedRate = BigDecimal.ONE
                .divide(currency1.getValue(), 4, RoundingMode.CEILING);

        // When
        CurrencyConversionDTO result = currencyService.getExchangedAmount(fromCurrency, toCurrency, amount);

        // Then
        assertNotNull(result);
        assertEquals(fromCurrency, result.sourceCurrency());
        assertEquals(toCurrency, result.targetCurrency());
        assertEquals(expectedRate, result.exchangeRate());
        assertEquals(amount, result.originalAmount());
        assertEquals(expectedResult, result.convertedAmount());
        assertEquals(today, result.rateDate());
        
        verify(currencyRepository, never()).findByCurrencyCode(fromCurrency);
        verify(currencyRepository, times(1)).findByCurrencyCode(toCurrency);
    }

    @Test
    void testGetExchangedAmountCurrencyNotFound() {
        // Given
        String fromCurrency = "USD";
        String toCurrency = "GBP"; // Not in our mocked data
        BigDecimal amount = new BigDecimal("100");
        
        when(currencyRepository.findByCurrencyCode(fromCurrency))
                .thenReturn(Collections.singletonList(currency1));
        when(currencyRepository.findByCurrencyCode(toCurrency))
                .thenReturn(Collections.emptyList());

        // When & Then
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            currencyService.getExchangedAmount(fromCurrency, toCurrency, amount);
        });
        
        assertEquals("Currency code " + toCurrency + " not found", exception.getMessage());
        verify(currencyRepository, times(1)).findByCurrencyCode(fromCurrency);
        verify(currencyRepository, times(1)).findByCurrencyCode(toCurrency);
    }
}