package com.kodilla.tripplanner.domain;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Table(name = "hotel")
public class Hotel {

    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "country", nullable = false)
    private String country;

    @Column(name = "city", nullable = false)
    private String city;

    @Setter
    @Column(name = "check_in", nullable = false)
    private LocalDate checkInDate;

    @Setter
    @Column(name = "check_out", nullable = false)
    private LocalDate checkOutDate;

    @Setter
    @Column(name = "price", nullable = false)
    private BigDecimal price;
}
