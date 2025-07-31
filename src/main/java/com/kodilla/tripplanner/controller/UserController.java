package com.kodilla.tripplanner.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
public class UserController {

    @GetMapping
    public List<String> getAllUsers() {
        return List.of("User1", "User2", "User3");
    }

    @GetMapping("/{id}")
    public String getUserById(@PathVariable String id) {
        return "User" + id;
    }

    @PostMapping
    public String createUser(@RequestBody String user) {
        return "User created: " + user;
    }

    @PutMapping("/{id}")
    public String updateUser(@PathVariable String id, @RequestBody String user) {
        return "User " + id + " updated with data: " + user;
    }

    @DeleteMapping("/{id}")
    public String deleteUser(@PathVariable String id) {
        return "User " + id + " deleted";
    }
}
