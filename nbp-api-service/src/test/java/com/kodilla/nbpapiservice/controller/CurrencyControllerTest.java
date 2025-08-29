package com.kodilla.nbpapiservice.controller;

import com.kodilla.nbpapiservice.dto.CurrencyConversionDTO;
import com.kodilla.nbpapiservice.dto.NBPRateDTO;
import com.kodilla.nbpapiservice.dto.NBPTableDTO;
import com.kodilla.nbpapiservice.service.CurrencyService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class CurrencyControllerTest {

    private MockMvc mockMvc;

    @Mock
    private CurrencyService currencyService;

    @InjectMocks
    private CurrencyController currencyController;

    private NBPTableDTO nbpTableDTO;
    private NBPRateDTO nbpRateDTO;
    private CurrencyConversionDTO currencyConversionDTO;
    private final LocalDate effectiveDate = LocalDate.of(2025, 8, 18);

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(currencyController)
                .build();

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

        currencyConversionDTO = new CurrencyConversionDTO(
                "PLN",
                "USD",
                new BigDecimal("0.2492"),
                new BigDecimal("100.00"),
                new BigDecimal("24.92"),
                effectiveDate
        );
    }

    @Test
    void shouldGetAvailableCurrencies() throws Exception {
        // Given
        List<NBPTableDTO> tables = List.of(nbpTableDTO);
        when(currencyService.getAvailableCurrencies()).thenReturn(tables);

        // When & Then
        mockMvc.perform(get("/api/v1/currency")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].table", is("A")))
                .andExpect(jsonPath("$[0].no", is("123/A/NBP/2025")))
                .andExpect(jsonPath("$[0].rates", hasSize(1)))
                .andExpect(jsonPath("$[0].rates[0].currency", is("US Dollar")))
                .andExpect(jsonPath("$[0].rates[0].code", is("USD")));

        verify(currencyService, times(1)).getAvailableCurrencies();
    }

    @Test
    void shouldGetAvailableCurrenciesAndSave() throws Exception {
        // Given
        when(currencyService.updateExchangeRates()).thenReturn(nbpTableDTO);

        // When & Then
        mockMvc.perform(get("/api/v1/currency/save")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.table", is("A")))
                .andExpect(jsonPath("$.no", is("123/A/NBP/2025")))
                .andExpect(jsonPath("$.rates", hasSize(1)))
                .andExpect(jsonPath("$.rates[0].currency", is("US Dollar")))
                .andExpect(jsonPath("$.rates[0].code", is("USD")));

        verify(currencyService, times(1)).updateExchangeRates();
    }

    @Test
    void shouldGetExchangeRate() throws Exception {
        // Given
        when(currencyService.getExchangedAmount(anyString(), anyString(), any(BigDecimal.class)))
                .thenReturn(currencyConversionDTO);

        // When & Then
        mockMvc.perform(get("/api/v1/currency/exchange")
                        .param("from", "PLN")
                        .param("to", "USD")
                        .param("amount", "100.00")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.sourceCurrency", is("PLN")))
                .andExpect(jsonPath("$.targetCurrency", is("USD")))
                .andExpect(jsonPath("$.originalAmount", is(100.00)))
                .andExpect(jsonPath("$.convertedAmount", is(24.92)));

        verify(currencyService, times(1)).getExchangedAmount("PLN", "USD", new BigDecimal("100.00"));
    }
}