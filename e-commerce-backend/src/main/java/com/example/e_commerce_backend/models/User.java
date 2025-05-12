package com.example.e_commerce_backend.models;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class User {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;

  @Column(name = "username", unique = true, nullable = false)
  private String username;

  @Column(nullable = false)
  private String password;

  @Column(nullable = false)
  private String email;

  private String role;
    
  private LocalDate dateOfBirth;
  private String phoneNumber;
  private boolean isBanned;

  // Getters 
  public long getId() {
    return id;
  }

  public String getUsername() {
    return username;
  }

  public String getPassword() {
    return password;
  }

  public String getEmail() {
    return email;
  }

  public String getRole() {
    return role;
  }

  public LocalDate getDateOfBirth() {
    return dateOfBirth;
  }

  public String getPhoneNumber() {
    return phoneNumber;
  }

  public boolean getBanned() {
    return isBanned;
  }
  //

  // Setters
  public void setUsername(String username) {
    this.username = username;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public void setRole(String role) {
    this.role = role;
  }

  public void setDateOfBirth(LocalDate dateOfBirth) {
    this.dateOfBirth = dateOfBirth;
  }

  public void setPhoneNumber(String phoneNumber) {
    this.phoneNumber = phoneNumber;
  }

  public void setBanned(boolean isBanned) {
    this.isBanned = isBanned;
  }
  //  
}
