package com.kodilla.userapiservice.repository;

import com.kodilla.userapiservice.domain.AccountType;
import com.kodilla.userapiservice.domain.User;
import com.kodilla.userapiservice.domain.UserStatus;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

@DataJpaTest
class UserRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private UserRepository userRepository;

    @Test
    void shouldFindUserByEmail() {
        // Given
        LocalDateTime now = LocalDateTime.now();
        User user = User.builder()
                .accountType(AccountType.USER)
                .login("testuser")
                .firstName("Test")
                .lastName("User")
                .email("test@example.com")
                .passwordHash("hashedpassword")
                .createdAt(now)
                .token("token123")
                .tokenCreatedAt(now)
                .tokenExpiresAt(now.plusDays(1))
                .verificationCode("123456")
                .userStatus(UserStatus.NOT_VERIFIED)
                .build();
        entityManager.persist(user);
        entityManager.flush();

        // When
        User found = userRepository.findByEmail("test@example.com");

        // Then
        assertNotNull(found);
        assertEquals("testuser", found.getLogin());
        assertEquals("test@example.com", found.getEmail());
    }

    @Test
    void shouldReturnNullWhenEmailNotFound() {
        // When
        User found = userRepository.findByEmail("nonexistent@example.com");

        // Then
        assertNull(found);
    }

    @Test
    void shouldFindUserByLogin() {
        // Given
        LocalDateTime now = LocalDateTime.now();
        User user = User.builder()
                .accountType(AccountType.USER)
                .login("testuser")
                .firstName("Test")
                .lastName("User")
                .email("test@example.com")
                .passwordHash("hashedpassword")
                .createdAt(now)
                .token("token123")
                .tokenCreatedAt(now)
                .tokenExpiresAt(now.plusDays(1))
                .verificationCode("123456")
                .userStatus(UserStatus.NOT_VERIFIED)
                .build();
        entityManager.persist(user);
        entityManager.flush();

        // When
        User found = userRepository.findByLogin("testuser");

        // Then
        assertNotNull(found);
        assertEquals("testuser", found.getLogin());
        assertEquals("test@example.com", found.getEmail());
    }

    @Test
    void shouldReturnNullWhenLoginNotFound() {
        // When
        User found = userRepository.findByLogin("nonexistent");

        // Then
        assertNull(found);
    }
}