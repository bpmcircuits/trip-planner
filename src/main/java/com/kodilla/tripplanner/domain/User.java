package com.kodilla.tripplanner.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue
    private Long id;

    @Setter
    @Column(name = "first_name")
    private String firstName;

    @Setter
    @Column(name = "last_name")
    private String lastName;

    @Column(name = "password_hash")
    private String passwordHash;

    @Setter
    @Column(name = "email")
    private String email;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Setter
    @Column(name = "token")
    private String token;

    @Setter
    @Column(name = "token_created_at")
    private LocalDateTime tokenCreatedAt;

    @Setter
    @Column(name = "token_expires_at")
    private LocalDateTime tokenExpiresAt;
}
