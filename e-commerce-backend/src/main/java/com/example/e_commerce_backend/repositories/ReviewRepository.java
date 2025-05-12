package com.example.e_commerce_backend.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.e_commerce_backend.models.Product;
import com.example.e_commerce_backend.models.Review;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {
  List<Review> findByProduct(Product product);
}