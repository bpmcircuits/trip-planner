package com.kodilla.tripplanner.dto;

import java.math.BigDecimal;

public record CurrencyDTO(Long id,
                          String currencyCode,
                          BigDecimal value) {}
