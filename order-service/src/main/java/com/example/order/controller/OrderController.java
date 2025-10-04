package com.example.order.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/orders")
public class OrderController {

  @PostMapping
  public ResponseEntity<?> create(@RequestBody Map<String, Object> body) {
    // TODO: orchestrate order creation and payment reservation (stub)
    return ResponseEntity.ok(Map.of("orderId", 1, "status", "CREATED"));
  }

  @GetMapping("/{userId}")
  public ResponseEntity<?> list(@PathVariable long userId) {
    // TODO: return orders for user
    return ResponseEntity.ok(Map.of("orders", new Object[]{}));
  }
}
