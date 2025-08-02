package com.kodilla.tripplanner.mapper;

import com.kodilla.tripplanner.domain.User;
import com.kodilla.tripplanner.dto.UserDTO;
import com.kodilla.tripplanner.dto.UserFormDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserMapper {

    public UserDTO toUserDTO(User user) {
        if (user == null) {
            return null;
        }
        return new UserDTO(
                user.getId(),
                user.getAccountType(),
                user.getLogin(),
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                user.getToken(),
                user.getTokenCreatedAt(),
                user.getTokenExpiresAt()
        );
    }

    public List<UserDTO> toUserDTOList(List<User> users) {
        if (users == null || users.isEmpty()) {
            return List.of();
        }
        return users.stream()
                .map(this::toUserDTO)
                .toList();
    }

    public User toUser(UserDTO userDTO) {
        if (userDTO == null) {
            return null;
        }
        return User.builder()
                .id(userDTO.id())
                .accountType(userDTO.accountType())
                .login(userDTO.login())
                .firstName(userDTO.firstName())
                .lastName(userDTO.lastName())
                .email(userDTO.email())
                .token(userDTO.token())
                .tokenCreatedAt(userDTO.tokenCreatedAt())
                .tokenExpiresAt(userDTO.tokenExpiresAt())
                .build();
    }

    public User toNewUser(UserFormDTO userDTO) {
        if (userDTO == null) {
            return null;
        }
        return User.builder()
                .login(userDTO.login())
                .firstName(userDTO.firstName())
                .lastName(userDTO.lastName())
                .passwordHash(userDTO.password())
                .email(userDTO.email())
                .build();
    }
}
