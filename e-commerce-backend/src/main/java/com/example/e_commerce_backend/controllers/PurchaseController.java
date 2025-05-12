package com.example.e_commerce_backend.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.e_commerce_backend.models.Purchase;
import com.example.e_commerce_backend.services.PurchaseService;
import com.stripe.Stripe;
import com.stripe.model.PaymentIntent;
import com.stripe.param.PaymentIntentCreateParams;

@RestController
@RequestMapping("/api/purchases")
@CrossOrigin(origins = "http://localhost:4200")
public class PurchaseController {

  private final PurchaseService purchaseService;
  
  @Value("${stripe.secret.key}")
  private String stripeSecretKey;

  public PurchaseController(PurchaseService purchaseService) {
    this.purchaseService = purchaseService;
  }

  @GetMapping
  public List<Purchase> getAll() {
    return purchaseService.getAllPurchases();
  }

  @GetMapping("/user/{username}")
  public List<Purchase> getByUser(@PathVariable String username) {
    return purchaseService.getPurchasesByUser(username);
  }

  @PostMapping
  public List<Purchase> create(@RequestBody List<Purchase> purchases) {
    return purchases.stream()
      .map(purchaseService::save)
      .toList();
  }

  @PutMapping("/{id}")
  public Purchase update(@PathVariable Long id, @RequestBody Purchase updatedPurchase) {
    return purchaseService.getPurchase(id)
      .map(existingPurchase -> {
        existingPurchase.setState(updatedPurchase.getState());
        return purchaseService.save(existingPurchase);
      })
      .orElseThrow(() -> new RuntimeException("Purchase not found with id: " + id));
  }

  @PostMapping("/create-payment-intent")
  public ResponseEntity<Map<String, String>> createPaymentIntent(@RequestBody Map<String, Object> data) {
    try {
      Stripe.apiKey = stripeSecretKey;

      Long amount = Long.parseLong(data.get("amount").toString());
      String currency = data.get("currency").toString();

      PaymentIntentCreateParams params = PaymentIntentCreateParams.builder()
        .setAmount(amount)
        .setCurrency(currency)
        .build();

      PaymentIntent intent = PaymentIntent.create(params);

      Map<String, String> responseData = new HashMap<>();
      responseData.put("clientSecret", intent.getClientSecret());

      return ResponseEntity.ok(responseData);
    } catch (Exception e) {
      e.printStackTrace();
      return ResponseEntity.badRequest().build();
    }
  }

  @PutMapping("/{id}/cancel")
  public ResponseEntity<?> cancelPurchase(@PathVariable Long id) {
    boolean cancelled = purchaseService.cancelPurchase(id);
    if (!cancelled) {
        return ResponseEntity.badRequest().body("Purchase not found or already cancelled.");
    }
    return ResponseEntity.ok().build();
  }
}