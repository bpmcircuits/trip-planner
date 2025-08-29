package com.kodilla.hotelapiservice.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "hotel")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Hotel {

    @Id
    private Long id;
    @Column(name = "hotel_name")
    private String name;
    @Column(name = "country_code")
    private String countryCode;
    @Column(name = "city")
    private String city;
    @Column(name = "price")
    private BigDecimal price;
    @Column(name = "currency")
    private String currency;
    @Column(name = "check_in_date")
    private LocalDate checkInDate;
    @Column(name = "check_out_date")
    private LocalDate checkOutDate;
    @Column(name = "review_score")
    private double reviewScore;
    @Column(name = "review_score_word")
    private String reviewScoreWord;
    @Column(name = "review_count")
    private int reviewCount;
    @Column(name = "last_updated")
    private LocalDateTime lastUpdated;

}
