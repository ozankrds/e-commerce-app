package com.example.e_commerce_backend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.e_commerce_backend.models.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
  boolean existsByName(String name);
}
