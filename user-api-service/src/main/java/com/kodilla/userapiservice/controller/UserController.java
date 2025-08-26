package com.kodilla.tripplanner.controller;

import com.kodilla.tripplanner.domain.User;
import com.kodilla.tripplanner.dto.UserDTO;
import com.kodilla.tripplanner.dto.UserFormDTO;
import com.kodilla.tripplanner.mapper.UserMapper;
import com.kodilla.tripplanner.service.UserService;
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
    public ResponseEntity<UserDTO> getUserById(@PathVariable Long id) {
        User user = userService.getUserById(id);
        UserDTO userDTO = userMapper.toUserDTO(user);
        return ResponseEntity.ok(userDTO);
    }

    @PostMapping
    public ResponseEntity<UserDTO> createUser(@RequestBody UserFormDTO userForm) {
        User user = userMapper.toNewUser(userForm);
        User createdUser = userService.createUser(user);
        return ResponseEntity.ok(userMapper.toUserDTO(createdUser));
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserDTO> updateUser(@PathVariable Long id, @RequestBody UserDTO userDto) {
        User user = userMapper.toUser(userDto);
        User updatedUser = userService.updateUser(id, user);
        return ResponseEntity.ok(userMapper.toUserDTO(updatedUser));
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/token")
    public ResponseEntity<UserDTO> generateUserToken(@PathVariable Long id) {
        User user = userService.generateUserToken(id);
        return ResponseEntity.ok(userMapper.toUserDTO(user));
    }

    @GetMapping("/{id}/trips")
    public String getUserTrips(@PathVariable String id) {
        return "Trips for user " + id + ": Trip1, Trip2, Trip3";
    }
}
