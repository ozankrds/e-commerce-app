package com.example.e_commerce_backend.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.e_commerce_backend.models.Category;
import com.example.e_commerce_backend.models.Product;
import com.example.e_commerce_backend.models.Review;
import com.example.e_commerce_backend.repositories.CategoryRepository;
import com.example.e_commerce_backend.repositories.ProductRepository;
import com.example.e_commerce_backend.repositories.ReviewRepository;

@Service
public class ProductService {
  private final ProductRepository productRepository;
  private final ReviewRepository reviewRepository;
  private final CategoryRepository categoryRepository;

  public ProductService(
    ProductRepository productRepository, 
    ReviewRepository reviewRepository, 
    CategoryRepository categoryRepository
  ) {
    this.productRepository = productRepository;
    this.reviewRepository = reviewRepository;
    this.categoryRepository = categoryRepository;
  }

  public Product saveProduct(Product product) {
    return productRepository.save(product);  // Persist the product
  }

  public Product createProduct(Product product, Long categoryId) {
    Category category = categoryRepository.findById(categoryId)
      .orElseThrow(() -> new RuntimeException("Bottom category not found"));
    
    product.setCategory(category);
    return productRepository.save(product);
  }

  public Product getProduct(Long id) {
    return productRepository.findById(id)
      .orElseThrow(() -> new RuntimeException("Product not found"));
  }

  public List<Product> getAllProducts() {
    return productRepository.findAll();
  }

  public List<Product> getProductsByCategory(Category category) {
    return productRepository.findByCategory(category);
  }

  public void addReview(Long productId, Review review) {
    Product product = productRepository.findById(productId)
      .orElseThrow(() -> new RuntimeException("Product not found"));

    review.setProduct(product);
    reviewRepository.save(review);

    List<Review> reviews = reviewRepository.findByProduct(product);
    int numberOfReviews = reviews.size();
    double averageRating = reviews.stream()
      .mapToDouble(Review::getRating)
      .average()
      .orElse(0.0);

    product.setNumberOfReviews(numberOfReviews);
    product.setAverageRating(averageRating);
    productRepository.save(product);
  }

  public void deleteProduct(Long id) {
    productRepository.deleteById(id);
  }
}
