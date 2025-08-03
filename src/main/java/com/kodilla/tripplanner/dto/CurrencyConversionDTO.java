package com.kodilla.tripplanner.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public record CurrencyConversionDTO(
        String sourceCurrency,
        String targetCurrency,
        BigDecimal exchangeRate,
        BigDecimal originalAmount,
        BigDecimal convertedAmount,
        LocalDate rateDate) {
}
