package com.kodilla.nbpapiservice.client;

import com.kodilla.nbpapiservice.config.NBPConfig;
import com.kodilla.nbpapiservice.dto.NBPRateDTO;
import com.kodilla.nbpapiservice.dto.NBPTableDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class NBPClientTest {

    @Mock
    private RestTemplate restTemplate;

    @Mock
    private NBPConfig nbpConfig;

    @InjectMocks
    private NBPClient nbpClient;

    private NBPTableDTO[] nbpTableDTOs;
    private final LocalDate effectiveDate = LocalDate.of(2025, 8, 18);

    @BeforeEach
    void setUp() {
        when(nbpConfig.getNbpApiEndpoint()).thenReturn("https://api.nbp.pl/api");

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

        nbpTableDTOs = new NBPTableDTO[]{nbpTableDTO};
    }

    @Test
    void shouldGetExchangeRates() {
        // Given
        when(restTemplate.getForObject(any(), eq(NBPTableDTO[].class))).thenReturn(nbpTableDTOs);

        // When
        List<NBPTableDTO> result = nbpClient.getExchangeRates();
        NBPTableDTO nbpTableDTO = result.isEmpty() ? null : result.getFirst();

        // Then
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("A", nbpTableDTO.getTableName());
        assertEquals("123/A/NBP/2025", nbpTableDTO.getNumber());
        assertEquals(effectiveDate, nbpTableDTO.getEffectiveDate());
        assertEquals(1, nbpTableDTO.getCurrencies().size());
        assertEquals("US Dollar", nbpTableDTO.getCurrencies().getFirst().getCurrencyName());
        assertEquals("USD", nbpTableDTO.getCurrencies().getFirst().getCurrencyCode());
        assertEquals(new BigDecimal("4.0123"), nbpTableDTO.getCurrencies().getFirst().getValue());
    }

    @Test
    void shouldReturnEmptyListWhenRestTemplateThrowsException() {
        // Given
        when(restTemplate.getForObject(any(), eq(NBPTableDTO[].class))).thenThrow(new RestClientException("Error"));

        // When
        List<NBPTableDTO> result = nbpClient.getExchangeRates();

        // Then
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    void shouldReturnEmptyListWhenResponseIsNull() {
        // Given
        when(restTemplate.getForObject(any(), eq(NBPTableDTO[].class))).thenReturn(null);

        // When
        List<NBPTableDTO> result = nbpClient.getExchangeRates();

        // Then
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }
}