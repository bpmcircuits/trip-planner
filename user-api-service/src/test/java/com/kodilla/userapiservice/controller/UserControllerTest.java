package com.kodilla.userapiservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.kodilla.userapiservice.domain.AccountType;
import com.kodilla.userapiservice.domain.User;
import com.kodilla.userapiservice.domain.UserStatus;
import com.kodilla.userapiservice.dto.UserDTO;
import com.kodilla.userapiservice.dto.UserFormDTO;
import com.kodilla.userapiservice.exception.EmailAlreadyExistsException;
import com.kodilla.userapiservice.exception.InvalidVerificationCodeException;
import com.kodilla.userapiservice.exception.LoginAlreadyExistsException;
import com.kodilla.userapiservice.exception.UserNotFoundException;
import com.kodilla.userapiservice.mapper.UserMapper;
import com.kodilla.userapiservice.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDateTime;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class UserControllerTest {

    private MockMvc mockMvc;
    private ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());

    @Mock
    private UserService userService;

    @Mock
    private UserMapper userMapper;

    @InjectMocks
    private UserController userController;

    private User testUser;
    private UserDTO testUserDTO;
    private UserFormDTO testUserFormDTO;

    @BeforeEach
    void setUp() {
        // Setup MockMvc with GlobalHttpErrorHandler
        mockMvc = MockMvcBuilders.standaloneSetup(userController)
                .setControllerAdvice(new com.kodilla.userapiservice.exception.GlobalHttpErrorHandler())
                .build();
        
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
                "password123",
                "test@example.com"
        );
    }

    @Test
    void shouldGetAllUsers() throws Exception {
        // Given
        List<User> users = List.of(testUser);
        List<UserDTO> userDTOs = List.of(testUserDTO);
        
        when(userService.getAllUsers()).thenReturn(users);
        when(userMapper.toUserDTOList(users)).thenReturn(userDTOs);

        // When & Then
        mockMvc.perform(get("/api/v1/users")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].login", is("testuser")))
                .andExpect(jsonPath("$[0].email", is("test@example.com")));
    }

    @Test
    void shouldGetUserById() throws Exception {
        // Given
        when(userService.getUserById(1L)).thenReturn(testUser);
        when(userMapper.toUserDTO(testUser)).thenReturn(testUserDTO);

        // When & Then
        mockMvc.perform(get("/api/v1/users/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.login", is("testuser")))
                .andExpect(jsonPath("$.email", is("test@example.com")));
    }

    @Test
    void shouldReturnNotFoundWhenUserDoesNotExist() throws Exception {
        // Given
        when(userService.getUserById(999L)).thenThrow(new UserNotFoundException());

        // When & Then
        mockMvc.perform(get("/api/v1/users/999")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void shouldRegisterUser() throws Exception {
        // Given
        when(userMapper.toNewUser(any(UserFormDTO.class))).thenReturn(testUser);
        when(userService.registerUser(any(User.class))).thenReturn(testUser);
        when(userMapper.toUserDTO(testUser)).thenReturn(testUserDTO);

        // When & Then
        mockMvc.perform(post("/api/v1/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(testUserFormDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.login", is("testuser")))
                .andExpect(jsonPath("$.email", is("test@example.com")));
    }

    @Test
    void shouldReturnConflictWhenEmailAlreadyExists() throws Exception {
        // Given
        when(userMapper.toNewUser(any(UserFormDTO.class))).thenReturn(testUser);
        when(userService.registerUser(any(User.class))).thenThrow(new EmailAlreadyExistsException());

        // When & Then
        mockMvc.perform(post("/api/v1/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(testUserFormDTO)))
                .andExpect(status().isConflict());
    }

    @Test
    void shouldReturnConflictWhenLoginAlreadyExists() throws Exception {
        // Given
        when(userMapper.toNewUser(any(UserFormDTO.class))).thenReturn(testUser);
        when(userService.registerUser(any(User.class))).thenThrow(new LoginAlreadyExistsException());

        // When & Then
        mockMvc.perform(post("/api/v1/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(testUserFormDTO)))
                .andExpect(status().isConflict());
    }

    @Test
    void shouldVerifyUser() throws Exception {
        // Given
        when(userService.verifyUser(anyString(), anyString())).thenReturn(true);

        // When & Then
        mockMvc.perform(post("/api/v1/users/verify")
                .param("email", "test@example.com")
                .param("verificationCode", "123456")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", is(true)));
    }

    @Test
    void shouldReturnNotFoundWhenVerifyingNonExistentUser() throws Exception {
        // Given
        when(userService.verifyUser(anyString(), anyString())).thenThrow(new UserNotFoundException());

        // When & Then
        mockMvc.perform(post("/api/v1/users/verify")
                .param("email", "nonexistent@example.com")
                .param("verificationCode", "123456")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void shouldReturnBadRequestWhenVerificationCodeIsInvalid() throws Exception {
        // Given
        when(userService.verifyUser(anyString(), anyString())).thenThrow(new InvalidVerificationCodeException());

        // When & Then
        mockMvc.perform(post("/api/v1/users/verify")
                .param("email", "test@example.com")
                .param("verificationCode", "invalid")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldUpdateUser() throws Exception {
        // Given
        when(userMapper.toUser(any(UserDTO.class))).thenReturn(testUser);
        when(userService.updateUser(anyLong(), any(User.class))).thenReturn(testUser);
        when(userMapper.toUserDTO(testUser)).thenReturn(testUserDTO);

        // When & Then
        mockMvc.perform(put("/api/v1/users/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(testUserDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.login", is("testuser")))
                .andExpect(jsonPath("$.email", is("test@example.com")));
    }

    @Test
    void shouldReturnNotFoundWhenUpdatingNonExistentUser() throws Exception {
        // Given
        when(userMapper.toUser(any(UserDTO.class))).thenReturn(testUser);
        when(userService.updateUser(anyLong(), any(User.class))).thenThrow(new UserNotFoundException());

        // When & Then
        mockMvc.perform(put("/api/v1/users/999")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(testUserDTO)))
                .andExpect(status().isNotFound());
    }

    @Test
    void shouldDeleteUser() throws Exception {
        // Given
        doNothing().when(userService).deleteUser(1L);

        // When & Then
        mockMvc.perform(delete("/api/v1/users/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    @Test
    void shouldReturnNotFoundWhenDeletingNonExistentUser() throws Exception {
        // Given
        doThrow(new UserNotFoundException()).when(userService).deleteUser(999L);

        // When & Then
        mockMvc.perform(delete("/api/v1/users/999")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void shouldGenerateUserToken() throws Exception {
        // Given
        when(userService.generateUserToken(1L)).thenReturn(testUser);
        when(userMapper.toUserDTO(testUser)).thenReturn(testUserDTO);

        // When & Then
        mockMvc.perform(post("/api/v1/users/1/token")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.login", is("testuser")))
                .andExpect(jsonPath("$.email", is("test@example.com")));
    }

    @Test
    void shouldReturnNotFoundWhenGeneratingTokenForNonExistentUser() throws Exception {
        // Given
        when(userService.generateUserToken(999L)).thenThrow(new UserNotFoundException());

        // When & Then
        mockMvc.perform(post("/api/v1/users/999/token")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void shouldGetUserTrips() throws Exception {
        // When & Then
        mockMvc.perform(get("/api/v1/users/1/trips")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", is("Trips for user 1: Trip1, Trip2, Trip3")));
    }
}