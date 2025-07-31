// src/test/java/com/kodilla/tripplanner/repository/UserRepositoryTest.java
package com.kodilla.tripplanner.repository;

import com.kodilla.tripplanner.domain.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    void shouldSaveAndFindAccountType() {
        User user = User.builder().accountType("admin").build();
        User saved = null;
        try {
            saved = userRepository.save(user);
            Optional<User> found = userRepository.findById(saved.getId());
            assertThat(found).isPresent();
            assertThat(found.get().getAccountType()).isEqualTo("admin");
        } finally {
            if (saved != null && saved.getId() != null) userRepository.deleteById(saved.getId());
        }
    }

    @Test
    void shouldSaveAndFindFirstName() {
        User user = User.builder().firstName("Jan").build();
        User saved = null;
        try {
            saved = userRepository.save(user);
            Optional<User> found = userRepository.findById(saved.getId());
            assertThat(found).isPresent();
            assertThat(found.get().getFirstName()).isEqualTo("Jan");
        } finally {
            if (saved != null && saved.getId() != null) userRepository.deleteById(saved.getId());
        }
    }

    @Test
    void shouldSaveAndFindLastName() {
        User user = User.builder().lastName("Kowalski").build();
        User saved = null;
        try {
            saved = userRepository.save(user);
            Optional<User> found = userRepository.findById(saved.getId());
            assertThat(found).isPresent();
            assertThat(found.get().getLastName()).isEqualTo("Kowalski");
        } finally {
            if (saved != null && saved.getId() != null) userRepository.deleteById(saved.getId());
        }
    }

    @Test
    void shouldSaveAndFindPasswordHash() {
        User user = User.builder().passwordHash("hash123").build();
        User saved = null;
        try {
            saved = userRepository.save(user);
            Optional<User> found = userRepository.findById(saved.getId());
            assertThat(found).isPresent();
            assertThat(found.get().getPasswordHash()).isEqualTo("hash123");
        } finally {
            if (saved != null && saved.getId() != null) userRepository.deleteById(saved.getId());
        }
    }

    @Test
    void shouldSaveAndFindEmail() {
        User user = User.builder().email("test@domain.com").build();
        User saved = null;
        try {
            saved = userRepository.save(user);
            Optional<User> found = userRepository.findById(saved.getId());
            assertThat(found).isPresent();
            assertThat(found.get().getEmail()).isEqualTo("test@domain.com");
        } finally {
            if (saved != null && saved.getId() != null) userRepository.deleteById(saved.getId());
        }
    }

    @Test
    void shouldSaveAndFindToken() {
        User user = User.builder().token("tokenABC").build();
        User saved = null;
        try {
            saved = userRepository.save(user);
            Optional<User> found = userRepository.findById(saved.getId());
            assertThat(found).isPresent();
            assertThat(found.get().getToken()).isEqualTo("tokenABC");
        } finally {
            if (saved != null && saved.getId() != null) userRepository.deleteById(saved.getId());
        }
    }

    @Test
    void shouldSaveAndFindTokenCreatedAt() {
        LocalDateTime now = LocalDateTime.now();
        User user = User.builder().tokenCreatedAt(now).build();
        User saved = null;
        try {
            saved = userRepository.save(user);
            Optional<User> found = userRepository.findById(saved.getId());
            assertThat(found).isPresent();
            assertThat(found.get().getTokenCreatedAt()).isEqualTo(now);
        } finally {
            if (saved != null && saved.getId() != null) userRepository.deleteById(saved.getId());
        }
    }

    @Test
    void shouldSaveAndFindTokenExpiresAt() {
        LocalDateTime expires = LocalDateTime.now().plusHours(2);
        User user = User.builder().tokenExpiresAt(expires).build();
        User saved = null;
        try {
            saved = userRepository.save(user);
            Optional<User> found = userRepository.findById(saved.getId());
            assertThat(found).isPresent();
            assertThat(found.get().getTokenExpiresAt()).isEqualTo(expires);
        } finally {
            if (saved != null && saved.getId() != null) userRepository.deleteById(saved.getId());
        }
    }
}