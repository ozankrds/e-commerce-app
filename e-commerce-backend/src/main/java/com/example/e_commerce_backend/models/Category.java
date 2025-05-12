package com.example.e_commerce_backend.models;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

@Entity
public class Category {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  
  @Column(name = "name", nullable = false)
  private String name;

  @OneToMany(mappedBy = "category", cascade = CascadeType.ALL, orphanRemoval = true)
  @JsonIgnore
  private List<Product> products;

  // Getters
  public Long getId() {
    return id;
  }
  public String getName() {
    return name;
  }
  public List<Product> getProducts() {
    return products;
  }
  //
  // Setters
  public void setName(String name) {
    this.name = name;
  }
  //
  
  public void addProduct(Product product) {
    this.products.add(product);
  }
}

