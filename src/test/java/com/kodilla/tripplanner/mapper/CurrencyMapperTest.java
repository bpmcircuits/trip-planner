package com.kodilla.tripplanner.mapper;

import com.kodilla.tripplanner.domain.Currency;
import com.kodilla.tripplanner.dto.CurrencyDTO;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

class CurrencyMapperTest {

    private final CurrencyMapper mapper = new CurrencyMapper();

    @Test
    void shouldMapToCurrencyDTO() {
        Currency currency = Currency.builder().id(1L).currencyCode("PLN").value(BigDecimal.valueOf(4.5)).build();

        CurrencyDTO dto = mapper.toCurrencyDTO(currency);

        assertThat(dto).isNotNull();
        assertThat(dto.id()).isEqualTo(1L);
        assertThat(dto.currencyCode()).isEqualTo("PLN");
        assertThat(dto.value()).isEqualTo(BigDecimal.valueOf(4.5));
    }

    @Test
    void shouldMapToCurrency() {
        CurrencyDTO dto = new CurrencyDTO(2L, "USD", BigDecimal.valueOf(1.0));

        Currency currency = mapper.toCurrency(dto);

        assertThat(currency).isNotNull();
        assertThat(currency.getId()).isEqualTo(2L);
        assertThat(currency.getCurrencyCode()).isEqualTo("USD");
        assertThat(currency.getValue()).isEqualTo(BigDecimal.valueOf(1.0));
    }

    @Test
    void shouldReturnNullForNullInput() {
        assertThat(mapper.toCurrencyDTO(null)).isNull();
        assertThat(mapper.toCurrency(null)).isNull();
    }
}