package com.example.e_commerce_backend.controllers;

import java.util.*;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import com.example.e_commerce_backend.models.User;
import com.example.e_commerce_backend.security.CustomUserDetails;
import com.example.e_commerce_backend.security.JwtService;
import com.example.e_commerce_backend.services.UserService;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "http://localhost:4200")
public class UserController {

  private final UserService userService;
  private final AuthenticationManager authManager;
  private final JwtService jwtService;
  private final PasswordEncoder passwordEncoder;

  public UserController(
      UserService userService,
      AuthenticationManager authManager,
      JwtService jwtService,
      PasswordEncoder passwordEncoder
  ) {
    this.userService = userService;
    this.authManager = authManager;
    this.jwtService = jwtService;
    this.passwordEncoder = passwordEncoder;
  }

  // ✅ Registration + return JWT
  @PostMapping("/register")
  public ResponseEntity<Map<String, String>> registerUser(@RequestBody User user) {
    // Encode password before saving
    user.setPassword(passwordEncoder.encode(user.getPassword()));
    User createdUser = userService.registerUser(user);

    String token = jwtService.generateToken(new CustomUserDetails(createdUser));

    return ResponseEntity.ok(Map.of("token", token));
  }

  // ✅ Login with username + password
  @PostMapping("/login")
  public ResponseEntity<Map<String, Object>> login(@RequestBody Map<String, String> request) {
    String username = request.get("username");
    String password = request.get("password");

    authManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));

    User user = userService.findByUsername(username)
      .orElseThrow(() -> new RuntimeException("User not found"));

    String token = jwtService.generateToken(new CustomUserDetails(user));

    Map<String, Object> response = new HashMap<>();
    response.put("token", token);
    response.put("id", user.getId());
    response.put("username", user.getUsername());
    response.put("email", user.getEmail());
    response.put("role", user.getRole());
    response.put("isBanned", user.getBanned());
    response.put("dateOfBirth", user.getDateOfBirth());
    response.put("phoneNumber", user.getPhoneNumber());

    return ResponseEntity.ok(response);
  }

  @GetMapping
  public ResponseEntity<List<User>> getAllUsers() {
    return ResponseEntity.ok(userService.getAllUsers());
  }

  @GetMapping("/username/{username}")
  public ResponseEntity<User> getUserByUsername(@PathVariable String username) {
    return userService.findByUsername(username)
      .map(ResponseEntity::ok)
      .orElseGet(() -> ResponseEntity.notFound().build());
  }

  @PutMapping("/{id}/update")
  public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody User userDetails) {
    try {
      User updatedUser = userService.updateUser(id, userDetails);
      return ResponseEntity.ok(updatedUser);
    } catch (IllegalArgumentException e) {
      return ResponseEntity.notFound().build();
    }
  }
  
  @PutMapping("/{id}/toggle-ban")
  public ResponseEntity<User> toggleBan(@PathVariable Long id) {
    return userService.toggleBan(id)
      .map(ResponseEntity::ok)
      .orElseGet(() -> ResponseEntity.notFound().build());
  }

  @PutMapping("/{id}/change-password")
  public ResponseEntity<User> changePassword(@PathVariable Long id, @RequestBody String newPassword) {
    try {
      User updatedUser = userService.changePassword(id, newPassword);
      return ResponseEntity.ok(updatedUser);
    } catch (IllegalArgumentException e) {
      return ResponseEntity.notFound().build();
    }
  }
}