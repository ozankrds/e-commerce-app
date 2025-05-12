package com.example.e_commerce_backend.controllers;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.e_commerce_backend.models.Review;
import com.example.e_commerce_backend.models.User;
import com.example.e_commerce_backend.services.ReviewService;

@RestController
@RequestMapping("/api/reviews")
@CrossOrigin(origins = "http://localhost:4200")
public class ReviewController {

  private final ReviewService reviewService;

  public ReviewController(ReviewService reviewService) {
    this.reviewService = reviewService;
  }

  @PostMapping("/{productId}")
  public ResponseEntity<String> addReview(
    @PathVariable Long productId,
    @RequestParam int rating,
    @RequestParam String comment,
    @RequestBody User user
  ) {
    reviewService.addReview(productId, rating, comment, user);
    return ResponseEntity.ok("Review added successfully!");
  }

  @GetMapping("/{productId}")
  public List<Review> getReviewsByProduct(@PathVariable Long productId) {
    return reviewService.getReviewsByProduct(productId);
  }
}
