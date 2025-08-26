package com.kodilla.tripplanner.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Table(name = "traveler")
public class Traveler {

    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(name = "gender", nullable = false)
    private Gender gender;

    @Column(name = "person_type", nullable = false)
    private PersonType personType;

    @Column(name = "age", nullable = false)
    private int age;

    @Column(name = "baggage")
    private BaggageType baggage;

    @ManyToOne
    @JoinColumn(name = "trip_id")
    private Trip trip;
}
