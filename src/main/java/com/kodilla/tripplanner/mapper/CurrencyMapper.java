package com.kodilla.tripplanner.mapper;

import com.kodilla.tripplanner.domain.Currency;
import com.kodilla.tripplanner.dto.NBPRateDTO;
import com.kodilla.tripplanner.dto.NBPTableDTO;
import org.springframework.stereotype.Service;

@Service
public class CurrencyMapper {

    public Currency mapToCurrency(NBPTableDTO nbpTableDTO, NBPRateDTO nbpRateDTO) {
        return Currency.builder()
                .currencyName(nbpRateDTO.getCurrencyName())
                .currencyCode(nbpRateDTO.getCurrencyCode())
                .value(nbpRateDTO.getValue())
                .lastUpdated(nbpTableDTO.getEffectiveDate())
                .build();
    }
}
