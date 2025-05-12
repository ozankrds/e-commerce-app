package com.example.e_commerce_backend.models;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

@Entity
public class Product {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String name;
  private String producerName;
  private String description;

  @ManyToOne
  @JoinColumn(name = "seller_id")
  private User seller;

  @Column(nullable = false)
  private double price;

  @ManyToOne
  @JoinColumn(name = "category_id", nullable = false)
  private Category category;

  private int stock;

  @Column(nullable = false, columnDefinition = "INT DEFAULT 0")
  private int numberOfReviews;

  @Column(nullable = false, columnDefinition = "DECIMAL(3,2) DEFAULT 0.0")
  private double averageRating;

  @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
  private List<Review> reviews;

  private String imageSrc;

  // Getters
  public Long getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public String getProducerName() {
    return producerName;
  }

  public String getDescription() {
    return description;
  }

  public User getSeller() {
    return seller;
  }

  public double getPrice() {
    return price;
  }

  public int getStock() {
    return stock;
  }

  public Category getCategory() {
    return category;
  }

  public int getNumberOfReviews() {
    return numberOfReviews;
  }

  public double getAverageRating() {
    return averageRating;
  }

  public List<Review> getReviews() {
    return reviews;
  }

  public String getImageSrc() {
    return imageSrc;
  }
  //

  // Setters
  public void setName(String name) {
    this.name = name;
  }

  public void setProducerName(String producerName) {
    this.producerName = producerName;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public void setSeller(User seller) {
    this.seller = seller;
  }

  public void setPrice(double price) {
    this.price = price;
  }

  public void setCategory(Category category) {
    this.category = category;
  }

  public void setStock(int stock) {
    this.stock = stock;
  }
 
  public void setNumberOfReviews(int numberOfReviews) {
    this.numberOfReviews = numberOfReviews;
  }
  
  public void setAverageRating(double averageRating) {
    this.averageRating = averageRating;
  }

  public void setReviews(List<Review> reviews) {
    this.reviews = reviews;
  }

  public void setImageSrc(String imageSrc) {
    this.imageSrc = imageSrc;
  }
  //

}