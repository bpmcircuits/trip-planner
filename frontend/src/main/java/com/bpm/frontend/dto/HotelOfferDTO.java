package com.bpm.frontend.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public record HotelOfferDTO(String name,
                            String location,
                            LocalDate startDate,
                            LocalDate endDate,
                            BigDecimal price,
                            double reviewScore,
                            String reviewScoreWord) {
}
