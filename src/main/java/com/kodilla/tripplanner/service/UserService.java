package com.kodilla.tripplanner.service;

import com.kodilla.tripplanner.domain.AccountType;
import com.kodilla.tripplanner.domain.User;
import com.kodilla.tripplanner.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User getUserById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    public User createUser(User user) {

        String encodedPassword = bCryptPasswordEncoder.encode(user.getPasswordHash());
        User createdUser = User.builder()
                .login(user.getLogin())
                .accountType(AccountType.USER)
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .passwordHash(encodedPassword)
                .token(UUID.randomUUID().toString())
                .tokenCreatedAt(LocalDateTime.now())
                .tokenExpiresAt(LocalDateTime.now().plusDays(1))
                .build();
        return userRepository.save(createdUser);
    }

    public User updateUser(Long id, User user) {
        return userRepository.findById(id)
                .map(existingUser -> {
                    existingUser.setLogin(user.getLogin());
                    existingUser.setFirstName(user.getFirstName());
                    existingUser.setLastName(user.getLastName());
                    existingUser.setEmail(user.getEmail());
                    return userRepository.save(existingUser);
                })
                .orElse(null);
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    public User generateUserToken(Long id) {
        return userRepository.findById(id)
                .map(user -> {
                    String newToken = UUID.randomUUID().toString();
                    user.setToken(newToken);
                    user.setTokenCreatedAt(LocalDateTime.now());
                    user.setTokenExpiresAt(LocalDateTime.now().plusDays(1));
                    return userRepository.save(user);
                })
                .orElse(null);
    }
}
