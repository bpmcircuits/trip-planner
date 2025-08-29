package com.kodilla.userapiservice.controller;

import com.kodilla.userapiservice.domain.User;
import com.kodilla.userapiservice.dto.UserDTO;
import com.kodilla.userapiservice.dto.UserFormDTO;
import com.kodilla.userapiservice.exception.EmailAlreadyExistsException;
import com.kodilla.userapiservice.exception.EmailSendingException;
import com.kodilla.userapiservice.exception.InvalidVerificationCodeException;
import com.kodilla.userapiservice.exception.LoginAlreadyExistsException;
import com.kodilla.userapiservice.exception.UserNotFoundException;
import com.kodilla.userapiservice.mapper.UserMapper;
import com.kodilla.userapiservice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserService userService;
    private final UserMapper userMapper;

    @GetMapping
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        List<User> users = userService.getAllUsers();
        List<UserDTO> userDTOs = userMapper.toUserDTOList(users);
        return ResponseEntity.ok(userDTOs);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable Long id) throws UserNotFoundException {
        User user = userService.getUserById(id);
        UserDTO userDTO = userMapper.toUserDTO(user);
        return ResponseEntity.ok(userDTO);
    }

    @PostMapping
    public ResponseEntity<UserDTO> registerUser(@RequestBody UserFormDTO userForm) throws EmailAlreadyExistsException, LoginAlreadyExistsException, EmailSendingException {
        User user = userMapper.toNewUser(userForm);
        User createdUser = userService.registerUser(user);
        return ResponseEntity.ok(userMapper.toUserDTO(createdUser));
    }

    @PostMapping("/verify")
    public ResponseEntity<Boolean> verifyUser(
            @RequestParam String email,
            @RequestParam String verificationCode) throws UserNotFoundException, InvalidVerificationCodeException {
        Boolean isVerified = userService.verifyUser(email, verificationCode);
        return ResponseEntity.ok(isVerified);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserDTO> updateUser(@PathVariable Long id, @RequestBody UserDTO userDto) throws UserNotFoundException {
        User user = userMapper.toUser(userDto);
        User updatedUser = userService.updateUser(id, user);
        return ResponseEntity.ok(userMapper.toUserDTO(updatedUser));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) throws UserNotFoundException {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/token")
    public ResponseEntity<UserDTO> generateUserToken(@PathVariable Long id) throws UserNotFoundException {
        User user = userService.generateUserToken(id);
        return ResponseEntity.ok(userMapper.toUserDTO(user));
    }

    @GetMapping("/{id}/trips")
    public String getUserTrips(@PathVariable String id) {
        return "Trips for user " + id + ": Trip1, Trip2, Trip3";
    }
}
