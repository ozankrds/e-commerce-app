package com.example.e_commerce_backend.controllers;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.e_commerce_backend.models.Product;
import com.example.e_commerce_backend.models.Review;
import com.example.e_commerce_backend.models.Category;
import com.example.e_commerce_backend.services.CategoryService;
import com.example.e_commerce_backend.services.ProductService;

@RestController
@RequestMapping("/api/products")
@CrossOrigin(origins = "http://localhost:4200")
public class ProductController {

  private final ProductService productService;
  private final CategoryService categoryService;

  public ProductController(ProductService productService, CategoryService categoryService) {
    this.productService = productService;
    this.categoryService = categoryService;
  }

  @PostMapping
  @PreAuthorize("hasRole('Admin') or hasRole('Seller')")
  public ResponseEntity<Product> createProduct(
    @RequestBody Product product,
    @RequestParam Long categoryId
  ) {
    Product saved = productService.createProduct(product, categoryId);
    return ResponseEntity.ok(saved);
  }

  @GetMapping
  public ResponseEntity<List<Product>> getAllProducts() {
    return ResponseEntity.ok(productService.getAllProducts());
  }

  @GetMapping("/{id}")
  public ResponseEntity<Product> getProductById(@PathVariable Long id) {
    return ResponseEntity.ok(productService.getProduct(id));
  }

  @PutMapping("/{id}")
  @PreAuthorize("hasRole('Admin') or hasRole('Seller')")
  public ResponseEntity<Product> updateProduct(@PathVariable Long id, @RequestBody Product updated) {
    Product existing = productService.getProduct(id);
    
    existing.setName(updated.getName());
    existing.setDescription(updated.getDescription());
    existing.setProducerName(updated.getProducerName());
    existing.setStock(updated.getStock());
    existing.setPrice(updated.getPrice());

    return ResponseEntity.ok(productService.saveProduct(existing));
  }

  @DeleteMapping("/{id}")
  @PreAuthorize("hasRole('Admin') or hasRole('Seller')")
  public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
    Product product = productService.getProduct(id);
    productService.deleteProduct(product.getId());
    return ResponseEntity.noContent().build();
  }

  @GetMapping("/by-category")
  public ResponseEntity<List<Product>> getProductsByCategory(
    @RequestParam("category.id") Long categoryId
  ) {
    Category category = categoryService.getCategoryById(categoryId)
      .orElseThrow(() -> new RuntimeException("Category not found"));

    return ResponseEntity.ok(productService.getProductsByCategory(category));
  }

  @PostMapping("/{productId}/reviews")
  @PreAuthorize("hasRole('User')")
  public ResponseEntity<Void> addReview(
    @PathVariable Long productId,
    @RequestBody Review review
  ) {
    productService.addReview(productId, review);
    return ResponseEntity.ok().build();
  }
}