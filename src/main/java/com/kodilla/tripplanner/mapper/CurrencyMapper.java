package com.kodilla.tripplanner.mapper;

import com.kodilla.tripplanner.domain.Currency;
import com.kodilla.tripplanner.dto.CurrencyDTO;
import org.springframework.stereotype.Service;

@Service
public class CurrencyMapper {
    public CurrencyDTO toCurrencyDTO(Currency currency) {
        if (currency == null) return null;

        return new CurrencyDTO(
                currency.getId(),
                currency.getCurrencyCode(),
                currency.getValue()
        );
    }

    public Currency toCurrency(CurrencyDTO currencyDTO) {
        if (currencyDTO == null) return null;

        return Currency.builder()
                .id(currencyDTO.id())
                .currencyCode(currencyDTO.currencyCode())
                .value(currencyDTO.value())
                .build();
    }
}
