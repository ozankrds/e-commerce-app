package com.example.e_commerce_backend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig {
  @Bean
  public WebMvcConfigurer corsConfigurer() {
    return new WebMvcConfigurer() {
      @Override
      public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/api/**") // Apply to all paths
          .allowedOrigins("http://localhost:4200") // Allow Angular dev origin
          .allowedMethods("*") // GET, POST, PUT, DELETE, etc.
          .allowedHeaders("*") // Accept all headers
          .allowCredentials(true); // Allow cookies and credentials
      }
    };
  }
}