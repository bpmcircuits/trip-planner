package com.kodilla.userapiservice.service;

import com.kodilla.userapiservice.domain.AccountType;
import com.kodilla.userapiservice.domain.User;
import com.kodilla.userapiservice.domain.UserStatus;
import com.kodilla.userapiservice.exception.EmailAlreadyExistsException;
import com.kodilla.userapiservice.exception.EmailSendingException;
import com.kodilla.userapiservice.exception.InvalidVerificationCodeException;
import com.kodilla.userapiservice.exception.LoginAlreadyExistsException;
import com.kodilla.userapiservice.exception.UserNotFoundException;
import com.kodilla.userapiservice.mail.domain.Mail;
import com.kodilla.userapiservice.mail.service.EmailService;
import com.kodilla.userapiservice.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Mock
    private EmailService emailService;

    @InjectMocks
    private UserService userService;

    private User testUser;

    @BeforeEach
    void setUp() {
        LocalDateTime now = LocalDateTime.now();
        
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
    }

    @Test
    void shouldGetAllUsers() {
        // Given
        when(userRepository.findAll()).thenReturn(List.of(testUser));

        // When
        List<User> result = userService.getAllUsers();

        // Then
        assertEquals(1, result.size());
        assertEquals(testUser, result.get(0));
        verify(userRepository, times(1)).findAll();
    }

    @Test
    void shouldGetUserById() throws UserNotFoundException {
        // Given
        when(userRepository.findById(1L)).thenReturn(Optional.of(testUser));

        // When
        User result = userService.getUserById(1L);

        // Then
        assertEquals(testUser, result);
        verify(userRepository, times(1)).findById(1L);
    }

    @Test
    void shouldThrowExceptionWhenUserNotFound() {
        // Given
        when(userRepository.findById(999L)).thenReturn(Optional.empty());

        // When & Then
        assertThrows(UserNotFoundException.class, () -> userService.getUserById(999L));
        verify(userRepository, times(1)).findById(999L);
    }

    @Test
    void shouldRegisterUser() throws EmailAlreadyExistsException, LoginAlreadyExistsException, EmailSendingException {
        // Given
        User newUser = User.builder()
                .login("newuser")
                .firstName("New")
                .lastName("User")
                .email("new@example.com")
                .passwordHash("password123")
                .build();

        when(userRepository.findByEmail("new@example.com")).thenReturn(null);
        when(userRepository.findByLogin("newuser")).thenReturn(null);
        when(bCryptPasswordEncoder.encode("password123")).thenReturn("hashedpassword");
        when(userRepository.save(any(User.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // When
        User result = userService.registerUser(newUser);

        // Then
        assertNotNull(result);
        assertEquals("newuser", result.getLogin());
        assertEquals("new@example.com", result.getEmail());
        assertEquals("hashedpassword", result.getPasswordHash());
        assertEquals(AccountType.USER, result.getAccountType());
        assertEquals(UserStatus.NOT_VERIFIED, result.getUserStatus());
        assertNotNull(result.getVerificationCode());
        assertNotNull(result.getToken());
        assertNotNull(result.getTokenCreatedAt());
        assertNotNull(result.getTokenExpiresAt());

        verify(userRepository, times(1)).findByEmail("new@example.com");
        verify(userRepository, times(1)).findByLogin("newuser");
        verify(bCryptPasswordEncoder, times(1)).encode("password123");
        verify(userRepository, times(1)).save(any(User.class));
        verify(emailService, times(1)).sendVerificationEmail(any(Mail.class));
    }

    @Test
    void shouldThrowExceptionWhenEmailAlreadyExists() throws EmailSendingException {
        // Given
        User newUser = User.builder()
                .login("newuser")
                .firstName("New")
                .lastName("User")
                .email("existing@example.com")
                .passwordHash("password123")
                .build();

        when(userRepository.findByEmail("existing@example.com")).thenReturn(testUser);

        // When & Then
        assertThrows(EmailAlreadyExistsException.class, () -> userService.registerUser(newUser));
        verify(userRepository, times(1)).findByEmail("existing@example.com");
        verify(userRepository, never()).save(any(User.class));
        verify(emailService, never()).sendVerificationEmail(any(Mail.class));
    }

    @Test
    void shouldThrowExceptionWhenLoginAlreadyExists() throws EmailSendingException {
        // Given
        User newUser = User.builder()
                .login("existinglogin")
                .firstName("New")
                .lastName("User")
                .email("new@example.com")
                .passwordHash("password123")
                .build();

        when(userRepository.findByEmail("new@example.com")).thenReturn(null);
        when(userRepository.findByLogin("existinglogin")).thenReturn(testUser);

        // When & Then
        assertThrows(LoginAlreadyExistsException.class, () -> userService.registerUser(newUser));
        verify(userRepository, times(1)).findByEmail("new@example.com");
        verify(userRepository, times(1)).findByLogin("existinglogin");
        verify(userRepository, never()).save(any(User.class));
        verify(emailService, never()).sendVerificationEmail(any(Mail.class));
    }

    @Test
    void shouldVerifyUser() throws UserNotFoundException, InvalidVerificationCodeException {
        // Given
        User userToVerify = User.builder()
                .id(2L)
                .email("verify@example.com")
                .verificationCode("123456")
                .userStatus(UserStatus.NOT_VERIFIED)
                .build();

        when(userRepository.findByEmail("verify@example.com")).thenReturn(userToVerify);
        when(userRepository.save(any(User.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // When
        boolean result = userService.verifyUser("verify@example.com", "123456");

        // Then
        assertTrue(result);
        assertEquals(UserStatus.VERIFIED, userToVerify.getUserStatus());
        assertNull(userToVerify.getVerificationCode());
        verify(userRepository, times(1)).findByEmail("verify@example.com");
        verify(userRepository, times(1)).save(userToVerify);
    }

    @Test
    void shouldThrowExceptionWhenVerifyingNonExistentUser() {
        // Given
        when(userRepository.findByEmail("nonexistent@example.com")).thenReturn(null);

        // When & Then
        assertThrows(UserNotFoundException.class, () -> userService.verifyUser("nonexistent@example.com", "123456"));
        verify(userRepository, times(1)).findByEmail("nonexistent@example.com");
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    void shouldThrowExceptionWhenVerificationCodeIsInvalid() {
        // Given
        User userToVerify = User.builder()
                .id(2L)
                .email("verify@example.com")
                .verificationCode("123456")
                .userStatus(UserStatus.NOT_VERIFIED)
                .build();

        when(userRepository.findByEmail("verify@example.com")).thenReturn(userToVerify);

        // When & Then
        assertThrows(InvalidVerificationCodeException.class, () -> userService.verifyUser("verify@example.com", "invalid"));
        assertEquals(UserStatus.NOT_VERIFIED, userToVerify.getUserStatus());
        assertNotNull(userToVerify.getVerificationCode());
        verify(userRepository, times(1)).findByEmail("verify@example.com");
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    void shouldUpdateUser() throws UserNotFoundException {
        // Given
        User updatedUser = User.builder()
                .id(1L)
                .login("updateduser")
                .firstName("Updated")
                .lastName("User")
                .email("updated@example.com")
                .build();

        when(userRepository.findById(1L)).thenReturn(Optional.of(testUser));
        when(userRepository.save(any(User.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // When
        User result = userService.updateUser(1L, updatedUser);

        // Then
        assertEquals("updateduser", result.getLogin());
        assertEquals("Updated", result.getFirstName());
        assertEquals("User", result.getLastName());
        assertEquals("updated@example.com", result.getEmail());
        verify(userRepository, times(1)).findById(1L);
        verify(userRepository, times(1)).save(testUser);
    }

    @Test
    void shouldThrowExceptionWhenUpdatingNonExistentUser() {
        // Given
        User updatedUser = User.builder()
                .id(999L)
                .login("updateduser")
                .firstName("Updated")
                .lastName("User")
                .email("updated@example.com")
                .build();

        when(userRepository.findById(999L)).thenReturn(Optional.empty());

        // When & Then
        assertThrows(UserNotFoundException.class, () -> userService.updateUser(999L, updatedUser));
        verify(userRepository, times(1)).findById(999L);
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    void shouldDeleteUser() throws UserNotFoundException {
        // Given
        when(userRepository.existsById(1L)).thenReturn(true);
        doNothing().when(userRepository).deleteById(1L);

        // When
        userService.deleteUser(1L);

        // Then
        verify(userRepository, times(1)).existsById(1L);
        verify(userRepository, times(1)).deleteById(1L);
    }

    @Test
    void shouldThrowExceptionWhenDeletingNonExistentUser() {
        // Given
        when(userRepository.existsById(999L)).thenReturn(false);

        // When & Then
        assertThrows(UserNotFoundException.class, () -> userService.deleteUser(999L));
        verify(userRepository, times(1)).existsById(999L);
        verify(userRepository, never()).deleteById(anyLong());
    }

    @Test
    void shouldGenerateUserToken() throws UserNotFoundException {
        // Given
        when(userRepository.findById(1L)).thenReturn(Optional.of(testUser));
        when(userRepository.save(any(User.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // When
        User result = userService.generateUserToken(1L);

        // Then
        assertNotNull(result.getToken());
        assertNotEquals("token123", result.getToken());
        assertNotNull(result.getTokenCreatedAt());
        assertNotNull(result.getTokenExpiresAt());
        verify(userRepository, times(1)).findById(1L);
        verify(userRepository, times(1)).save(testUser);
    }

    @Test
    void shouldThrowExceptionWhenGeneratingTokenForNonExistentUser() {
        // Given
        when(userRepository.findById(999L)).thenReturn(Optional.empty());

        // When & Then
        assertThrows(UserNotFoundException.class, () -> userService.generateUserToken(999L));
        verify(userRepository, times(1)).findById(999L);
        verify(userRepository, never()).save(any(User.class));
    }
}