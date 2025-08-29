package com.kodilla.flightapiservice.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UuidGenerator;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "flight")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Flight {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private boolean oneWay;

    @Column(name = "search_id", unique = true)
    private UUID searchId;
    
    @Column(name = "currency")
    private String currency;
    
    @Column(name = "total_price")
    private String totalPrice;
    
    @OneToMany(mappedBy = "flight", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<FlightSegment> outboundSegments = new ArrayList<>();

    @OneToMany(mappedBy = "flight", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<FlightSegment> inboundSegments = new ArrayList<>();
    
    @OneToMany(mappedBy = "flight", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<TravelerPrice> travelerPrices = new ArrayList<>();
    
    @Column(name = "outbound_duration_iso")
    private String outboundDurationIso;
    
    @Column(name = "outbound_duration_minutes")
    private Integer outboundDurationMinutes;
    
    @Column(name = "inbound_duration_iso")
    private String inboundDurationIso;
    
    @Column(name = "inbound_duration_minutes")
    private Integer inboundDurationMinutes;
    
    @Column(name = "last_updated")
    private LocalDateTime lastUpdated;
    
    public void addOutboundSegment(FlightSegment segment) {
        outboundSegments.add(segment);
        segment.setFlight(this);
        segment.setDirection("outbound");
    }
    
    public void addInboundSegment(FlightSegment segment) {
        inboundSegments.add(segment);
        segment.setFlight(this);
        segment.setDirection("inbound");
    }
    
    public void addTravelerPrice(TravelerPrice travelerPrice) {
        travelerPrices.add(travelerPrice);
        travelerPrice.setFlight(this);
    }
}
