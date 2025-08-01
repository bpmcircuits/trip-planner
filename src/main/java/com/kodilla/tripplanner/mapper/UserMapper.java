package com.kodilla.tripplanner.mapper;

import com.kodilla.tripplanner.domain.User;
import com.kodilla.tripplanner.dto.UserDTO;
import org.springframework.stereotype.Service;

@Service
public class UserMapper {

    public UserDTO toUserDTO(User user) {
        if (user == null) {
            return null;
        }
        return new UserDTO(
                user.getId(),
                user.getAccountType(),
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                user.getToken(),
                user.getTokenCreatedAt(),
                user.getTokenExpiresAt()
        );
    }

    public User toUser(UserDTO userDTO) {
        if (userDTO == null) {
            return null;
        }
        return User.builder()
                .id(userDTO.id())
                .accountType(userDTO.accountType())
                .firstName(userDTO.firstName())
                .lastName(userDTO.lastName())
                .email(userDTO.email())
                .token(userDTO.token())
                .tokenCreatedAt(userDTO.tokenCreatedAt())
                .tokenExpiresAt(userDTO.tokenExpiresAt())
                .build();
    }
}
