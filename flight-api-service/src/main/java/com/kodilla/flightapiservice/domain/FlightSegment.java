package com.kodilla.flightapiservice.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "flight_segment")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FlightSegment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne
    @JoinColumn(name = "flight_id")
    private Flight flight;
    
    @Column(name = "direction")
    private String direction; // "outbound" or "inbound"
    
    @Column(name = "from_iata")
    private String fromIata;
    
    @Column(name = "to_iata")
    private String toIata;
    
    @Column(name = "carrier_code")
    private String carrierCode;
    
    @Column(name = "departure_at")
    private String departureAt;
    
    @Column(name = "arrival_at")
    private String arrivalAt;
    
    @Column(name = "segment_order")
    private Integer segmentOrder;
}