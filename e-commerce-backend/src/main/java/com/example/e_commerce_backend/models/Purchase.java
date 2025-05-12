package com.example.e_commerce_backend.models;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.*;

@Entity
public class Purchase {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne
  @JoinColumn(name = "user_id")
  private User user;

  @ManyToMany
  @JoinTable(
    name = "purchase_products",
    joinColumns = @JoinColumn(name = "purchase_id"),
    inverseJoinColumns = @JoinColumn(name = "product_id")
  )
  private List<Product> products = new ArrayList<>();

  @ElementCollection
  private List<Integer> quantities = new ArrayList<>();

  @Enumerated(EnumType.STRING)
  private PurchaseState state;

  private LocalDateTime purchaseDate = LocalDateTime.now();

  private String address;

  public enum PurchaseState {
    TAKEN,
    ON_THE_WAY,
    DELIVERED,
    CANCELLED
  }

  // Getters and setters

  public Long getId() {
    return id;
  }

  public User getUser() {
    return user;
  }

  public void setUser(User user) {
    this.user = user;
  }

  public List<Product> getProducts() {
    return products;
  }

  public void setProducts(List<Product> products) {
    this.products = products;
  }

  public List<Integer> getQuantities() {
    return quantities;
  }

  public void setQuantities(List<Integer> quantities) {
    this.quantities = quantities;
  }

  public PurchaseState getState() {
    return state;
  }

  public void setState(PurchaseState state) {
    this.state = state;
  }

  public LocalDateTime getPurchaseDate() {
    return purchaseDate;
  }

  public String getAddress() {
    return address;
  }

  public void setAddress(String address) {
    this.address = address;
  }
}