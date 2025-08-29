package com.kodilla.nbpapiservice.mapper;

import com.kodilla.nbpapiservice.domain.Currency;
import com.kodilla.nbpapiservice.dto.NBPRateDTO;
import com.kodilla.nbpapiservice.dto.NBPTableDTO;
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
