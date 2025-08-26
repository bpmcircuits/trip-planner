package com.kodilla.tripplanner.dto;

import com.kodilla.tripplanner.domain.AccountType;

import java.time.LocalDateTime;

public record UserDTO(Long id,
                      AccountType accountType,
                      String login,
                      String firstName,
                      String lastName,
                      String email,
                      String token,
                      LocalDateTime tokenCreatedAt,
                      LocalDateTime tokenExpiresAt) {}
