package com.kodilla.tripplanner.dto;

public record UserDTO(Long id,
                      String accountType,
                      String firstName,
                      String lastName,
                      String email,
                      String token,
                      String tokenCreatedAt,
                      String tokenExpiresAt) {}
