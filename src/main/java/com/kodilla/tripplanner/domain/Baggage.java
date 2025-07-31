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
@Table(name = "baggage")
public class Baggage {

    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "baggage_type", nullable = false)
    private BaggageType baggageType;

    @Column(name = "weight", nullable = false)
    private BigDecimal weight;

    @ManyToOne
    @JoinColumn(name = "traveler_id", nullable = false)
    private Traveler traveler;
}
