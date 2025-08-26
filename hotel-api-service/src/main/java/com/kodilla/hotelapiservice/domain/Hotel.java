package com.kodilla.tripplannerhotelapi.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

@Entity
@Table(name = "hotel")
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

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Hotel hotel = (Hotel) o;
        return Double.compare(reviewScore, hotel.reviewScore) == 0
                && reviewCount == hotel.reviewCount
                && Objects.equals(id, hotel.id)
                && Objects.equals(name, hotel.name)
                && Objects.equals(countryCode, hotel.countryCode)
                && Objects.equals(city, hotel.city)
                && Objects.equals(price, hotel.price)
                && Objects.equals(currency, hotel.currency)
                && Objects.equals(checkInDate, hotel.checkInDate)
                && Objects.equals(checkOutDate, hotel.checkOutDate)
                && Objects.equals(reviewScoreWord, hotel.reviewScoreWord);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, countryCode, city, price,
                currency, checkInDate, checkOutDate, reviewScore, reviewScoreWord, reviewCount);
    }
}
