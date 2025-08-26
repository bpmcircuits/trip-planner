package com.kodilla.tripplanner.dto;

public record UserFormDTO(
        String login,
        String firstName,
        String lastName,
        String email,
        String password) {
}
