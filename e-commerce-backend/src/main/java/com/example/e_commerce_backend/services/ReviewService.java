package com.example.e_commerce_backend.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.e_commerce_backend.models.Product;
import com.example.e_commerce_backend.models.Review;
import com.example.e_commerce_backend.models.User;
import com.example.e_commerce_backend.repositories.ProductRepository;
import com.example.e_commerce_backend.repositories.ReviewRepository;

@Service
public class ReviewService {

private final ReviewRepository reviewRepository;
  private final ProductRepository productRepository;

  public ReviewService(ReviewRepository reviewRepository, ProductRepository productRepository) {
    this.reviewRepository = reviewRepository;
    this.productRepository = productRepository;
  }

  public void addReview(Long productId, int rating, String comment, User user) {
    Product product = productRepository.findById(productId)
      .orElseThrow(() -> new RuntimeException("Product not found"));

    Review review = new Review(rating, comment, product, user);
    reviewRepository.save(review);

    updateProductRating(product);
  }

  private void updateProductRating(Product product) {
    List<Review> reviews = reviewRepository.findByProduct(product);

    int totalReviews = reviews.size();
    
    double totalRating = 0;
    for (Review review : reviews) {
      totalRating += review.getRating();
    }

    product.setAverageRating(totalRating / totalReviews);
    product.setNumberOfReviews(totalReviews);
    productRepository.save(product);
  }

  public List<Review> getReviewsByProduct(Long productId) {
    Product product = productRepository.findById(productId)
      .orElseThrow(() -> new RuntimeException("Product not found"));

    return product.getReviews();
  }
}


