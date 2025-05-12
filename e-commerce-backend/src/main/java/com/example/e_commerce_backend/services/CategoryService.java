package com.example.e_commerce_backend.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.e_commerce_backend.models.Category;
import com.example.e_commerce_backend.repositories.CategoryRepository;

@Service
public class CategoryService {
  private final CategoryRepository categoryRepository;

  public CategoryService(CategoryRepository categoryRepository) {
    this.categoryRepository = categoryRepository;
  }

  public List<Category> getAllCategories() {
    return categoryRepository.findAll();
  }

  public Optional<Category> getCategoryById(Long id) {
    return categoryRepository.findById(id);
  }
}