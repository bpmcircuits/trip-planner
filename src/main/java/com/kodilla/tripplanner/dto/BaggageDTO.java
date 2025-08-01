package com.kodilla.tripplanner.dto;

import com.kodilla.tripplanner.domain.BaggageType;

import java.math.BigDecimal;

public record BaggageDTO(Long id,
                         BaggageType type,
                         BigDecimal weight,
                         Long travelerId) {}
