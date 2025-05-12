package com.example.e_commerce_backend.models;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Review {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne
  @JoinColumn(name = "product_id", nullable = false)
  private Product product;

  @ManyToOne
  @JoinColumn(name = "user_id", nullable = false)
  private User user;

  @Column(nullable = false)
  private int rating; // 1 to 5

  private String comment;

  private LocalDateTime createdAt = LocalDateTime.now();

  public Review() {}

  public Review(int rating, String comment, Product product, User user) {
    this.rating = rating;
    this.comment = comment;
    this.product = product;
    this.user = user;
  }

  // Getters and setters
  public Long getId() {
    return id;
  }
  public Product getProduct() {
    return product;
  }
  public User getUser() {
    return user;
  }
  public int getRating() {
    return rating;
  }
  public String getComment() {
    return comment;
  }
  public LocalDateTime getCreatedAt() {
    return createdAt;
  }
  //

  //
  public void setProduct(Product product) {
    this.product = product;
  }
  public void setUser(User user) {
    this.user = user;
  }
  public void setRating(int rating) {
    this.rating = rating;
  }
  public void setComment(String comment) {
    this.comment = comment;
  }
  //
}


