package com.example.e_commerce_backend;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.example.e_commerce_backend.models.Category;
import com.example.e_commerce_backend.models.User;
import com.example.e_commerce_backend.repositories.CategoryRepository;
import com.example.e_commerce_backend.repositories.UserRepository;


@SpringBootApplication
public class ECommerceBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(ECommerceBackendApplication.class, args);
	}

	@Bean
  public CommandLineRunner initData(CategoryRepository categoryRepo, UserRepository userRepo) {
    return args -> {
      // Add categories
      List<String> categoryNames = List.of("Clothes", "Shoes", "Accessory", "Bag", "Cosmetic", "Electronic");
      for (String name : categoryNames) {
        if (!categoryRepo.existsByName(name)) {
          Category category = new Category();
          category.setName(name);
          categoryRepo.save(category);
        }
      }

      // Add admin user
      if (!userRepo.existsByUsername("admin")) {
        User admin = new User();
        admin.setUsername("admin");
        admin.setPassword("123456"); // Consider encoding this in real scenarios
        admin.setEmail("admin@gmail.com");
        admin.setRole("Admin");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        admin.setDateOfBirth(LocalDate.parse("21.09.2004", formatter));
        admin.setPhoneNumber("0 555 555 55 55");
        admin.setBanned(false);

        userRepo.save(admin);
      }
    };
  }
}