package com.kodilla.tripplanner.mapper;

import com.kodilla.tripplanner.domain.AccountType;
import com.kodilla.tripplanner.domain.User;
import com.kodilla.tripplanner.dto.UserDTO;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

class UserMapperTest {

    private final UserMapper mapper = new UserMapper();

    @Test
    void shouldMapToUserDTO() {
        User user = User.builder()
                .id(1L)
                .accountType(AccountType.ADMIN)
                .firstName("Jan")
                .lastName("Kowalski")
                .email("jan@kowalski.pl")
                .token("abc")
                .tokenCreatedAt(LocalDateTime.now())
                .tokenExpiresAt(LocalDateTime.now().plusDays(1))
                .build();

        UserDTO dto = mapper.toUserDTO(user);

        assertThat(dto).isNotNull();
        assertThat(dto.id()).isEqualTo(1L);
        assertThat(dto.accountType()).isEqualTo(AccountType.ADMIN);
        assertThat(dto.firstName()).isEqualTo("Jan");
        assertThat(dto.lastName()).isEqualTo("Kowalski");
        assertThat(dto.email()).isEqualTo("jan@kowalski.pl");
        assertThat(dto.token()).isEqualTo("abc");
    }

    @Test
    void shouldMapToUser() {
        LocalDateTime now = LocalDateTime.now();
        UserDTO dto = new UserDTO(2L, AccountType.USER, "anno", "Anna",
                "Nowak", "anna@nowak.pl", "def", now, now.plusDays(2));

        User user = mapper.toUser(dto);

        assertThat(user).isNotNull();
        assertThat(user.getId()).isEqualTo(2L);
        assertThat(user.getAccountType()).isEqualTo(AccountType.USER);
        assertThat(user.getLogin()).isEqualTo("anno");
        assertThat(user.getFirstName()).isEqualTo("Anna");
        assertThat(user.getLastName()).isEqualTo("Nowak");
        assertThat(user.getEmail()).isEqualTo("anna@nowak.pl");
        assertThat(user.getToken()).isEqualTo("def");
    }

    @Test
    void shouldReturnNullForNullInput() {
        assertThat(mapper.toUserDTO(null)).isNull();
        assertThat(mapper.toUser(null)).isNull();
    }
}