package com.kodilla.tripapiservice.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Table(name = "trip")
public class Trip {

    @Id
    @GeneratedValue
    private Long id;

    @Setter
    @Column(name = "name", nullable = false)
    private String name;

    @Setter
    @Column(name = "description")
    private String description;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "flight_id")
    private Long flightId;

    @Column(name = "hotel_id")
    private Long hotelId;

    @OneToMany(mappedBy = "trip", cascade = CascadeType.ALL)
    private List<Traveler> travelers;
}
