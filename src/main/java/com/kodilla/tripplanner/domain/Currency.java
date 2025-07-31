package com.kodilla.tripplanner.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

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

    @Column(name = "currency_code", nullable = false, unique = true)
    private String currencyCode;

    @Column(name = "currency_name", nullable = false)
    private BigDecimal value;
}
