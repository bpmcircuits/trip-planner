package com.kodilla.nbpapiservice.service;

import com.kodilla.nbpapiservice.client.NBPClient;
import com.kodilla.nbpapiservice.domain.Currency;
import com.kodilla.nbpapiservice.dto.CurrencyConversionDTO;
import com.kodilla.nbpapiservice.dto.NBPRateDTO;
import com.kodilla.nbpapiservice.dto.NBPTableDTO;
import com.kodilla.nbpapiservice.exception.CurrencyNotFoundException;
import com.kodilla.nbpapiservice.mapper.CurrencyMapper;
import com.kodilla.nbpapiservice.repository.CurrencyRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.ArrayList;
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
    private NBPRateDTO nbpRateDTO;
    private Currency currency;
    private final LocalDate effectiveDate = LocalDate.of(2025, 8, 18);

    @BeforeEach
    void setUp() {
        nbpRateDTO = new NBPRateDTO(
                "US Dollar",
                "USD",
                new BigDecimal("4.0123")
        );

        List<NBPRateDTO> rates = new ArrayList<>();
        rates.add(nbpRateDTO);

        nbpTableDTO = new NBPTableDTO(
                "A",
                "123/A/NBP/2025",
                effectiveDate,
                rates
        );

        currency = Currency.builder()
                .id(1L)
                .currencyName("US Dollar")
                .currencyCode("USD")
                .value(new BigDecimal("4.0123"))
                .lastUpdated(effectiveDate)
                .build();
    }

    @Test
    void shouldGetAvailableCurrencies() {
        // Given
        List<NBPTableDTO> tables = List.of(nbpTableDTO);
        when(nbpClient.getExchangeRates()).thenReturn(tables);

        // When
        List<NBPTableDTO> result = currencyService.getAvailableCurrencies();

        // Then
        assertEquals(1, result.size());
        assertEquals(nbpTableDTO, result.getFirst());
        verify(nbpClient, times(1)).getExchangeRates();
    }

    @Test
    void shouldUpdateExchangeRatesWhenNotActual() {
        // Given
        List<NBPTableDTO> tables = List.of(nbpTableDTO);
        when(nbpClient.getExchangeRates()).thenReturn(tables);
        when(currencyRepository.findByLastUpdated(effectiveDate)).thenReturn(List.of());
        when(currencyMapper.mapToCurrency(any(NBPTableDTO.class), any(NBPRateDTO.class))).thenReturn(currency);

        // When
        NBPTableDTO result = currencyService.updateExchangeRates();

        // Then
        assertEquals(nbpTableDTO, result);
        verify(nbpClient, times(1)).getExchangeRates();
        verify(currencyRepository, times(1)).findByLastUpdated(effectiveDate);
        verify(currencyMapper, times(1)).mapToCurrency(nbpTableDTO, nbpRateDTO);
        verify(currencyRepository, times(1)).save(currency);
    }

    @Test
    void shouldNotUpdateExchangeRatesWhenAlreadyActual() {
        // Given
        List<NBPTableDTO> tables = List.of(nbpTableDTO);
        when(nbpClient.getExchangeRates()).thenReturn(tables);
        when(currencyRepository.findByLastUpdated(effectiveDate)).thenReturn(List.of(currency));

        // When
        NBPTableDTO result = currencyService.updateExchangeRates();

        // Then
        assertEquals(nbpTableDTO, result);
        verify(nbpClient, times(1)).getExchangeRates();
        verify(currencyRepository, times(1)).findByLastUpdated(effectiveDate);
        verify(currencyMapper, never()).mapToCurrency(any(), any());
        verify(currencyRepository, never()).save(any());
    }

    @Test
    void shouldGetExchangedAmountFromPLNToUSD() throws CurrencyNotFoundException {
        // Given
        String fromCurrency = "PLN";
        String toCurrency = "USD";
        BigDecimal amount = new BigDecimal("100.00");
        
        when(currencyRepository.findByCurrencyCode(toCurrency)).thenReturn(List.of(currency));

        // When
        CurrencyConversionDTO result = currencyService.getExchangedAmount(fromCurrency, toCurrency, amount);

        // Then
        assertEquals(fromCurrency, result.sourceCurrency());
        assertEquals(toCurrency, result.targetCurrency());
        assertEquals(amount, result.originalAmount());
        
        // PLN to USD conversion: 100 * 1 / 4.0123 = 24.92
        BigDecimal expectedRate = BigDecimal.ONE.divide(new BigDecimal("4.0123"), 4, RoundingMode.CEILING);
        BigDecimal expectedAmount = amount.multiply(BigDecimal.ONE).divide(new BigDecimal("4.0123"), 2, RoundingMode.CEILING);
        
        assertEquals(expectedRate, result.exchangeRate());
        assertEquals(expectedAmount, result.convertedAmount());
        assertEquals(LocalDate.now(), result.rateDate());
        
        verify(currencyRepository, times(1)).findByCurrencyCode(toCurrency);
    }

    @Test
    void shouldGetExchangedAmountFromUSDToPLN() throws CurrencyNotFoundException {
        // Given
        String fromCurrency = "USD";
        String toCurrency = "PLN";
        BigDecimal amount = new BigDecimal("100.00");
        
        when(currencyRepository.findByCurrencyCode(fromCurrency)).thenReturn(List.of(currency));

        // When
        CurrencyConversionDTO result = currencyService.getExchangedAmount(fromCurrency, toCurrency, amount);

        // Then
        assertEquals(fromCurrency, result.sourceCurrency());
        assertEquals(toCurrency, result.targetCurrency());
        assertEquals(amount, result.originalAmount());
        
        // USD to PLN conversion: 100 * 4.0123 / 1 = 401.23
        BigDecimal expectedRate = new BigDecimal("4.0123").divide(BigDecimal.ONE, 4, RoundingMode.CEILING);
        BigDecimal expectedAmount = amount.multiply(new BigDecimal("4.0123")).divide(BigDecimal.ONE, 2, RoundingMode.CEILING);
        
        assertEquals(expectedRate, result.exchangeRate());
        assertEquals(expectedAmount, result.convertedAmount());
        assertEquals(LocalDate.now(), result.rateDate());
        
        verify(currencyRepository, times(1)).findByCurrencyCode(fromCurrency);
    }

    @Test
    void shouldGetExchangedAmountBetweenTwoForeignCurrencies() throws CurrencyNotFoundException {
        // Given
        String fromCurrency = "USD";
        String toCurrency = "EUR";
        BigDecimal amount = new BigDecimal("100.00");
        
        Currency eurCurrency = Currency.builder()
                .id(2L)
                .currencyName("Euro")
                .currencyCode("EUR")
                .value(new BigDecimal("4.3210"))
                .lastUpdated(effectiveDate)
                .build();
        
        when(currencyRepository.findByCurrencyCode(fromCurrency)).thenReturn(List.of(currency));
        when(currencyRepository.findByCurrencyCode(toCurrency)).thenReturn(List.of(eurCurrency));

        // When
        CurrencyConversionDTO result = currencyService.getExchangedAmount(fromCurrency, toCurrency, amount);

        // Then
        assertEquals(fromCurrency, result.sourceCurrency());
        assertEquals(toCurrency, result.targetCurrency());
        assertEquals(amount, result.originalAmount());
        
        // USD to EUR conversion: 100 * 4.0123 / 4.3210 = 92.86
        BigDecimal expectedRate = new BigDecimal("4.0123").divide(new BigDecimal("4.3210"), 4, RoundingMode.CEILING);
        BigDecimal expectedAmount = amount.multiply(new BigDecimal("4.0123")).divide(new BigDecimal("4.3210"), 2, RoundingMode.CEILING);
        
        assertEquals(expectedRate, result.exchangeRate());
        assertEquals(expectedAmount, result.convertedAmount());
        assertEquals(LocalDate.now(), result.rateDate());
        
        verify(currencyRepository, times(1)).findByCurrencyCode(fromCurrency);
        verify(currencyRepository, times(1)).findByCurrencyCode(toCurrency);
    }

    @Test
    void shouldThrowExceptionWhenCurrencyNotFound() {
        // Given
        String fromCurrency = "XYZ";
        String toCurrency = "PLN";
        BigDecimal amount = new BigDecimal("100.00");
        
        when(currencyRepository.findByCurrencyCode(fromCurrency)).thenReturn(List.of());

        // When & Then
        CurrencyNotFoundException exception = assertThrows(
                CurrencyNotFoundException.class,
                () -> currencyService.getExchangedAmount(fromCurrency, toCurrency, amount)
        );
        
        assertEquals("Currency not found: XYZ", exception.getMessage());
        verify(currencyRepository, times(1)).findByCurrencyCode(fromCurrency);
    }
}