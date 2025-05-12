package com.example.e_commerce_backend.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.e_commerce_backend.models.Product;
import com.example.e_commerce_backend.models.Purchase;
import com.example.e_commerce_backend.models.Purchase.PurchaseState;
import com.example.e_commerce_backend.repositories.ProductRepository;
import com.example.e_commerce_backend.repositories.PurchaseRepository;

import jakarta.transaction.Transactional;

@Service
public class PurchaseService {
  private final PurchaseRepository purchaseRepository;
  private final ProductRepository productRepository;

  public PurchaseService(PurchaseRepository purchaseRepository, ProductRepository productRepository) {
    this.purchaseRepository = purchaseRepository;
    this.productRepository = productRepository;
  }

  @Transactional
  public Purchase save(Purchase purchase) {
    List<Product> managedProducts = new ArrayList<>();
    for (Product product : purchase.getProducts()) {
      Product managed = productRepository.findById(product.getId())
        .orElseThrow(() -> new RuntimeException("Product not found with id: " + product.getId()));
      managedProducts.add(managed);
    }
    purchase.setProducts(managedProducts);
    return purchaseRepository.save(purchase);
  }

  @Transactional
  public boolean cancelPurchase(Long purchaseId) {
    Optional<Purchase> optionalPurchase = purchaseRepository.findById(purchaseId);

    if (optionalPurchase.isEmpty()) {
      return false; // Not found
    }

    Purchase purchase = optionalPurchase.get();

    if (purchase.getState() == PurchaseState.CANCELLED) {
      return false; // Already cancelled
    }

    purchase.setState(PurchaseState.CANCELLED);
    purchaseRepository.save(purchase);
    return true;
  }

  public List<Purchase> getAllPurchases() {
    return purchaseRepository.findAll();
  }

  public Optional<Purchase> getPurchase(Long id) {
    return purchaseRepository.findById(id);
  }

  public List<Purchase> getPurchasesByUser(String username) {
    return purchaseRepository.findByUserUsername(username);
  }
}
