package com.kodilla.flightapiservice.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "traveler_price")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TravelerPrice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne
    @JoinColumn(name = "flight_id")
    private Flight flight;
    
    @Column(name = "traveler_type")
    private String travelerType; // "ADULT", "CHILD", "INFANT"
    
    @Column(name = "currency")
    private String currency;
    
    @Column(name = "price")
    private String price;
}