package com.kodilla.tripplanner.repository;

import com.kodilla.tripplanner.domain.AccountType;
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

    private User.UserBuilder baseUserBuilder() {
        LocalDateTime now = LocalDateTime.now();
        return User.builder()
                .accountType(AccountType.USER)
                .login("testLogin")
                .passwordHash("testHash")
                .email("test@domain.com")
                .token("testToken")
                .createdAt(now)
                .tokenCreatedAt(now)
                .tokenExpiresAt(now.plusHours(1));
    }

    @Test
    void shouldSaveAndFindAccountType() {
        User user = baseUserBuilder().accountType(AccountType.ADMIN).build();
        User saved = null;
        try {
            saved = userRepository.save(user);
            Optional<User> found = userRepository.findById(saved.getId());
            assertThat(found).isPresent();
            assertThat(found.get().getAccountType()).isEqualTo(AccountType.ADMIN);
        } finally {
            if (saved != null && saved.getId() != null) userRepository.deleteById(saved.getId());
        }
    }

    @Test
    void shouldSaveAndFindFirstName() {
        User user = baseUserBuilder().firstName("Jan").build();
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
        User user = baseUserBuilder().lastName("Kowalski").build();
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
        User user = baseUserBuilder().passwordHash("hash123").build();
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
        User user = baseUserBuilder().email("test@domain.com").build();
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
        User user = baseUserBuilder().token("tokenABC").build();
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
        User user = baseUserBuilder().tokenCreatedAt(now).build();
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
        User user = baseUserBuilder().tokenExpiresAt(expires).build();
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