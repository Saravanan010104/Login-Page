package com.example.cart.controller;

import com.example.cart.service.CartService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/cart")
public class CartController {

  public record AddItemRequest(@NotNull Long userId, @NotNull Long productId, @Min(1) int qty) {}

  private final CartService cartService;

  public CartController(CartService cartService) {
    this.cartService = cartService;
  }

  @PostMapping
  public ResponseEntity<?> add(@Valid @RequestBody AddItemRequest req) {
    cartService.addItem(req.userId(), req.productId(), req.qty());
    return ResponseEntity.ok(Map.of("status", "ok"));
  }

  @GetMapping("/{userId}")
  public ResponseEntity<?> get(@PathVariable long userId) {
    return ResponseEntity.ok(cartService.getCart(userId));
  }
}
