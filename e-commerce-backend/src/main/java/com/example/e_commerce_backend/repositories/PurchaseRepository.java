package com.example.e_commerce_backend.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.e_commerce_backend.models.Purchase;

@Repository
public interface PurchaseRepository extends JpaRepository<Purchase, Long> {
  List<Purchase> findByUserUsername(String username);
}

