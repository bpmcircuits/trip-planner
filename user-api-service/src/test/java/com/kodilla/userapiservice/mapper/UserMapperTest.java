package com.kodilla.userapiservice.mapper;

import com.kodilla.userapiservice.domain.AccountType;
import com.kodilla.userapiservice.domain.User;
import com.kodilla.userapiservice.domain.UserStatus;
import com.kodilla.userapiservice.dto.UserDTO;
import com.kodilla.userapiservice.dto.UserFormDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

class UserMapperTest {

    private UserMapper userMapper;
    private User testUser;
    private UserDTO testUserDTO;
    private UserFormDTO testUserFormDTO;
    private LocalDateTime now;

    @BeforeEach
    void setUp() {
        userMapper = new UserMapper();
        now = LocalDateTime.now();
        
        testUser = User.builder()
                .id(1L)
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

        testUserDTO = new UserDTO(
                1L,
                AccountType.USER,
                "testuser",
                "Test",
                "User",
                "test@example.com",
                "token123",
                now,
                now.plusDays(1)
        );

        testUserFormDTO = new UserFormDTO(
                "testuser",
                "Test",
                "User",
                "test@example.com",
                "password123"
        );
    }

    @Test
    void shouldMapUserToUserDTO() {
        // When
        UserDTO result = userMapper.toUserDTO(testUser);

        // Then
        assertNotNull(result);
        assertEquals(1L, result.id());
        assertEquals(AccountType.USER, result.accountType());
        assertEquals("testuser", result.login());
        assertEquals("Test", result.firstName());
        assertEquals("User", result.lastName());
        assertEquals("test@example.com", result.email());
        assertEquals("token123", result.token());
        assertEquals(now, result.tokenCreatedAt());
        assertEquals(now.plusDays(1), result.tokenExpiresAt());
    }

    @Test
    void shouldReturnNullWhenMappingNullUserToUserDTO() {
        // When
        UserDTO result = userMapper.toUserDTO(null);

        // Then
        assertNull(result);
    }

    @Test
    void shouldMapUserListToUserDTOList() {
        // When
        List<UserDTO> result = userMapper.toUserDTOList(List.of(testUser));

        // Then
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(1L, result.get(0).id());
        assertEquals("testuser", result.get(0).login());
        assertEquals("test@example.com", result.get(0).email());
    }

    @Test
    void shouldReturnEmptyListWhenMappingNullUserListToUserDTOList() {
        // When
        List<UserDTO> result = userMapper.toUserDTOList(null);

        // Then
        assertNotNull(result);
        assertEquals(0, result.size());
    }

    @Test
    void shouldReturnEmptyListWhenMappingEmptyUserListToUserDTOList() {
        // When
        List<UserDTO> result = userMapper.toUserDTOList(List.of());

        // Then
        assertNotNull(result);
        assertEquals(0, result.size());
    }

    @Test
    void shouldMapUserDTOToUser() {
        // When
        User result = userMapper.toUser(testUserDTO);

        // Then
        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals(AccountType.USER, result.getAccountType());
        assertEquals("testuser", result.getLogin());
        assertEquals("Test", result.getFirstName());
        assertEquals("User", result.getLastName());
        assertEquals("test@example.com", result.getEmail());
        assertEquals("token123", result.getToken());
        assertEquals(now, result.getTokenCreatedAt());
        assertEquals(now.plusDays(1), result.getTokenExpiresAt());
    }

    @Test
    void shouldReturnNullWhenMappingNullUserDTOToUser() {
        // When
        User result = userMapper.toUser(null);

        // Then
        assertNull(result);
    }

    @Test
    void shouldMapUserFormDTOToNewUser() {
        // When
        User result = userMapper.toNewUser(testUserFormDTO);

        // Then
        assertNotNull(result);
        assertEquals("testuser", result.getLogin());
        assertEquals("Test", result.getFirstName());
        assertEquals("User", result.getLastName());
        assertEquals("test@example.com", result.getEmail());
        assertEquals("password123", result.getPasswordHash());
    }

    @Test
    void shouldReturnNullWhenMappingNullUserFormDTOToNewUser() {
        // When
        User result = userMapper.toNewUser(null);

        // Then
        assertNull(result);
    }
}