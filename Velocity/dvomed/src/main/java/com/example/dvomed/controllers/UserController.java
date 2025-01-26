package com.example.dvomed.controllers;

import com.example.dvomed.entities.User;
import com.example.dvomed.services.UserServiceImpl;
import com.example.dvomed.utils.JwtUtil;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserServiceImpl userService;
    private final JwtUtil jwtUtil;

    public UserController(UserServiceImpl userService, JwtUtil jwtUtil) {
        this.userService = userService;
        this.jwtUtil = jwtUtil;
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')") // Restrict to ADMIN role
    public List<User> getAllUsers(@RequestHeader("Authorization") String token) {
        jwtUtil.extractUsername(token.substring(7)); // Validate the token
        return userService.getAllUsers();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')") // Allow USER and ADMIN
    public ResponseEntity<User> getUserById(
            @RequestHeader("Authorization") String token,
            @PathVariable Long id) {
        jwtUtil.extractUsername(token.substring(7)); // Validate the token
        return userService.getUserById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')") // Only ADMIN can create new users
    public ResponseEntity<User> createUser(
            @RequestHeader("Authorization") String token,
            @RequestBody User user) {
        jwtUtil.extractUsername(token.substring(7)); // Validate the token
        User createdUser = userService.createUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')") // Only ADMIN can delete users
    public ResponseEntity<Void> deleteUser(
            @RequestHeader("Authorization") String token,
            @PathVariable Long id) {
        jwtUtil.extractUsername(token.substring(7)); // Validate the token
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
}

