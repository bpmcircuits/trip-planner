package com.kodilla.nbpapiservice.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Table(name = "currency")
public class Currency {

    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "currency_name", nullable = false)
    private String currencyName;

    @Column(name = "currency_code", nullable = false)
    private String currencyCode;

    @Column(name = "value", nullable = false)
    private BigDecimal value;

    @Column(name = "last_updated", nullable = false)
    private LocalDate lastUpdated;
}
