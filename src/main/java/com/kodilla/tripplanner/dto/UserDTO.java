package com.kodilla.tripplanner.dto;

import java.time.LocalDateTime;

public record UserDTO(Long id,
                      String accountType,
                      String firstName,
                      String lastName,
                      String email,
                      String token,
                      LocalDateTime tokenCreatedAt,
                      LocalDateTime tokenExpiresAt) {}
