package com.example.e_commerce_backend.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.e_commerce_backend.models.User;
import com.example.e_commerce_backend.repositories.UserRepository;

@Service
public class UserService {

  @Autowired
  private UserRepository userRepository;

  public User registerUser(User user) {
    Optional<User> existingUser = userRepository.findByUsername(user.getUsername());

    if (existingUser.isPresent()) {
      throw new IllegalArgumentException("Username already taken.");
    }

    // Save the new user
    return userRepository.save(user);
  }

  public List<User> getAllUsers() {
    return userRepository.findAll();
  }

  public Optional<User> findByUsername(String username) {
    return userRepository.findByUsername(username);
  }

  public User updateUser(Long id, User userDetails) {
    Optional<User> userOpt = userRepository.findById(id);
    if (userOpt.isEmpty()) {
        throw new IllegalArgumentException("User not found.");
    }

    User user = userOpt.get();
    
    // Update user details
    user.setEmail(userDetails.getEmail());
    user.setPhoneNumber(userDetails.getPhoneNumber());
    user.setDateOfBirth(userDetails.getDateOfBirth());
    // Add other fields as needed

    return userRepository.save(user);
}

  public Optional<User> toggleBan(Long id) {
    return userRepository.findById(id).map(user -> {
        user.setBanned(!user.getBanned());
        return userRepository.save(user);
    });
  }

  public User changePassword(Long id, String newPassword) {
    Optional<User> userOpt = userRepository.findById(id);
    if (userOpt.isEmpty()) {
      throw new IllegalArgumentException("User not found.");
    }

    User user = userOpt.get();
    user.setPassword(newPassword); // In production, use a password encoder
    return userRepository.save(user);
}

}

