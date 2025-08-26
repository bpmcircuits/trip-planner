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
    @Column(name = "account_type", nullable = false)
    private AccountType accountType;

    @Setter
    @Column(name = "login", unique = true, nullable = false)
    private String login;

    @Setter
    @Column(name = "first_name")
    private String firstName;

    @Setter
    @Column(name = "last_name")
    private String lastName;

    @Setter
    @Column(name = "password_hash", nullable = false)
    private String passwordHash;

    @Setter
    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Setter
    @Column(name = "token", nullable = false)
    private String token;

    @Setter
    @Column(name = "token_created_at", nullable = false)
    private LocalDateTime tokenCreatedAt;

    @Setter
    @Column(name = "token_expires_at", nullable = false)
    private LocalDateTime tokenExpiresAt;
}
