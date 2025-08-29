package com.kodilla.userapiservice.dto;

public record UserFormDTO(
        String login,
        String firstName,
        String lastName,
        String email,
        String password) {
}
